package com.example.caulong.booking;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class ServiceTypeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ServiceTypeAdapter adapter;
    private List<ServiceType> serviceTypeList = new ArrayList<>();
    private SQLiteDatabase db;
    private long booking_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        booking_id = getIntent().getLongExtra("booking_id",-1);
        loadServiceTypes();
        adapter.setOnItemClickListener(new ServiceTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ServiceType serviceType) {
                Intent intent = new Intent(ServiceTypeListActivity.this, ServiceListActivity.class);
                intent.putExtra("type_id", serviceType.getType_id());
                intent.putExtra("booking_id",booking_id);
                startActivity(intent);
            }
        });
    }

    private void loadServiceTypes() {
        DataDatSan helper = new DataDatSan(this);
        db = helper.getReadableDatabase();

        Cursor cursor = db.query("Service_type", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int typeId = cursor.getInt(0);
            String typeName = cursor.getString(1);
            serviceTypeList.add(new ServiceType(typeId, typeName));
        }
        cursor.close();

        adapter = new ServiceTypeAdapter(serviceTypeList, this);
        recyclerView.setAdapter(adapter);
    }
}
