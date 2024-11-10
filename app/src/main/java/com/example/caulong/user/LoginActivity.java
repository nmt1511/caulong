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
import com.example.caulong.admin.AdminActivity;
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
        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        edtUser = findViewById(R.id.edt_username);
        edtPass = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btnlogin);
        txtReg = findViewById(R.id.txtsignup);
    }

    private int[] isUser(String user, String pass){
        try{
            DataDatSan helper = new DataDatSan(this);
            db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select user_id, role from User where username=? and password=?", new String[]{user,pass});
            if(cursor.moveToFirst()){
                int userId = cursor.getInt(0);
                int role = cursor.getInt(1); // role là 0 cho customer và 1 cho admin
                cursor.close();
                return new int[]{userId, role};
            }
            cursor.close();
        }catch (Exception ex){
            Toast.makeText(this, "Lỗi hệ thống ", Toast.LENGTH_LONG).show();
        }
        return new int[]{-1, -1};
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
                    int[] userInfo = isUser(username,pass);
                    int userId = userInfo[0];
                    int role = userInfo[1];
                    if(userId != -1) {
                        int customerId = -1;
                        int adminId = -1;
                        if(role == 0){ //Customer
                            Cursor cursor = db.rawQuery(
                                    "SELECT customer_id FROM Customer WHERE user_id = ?",
                                    new String[]{String.valueOf(userId)}
                            );
                            if (cursor.moveToFirst()) {
                                customerId = cursor.getInt(0);
                            }
                            cursor.close();
                        } else if (role == 1) { // Vai trò Admin
                            Cursor cursor = db.rawQuery("SELECT admin_id FROM Admin WHERE user_id = ?", new String[]{String.valueOf(userId)});
                            if (cursor.moveToFirst()) {
                                adminId = cursor.getInt(0);
                            }
                            cursor.close();
                        }

                        //Lưu user_id vào SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("userId",userId);
                        //editor.putInt("role", role);
                        if (role == 0) {
                            editor.putInt("customerId", customerId);
                            Toast.makeText(getApplication(), "Mật khẩu hợp lệ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (role == 1) {
                            editor.putInt("adminId", adminId);
                            Toast.makeText(getApplication(), "Mật khẩu hợp lệ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        editor.apply();
                    }
                    else {
                        Toast.makeText(getApplication(), "Mật khẩu không hợp lệ", Toast.LENGTH_LONG).show();
                        edtUser.requestFocus();
                    }
                }
            }
        });
    }
}
