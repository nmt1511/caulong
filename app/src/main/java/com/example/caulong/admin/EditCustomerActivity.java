package com.example.caulong.admin;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Customer;

public class EditCustomerActivity extends Activity {
    private EditText edtName, edtPhone, edtEmail;
    private RadioGroup rdgGender;
    private RadioButton rdNam, rdNu;
    private Button btnSave, btnClear;
    private DataDatSan helper;
    SQLiteDatabase db;
    String id_customer, avatar;
    long user_id;

    void init(){
        edtName = findViewById(R.id.edt_ename);
        edtPhone = findViewById(R.id.edt_ephone);
        edtEmail = findViewById(R.id.edt_eemail);
        rdgGender = findViewById(R.id.rd_egroupGender);
        rdNam = findViewById(R.id.rd_enam);
        rdNu = findViewById(R.id.rd_enu);
        btnSave = findViewById(R.id.btn_eSaveCustomer);
        btnClear = findViewById(R.id.btn_eClearCustomer);
    }
    private void clearCustomer(){
        edtName.setText("");
        edtPhone.setText("");
        edtEmail.setText("");

    }
    private void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        Customer c= (Customer) bundle.getSerializable("customer");
        edtName.setText(c.getCustomer_name());
        edtPhone.setText(c.getPhone_number());
        edtEmail.setText(c.getEmail());
        String gender = c.getGender();
        if(gender.equals("Nam")){
            rdNam.setChecked(true);
            rdNu.setChecked(false);
        }
        else{
            rdNam.setChecked(false);
            rdNu.setChecked(true);
        }
        id_customer = c.getCustomer_id();
        user_id = c.getUser_id();
        avatar = c.getAvatar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        init();
        getData();
        helper = new DataDatSan(this);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCustomer();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveCustomer()){
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    String name = edtName.getText().toString().trim();
                    String gender = rdNam.isChecked() ? "Nam" : "Nữ";
                    String phone = edtPhone.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    Customer c = new Customer( id_customer, name, gender, phone, email,user_id,avatar);
                    bundle.putSerializable("customer", c);
                    intent.putExtra("data", bundle);
                    setResult(DetailCustomerActivity.SAVE_EDIT, intent);
                    Toast.makeText(getApplication(), "Cập nhật khách hàng thành công!!!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
    private boolean saveCustomer(){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put("customer_name",edtName.getText().toString().trim());
            values.put("phone_number",edtPhone.getText().toString().trim());
            values.put("email",edtEmail.getText().toString().trim());
            values.put("gender",rdNam.isChecked() ? "Nam" : "Nữ");
            values.put("user_id",user_id);
            if(db.update("Customer",values,"customer_id = ?",new String[]{id_customer}) != -1)
                return true;
        }catch (Exception e){
            Toast.makeText(getApplication(), "Lỗi "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
