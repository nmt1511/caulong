package com.example.caulong.admin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class DetailCustomerActivity extends Activity {
    private TextView tvname, tvgender, tvsdt, tvemail;
    private Button btnNext, btnPrevious, btnUpdate, btnDetail;
    private ImageView imgAvatar;
    private DataDatSan helper;
    SQLiteDatabase db;
    List<String> customerIds = new ArrayList<>();
    ArrayList<Customer> customerList = new ArrayList<>();
    int currentIndex = 0; //vt hiện tại trg ds
    String currentCustomerID;
    public static final int EDIT = 114;
    public static final int SAVE_EDIT = 116;

    void init(){
        tvname = findViewById(R.id.tvName);
        tvgender = findViewById(R.id.tvGender);
        tvsdt = findViewById(R.id.tvsdt);
        tvemail = findViewById(R.id.tvemail);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDetail = findViewById(R.id.btnDetail);
        imgAvatar = findViewById(R.id.imgAvatar);
        helper = new DataDatSan(this);
    }

    private void getInformation(String id){
        db = helper.getReadableDatabase();
        Cursor c= db.rawQuery("select * from Customer where customer_id = ?", new String[]{id});

        if(c != null && c.moveToFirst()){
            tvname.setText(c.getString(1));
            tvgender.setText(c.getString(2));
            tvsdt.setText(c.getString(3));
            tvemail.setText(c.getString(4));
        }
        if(c != null)
            c.close();
    }

    private void getAllCustomerID(){
        db = helper.getReadableDatabase();
        Cursor c= db.rawQuery("select customer_id from Customer",null);

        if(c != null) {
            while(c.moveToNext()) {
                String id = c.getString(0);
                customerIds.add(id);
            }
            c.close();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);
        init();
        getAllCustomerID();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        if(bundle != null){
            Customer c= (Customer) bundle.getSerializable("customer");
            String id = c.getCustomer_id();
            currentCustomerID = id;
            currentIndex = customerIds.indexOf(id);  // Xác định vị trí hiện tại dựa trên ID
            getInformation(currentCustomerID);
        }
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex < customerIds.size() - 1){
                    currentIndex++;// khách tiếp theo
                    currentCustomerID = customerIds.get(currentIndex);
                    getInformation(currentCustomerID);
                } else if (currentIndex == customerIds.size() - 1) {
                    currentIndex = 0;
                    currentCustomerID = customerIds.get(currentIndex);
                    getInformation(currentCustomerID);
                }
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex > 0){
                    currentIndex--;//lùi lại 1 khách
                    currentCustomerID = customerIds.get(currentIndex);
                    getInformation(currentCustomerID);
                } else if (currentIndex == 0) {
                    currentIndex = customerIds.size()-1;
                    currentCustomerID = customerIds.get(currentIndex);
                    getInformation(currentCustomerID);
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer c = customerList.get(currentIndex);
                Bundle bundle = new Bundle();
                Intent intentedit = new Intent(DetailCustomerActivity.this, EditCustomerActivity.class);
                bundle.putSerializable("customer",c);;
                intentedit.putExtra("data", bundle);
                startActivityForResult(intentedit,EDIT);
            }
        });
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Customer c = customerList.get(currentIndex);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(DetailCustomerActivity.this, DetailCustomer_booking.class);
                bundle.putSerializable("customer",c);;
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT && resultCode == SAVE_EDIT) {
            Bundle bundle = data.getBundleExtra("data");
            Customer c = (Customer) bundle.getSerializable("customer");
            customerList.set(currentIndex, c);
            Customer_List.adapter.notifyDataSetChanged();
            if (c != null) {
                tvname.setText(c.getCustomer_name());
                tvgender.setText(c.getGender());
                tvemail.setText(c.getEmail());
                tvsdt.setText(c.getPhone_number());
            }
        }
    }
}
