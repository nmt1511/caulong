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

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Service;
import com.example.caulong.entities.ServiceType;

import java.util.ArrayList;

public class EditServiceActivity extends Activity {
    Button btnSave;
    EditText name, price, quantity;
    Spinner spnType;
    SQLiteDatabase db;
    DataDatSan helper = new DataDatSan(this);
    ArrayList<ServiceType> TypeList = new ArrayList<>();
    ArrayAdapter<ServiceType> adapter;
    int posSpinner = -1;
    String id_service;

    void init(){
        btnSave = findViewById(R.id.btnSave_edit);
        name = findViewById(R.id.etServiceName_edit);
        price = findViewById(R.id.etPriceService_edit);
        quantity = findViewById(R.id.etQuantityService_edit);
        spnType = findViewById(R.id.spnTypeService_edt);
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
            adapter = new ArrayAdapter<>(this, R.layout.spinner_item, TypeList);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spnType.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getApplication(), "Lỗi "+ ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Service service = (Service) bundle.getSerializable("service");
        name.setText(service.getService_name());
        quantity.setText(String.valueOf(service.getQuantity()));
        price.setText(String.valueOf(service.getService_price()));
        id_service = String.valueOf(service.getService_id());
        int typeId = service.getType_id();
        int i=0;
        while(i < TypeList.size()){
            if (TypeList.get(i).getType_id() == typeId) {
                break;
            }
            i++;
        }
        spnType.setSelection(i);
    }
    private boolean saveService(){
        ContentValues values = new ContentValues();
        try{
            values.put("service_name",name.getText().toString());
            values.put("quantity",Integer.parseInt(quantity.getText().toString()));
            values.put("service_price",Double.parseDouble(price.getText().toString()));
            values.put("type_id",TypeList.get(posSpinner).getType_id());
            if(db.update("Service",values,"service_id=?",new String[]{id_service}) != -1)
                return true;
        }catch (Exception e){
            Toast.makeText(getApplication(), "Lỗi "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        init();
        getTypeList();
        getData();

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveService()) {
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    Service s = new Service(Integer.parseInt(quantity.getText().toString()),Integer.parseInt(id_service),name.getText().toString(),Double.parseDouble(price.getText().toString()),TypeList.get(posSpinner).getType_id(),TypeList.get(posSpinner).getType_name());
                    bundle.putSerializable("service", s);
                    intent.putExtra("data", bundle);
                    setResult(ServiceList.SAVE_EDIT_SERVICE, intent);
                    Toast.makeText(getApplication(), "Cập nhật dịch vụ thành công!!!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplication(), "Cập nhật dịch vụ không thành công!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
