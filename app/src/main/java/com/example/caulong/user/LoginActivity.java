package com.example.caulong.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.MainActivity;
import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;

public class LoginActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    EditText edtUser, edtPass;
    Button btnLogin;
    TextView txtReg;
    DataDatSan dbDatSan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Khởi tạo CSDL
        dbDatSan = new DataDatSan(this);
        dbDatSan.getWritableDatabase();
        init();
        initListener();
    }
    private void init(){
        edtUser = findViewById(R.id.edt_username);
        edtPass = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btnlogin);
        txtReg = findViewById(R.id.txtsignup);
    }

    private int isUser(String user, String pass){
        try{
            DataDatSan helper = new DataDatSan(this);
            db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from User where username=? and password=?", new String[]{user,pass});
            if(cursor.moveToFirst()){
                return cursor.getInt(0);
            }
        }catch (Exception ex){
            Toast.makeText(this, "Lỗi hệ thống ", Toast.LENGTH_LONG).show();
        }
        return -1;
    }

    private void initListener(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                if(username.equals("")|| pass.equals(""))
                    Toast.makeText(LoginActivity.this,"Cần điền đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
                else{
                    int userId = isUser(username,pass);
                    if(userId != -1) {
                        //Lưu user_id vào SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("userId",userId);
                        editor.apply();

                        Toast.makeText(getApplication(), "Mật khẩu hợp lệ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplication(), "Mật khẩu không hợp lệ", Toast.LENGTH_LONG).show();
                        edtUser.requestFocus();
                    }
                }
            }
        });
    }
}
