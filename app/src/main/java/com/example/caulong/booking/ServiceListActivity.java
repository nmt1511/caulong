package com.example.caulong.booking;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Service;

import java.util.ArrayList;

public class ServiceListActivity extends AppCompatActivity {
    private int typeid;
    private TextView tvTotalPrice;
    private Button btnAddToCart;
    private RecyclerView recyclerView;
    private TextView txtTitleService;
    private ServiceAdapter serviceAdapter;
    private ArrayList<Service> serviceList = new ArrayList<>();;
    SQLiteDatabase db;

    void init(){
        tvTotalPrice = findViewById(R.id.tv_price);
        btnAddToCart = findViewById(R.id.btn_AddtoCart);
        txtTitleService = findViewById(R.id.txtTitleService);
        recyclerView = findViewById(R.id.recyclerViewItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
//        typeid = getIntent().getIntExtra("type_id",-1);
//        init();
        //serviceList = new ArrayList<>();
//        serviceAdapter = new ServiceAdapter(serviceList);
//        recyclerView.setAdapter(serviceAdapter);
//        loadItems(typeid);
    }
    private void loadItems(int typeId) {
        DataDatSan helper = new DataDatSan(this);
        db = helper.getReadableDatabase();
        String type_name="";
        Cursor c= db.rawQuery("SELECT * FROM Service_type WHERE type_id = ?", new String[]{String.valueOf(typeId)});
        if(c.moveToFirst()){
            type_name = c.getString(1);
        }
        txtTitleService.setText(type_name.toString());

        String query = "SELECT * FROM Service WHERE type_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(typeId)});

        if (cursor.moveToFirst()) {
            do {
                int itemId = cursor.getInt(0);
                String itemName = cursor.getString(1);
                double itemprice = cursor.getDouble(4);
                // Thêm sản phẩm vào danh sách
                serviceList.add(new Service(itemId, itemName, itemprice));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        serviceAdapter = new ServiceAdapter(serviceList,this);
        recyclerView.setAdapter(serviceAdapter);
    }
}
