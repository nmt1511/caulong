package com.example.caulong.user;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;

public class EditInfoActivity extends AppCompatActivity {

    private EditText edtCustomerName, edtGender, edtPhone, edtEmail;
    private ImageView imgAvatar;
    private Button btnSave;
    private SQLiteDatabase db;
    DataDatSan dbHelper = new DataDatSan(this);
    private int userId; // Lưu userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        // Khởi tạo database
        db = dbHelper.getWritableDatabase();

        // Lấy userId từ Intent
        userId = getIntent().getIntExtra("user_id", -1);

        // Ánh xạ các view
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtGender = findViewById(R.id.edtGender);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        btnSave = findViewById(R.id.btnSave);
        imgAvatar = findViewById(R.id.imgProfile);
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int CustomerId = preferences.getInt("customerId", -1);
        dbHelper.loadAvatarFromDatabase(imgAvatar, CustomerId);
        // Lấy thông tin hiện tại từ cơ sở dữ liệu
        loadUserInfo();

        // Thiết lập sự kiện click cho nút Lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc Activity hiện tại
                // Intent intent = new Intent(EditInfoActivity.this, PreviousActivity.class);
                // startActivity(intent);
            }
        });
    }

    private void loadUserInfo() {
        db = dbHelper.getReadableDatabase();
        // Thực hiện truy vấn lấy thông tin người dùng từ cơ sở dữ liệu và gán vào EditText
        String query = "SELECT c.customer_name, c.gender, c.phone_number, c.email " +
                "FROM User u " +
                "JOIN Customer c ON u.user_id = c.user_id " +
                "WHERE u.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            edtCustomerName.setText(cursor.getString(0));
            edtGender.setText(cursor.getString(1));
            edtPhone.setText(cursor.getString(2));
            edtEmail.setText(cursor.getString(3));
        }
        cursor.close();
        db.close();
    }

    private void saveUserInfo() {
        db = dbHelper.getReadableDatabase();
        // Lưu thông tin mới vào cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put("customer_name", edtCustomerName.getText().toString());
        values.put("gender", edtGender.getText().toString());
        values.put("phone_number", edtPhone.getText().toString());
        values.put("email", edtEmail.getText().toString());

        // Cập nhật dữ liệu vào bảng Customer
        int rowsAffected = db.update("Customer", values, "user_id = ?", new String[]{String.valueOf(userId)});
        if (rowsAffected > 0) {
            Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại activity trước đó
        } else {
            Toast.makeText(this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
