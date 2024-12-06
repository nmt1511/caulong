package com.example.caulong.admin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ServiceList extends Activity {
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private ArrayList<Service> ServiceList;
    private ServiceAdapter adapter;
    SQLiteDatabase db;
    DataDatSan helper;
    public static final int OPEN_SERVICE = 113;
    public static final int EDIT_SERVICE = 114;
    public static final int SAVE_SERVICE = 115;
    public static final int SAVE_EDIT_SERVICE = 116;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicelist);
        recyclerView = findViewById(R.id.d_rvServices);
        btnAdd = findViewById(R.id.btnAddService);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        helper = new DataDatSan(this);
        ServiceList = getAllServices();
        adapter = new ServiceAdapter(ServiceList);
        recyclerView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceList.this, InsertServiceActivity.class);
                startActivityForResult(intent,OPEN_SERVICE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case OPEN_SERVICE:
                if(resultCode == SAVE_SERVICE){
                    Bundle bundle = data.getBundleExtra("data");
                    Service service = (Service) bundle.getSerializable("service");
                    ServiceList.add(service);
                    adapter.notifyDataSetChanged();
                }
                break;
            case EDIT_SERVICE:
                if(resultCode == SAVE_EDIT_SERVICE){
                    Bundle bundle = data.getBundleExtra("data");
                    Service service = (Service) bundle.getSerializable("service");
                    for (int i = 0; i < ServiceList.size(); i++) {
                        if (ServiceList.get(i).getService_id() == service.getService_id()) {
                            ServiceList.set(i, service);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public ArrayList<Service> getAllServices() {
        ArrayList<Service> ServiceList = new ArrayList<>();
        db = helper.getReadableDatabase();
        String query = "SELECT  s.quantity, s.service_id, s.service_name, s.service_price, s.type_id, t.type_name " +
                "FROM Service s "+
                "INNER JOIN Service_type t ON s.type_id = t.type_id";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Service service = new Service(
                        cursor.getInt(0), //quantity
                        cursor.getInt(1), //id
                        cursor.getString(2),// name
                        cursor.getDouble(3),//price
                        cursor.getInt(4), //type_id
                        cursor.getString(5) //type_name
                );
                ServiceList.add(service);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ServiceList;
    }
}
