package com.example.caulong.admin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Customer;
import com.example.caulong.entities.tournament;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Customer_List extends Activity {
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private ArrayList<Customer> customerList;
    private ArrayList<Customer> allCustomers;
    public static CustomerAdapter adapter;
    private EditText edtFind_customer;
    SQLiteDatabase db;
    DataDatSan helper;
    public static final int OPEN_CUSTOMER = 113;
    public static final int SAVE_CUSTOMER = 115;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        recyclerView = findViewById(R.id.d_rvCustomer);
        btnAdd = findViewById(R.id.btnAdd_Customer);
        edtFind_customer = findViewById(R.id.search_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        helper = new DataDatSan(this);
        //Lấy toàn bộ danh sách khách hàng
        allCustomers = getAllCustomer();
        customerList = new ArrayList<>(allCustomers);

        adapter = new CustomerAdapter(customerList);
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer_List.this, InsertCustomerActivity.class);
                startActivityForResult(intent,OPEN_CUSTOMER);
            }
        });
        edtFind_customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //tìm khi người dùng nhập
                String searchtext = charSequence.toString().trim();
                filterCustomerlist(searchtext);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OPEN_CUSTOMER) {
            if (resultCode == SAVE_CUSTOMER) {
                Bundle bundle = data.getBundleExtra("data");
                Customer customer = (Customer) bundle.getSerializable("customer");
                allCustomers.add(customer); // Cập nhật vào danh sách gốc
                filterCustomerlist(edtFind_customer.getText().toString()); // Cập nhật RecyclerView
            }
        }
    }

    public ArrayList<Customer> getAllCustomer() {
        ArrayList<Customer> customers = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Customer", null);

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6)
                );
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }
    private void filterCustomerlist(String searchText){
        customerList.clear();
        if (searchText.isEmpty()) {
            customerList.addAll(allCustomers); // Hiển thị toàn bộ khách hàng nếu ô tìm kiếm trống
        } else {
            for (Customer customer : allCustomers) {
                if (customer.getCustomer_name().toLowerCase().contains(searchText.toLowerCase())) {
                    customerList.add(customer); // Thêm khách hàng phù hợp với điều kiện tìm kiếm
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
