package com.example.caulong.admin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Service;
import com.example.caulong.entities.ServiceType;

import java.util.ArrayList;

public class InsertServiceActivity extends Activity {
    Button btnAdd;
    EditText name, price, quantity;
    Spinner spnType;
    SQLiteDatabase db;
    DataDatSan helper = new DataDatSan(this);
    ArrayList<ServiceType> TypeList = new ArrayList<>();
    ArrayAdapter<ServiceType> adapter;
    int posSpinner = -1;

    void init(){
        btnAdd = findViewById(R.id.btnSave_add);
        name = findViewById(R.id.etServiceName_add);
        price = findViewById(R.id.etPriceService_add);
        quantity = findViewById(R.id.etQuantityService_add);
        spnType = findViewById(R.id.spnTypeService_add);
    }
    private void getTypeList(){
        try{
            db = helper.getReadableDatabase();
            Cursor c =db.query("Service_type",null,null,null,null,null,null);
            c.moveToFirst();
            while(!c.isAfterLast()){
                TypeList.add(new ServiceType(c.getInt(0), c.getString(1).toString()));
                c.moveToNext();
            }
            adapter = new ArrayAdapter<ServiceType>(this, R.layout.spinner_item, TypeList);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spnType.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private long saveService(){
        db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        try{
            values.put("service_name",name.getText().toString());
            values.put("quantity",Integer.parseInt(quantity.getText().toString()));
            values.put("service_price",Double.parseDouble(price.getText().toString()));
            values.put("type_id",TypeList.get(posSpinner).getType_id());
            return (db.insert("Service",null,values));
        }catch (Exception e){
            Toast.makeText(getApplication(), "Lỗi "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return -1;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_service);
        init();
        getTypeList();

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                posSpinner = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = saveService();
                Bundle bundle = new Bundle();
                Intent intent = getIntent();
                if(id != 1){
                    Service s = new Service(Integer.parseInt(quantity.getText().toString()),Integer.parseInt(id+""),name.getText().toString(),Double.parseDouble(price.getText().toString()),TypeList.get(posSpinner).getType_id(),TypeList.get(posSpinner).getType_name());
                    bundle.putSerializable("service",s);
                    intent.putExtra("data",bundle);
                    setResult(ServiceList.SAVE_SERVICE,intent);
                    Toast.makeText(getApplication(),"Thêm dịch vụ thành công", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplication(), "Thêm dịch vụ không thành công!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
