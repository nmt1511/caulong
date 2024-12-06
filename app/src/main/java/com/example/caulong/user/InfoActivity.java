package com.example.caulong.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.data.DataDatSan;
import com.example.caulong.R;

public class InfoActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DataDatSan dbDatSan;
    private ImageView imgAvatar;
    private TextView tvUsername, tvName, tvGender, tvPhone, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Khởi tạo cơ sở dữ liệu
        dbDatSan = new DataDatSan(this);
        db = dbDatSan.getReadableDatabase();

        // Ánh xạ các thành phần giao diện
        tvUsername = findViewById(R.id.tvUsername);
        tvName = findViewById(R.id.tvName);
        tvGender = findViewById(R.id.tvGender);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        imgAvatar = findViewById(R.id.imgProfile);

        // Lấy user_id từ SharedPreferences
        // Trong InfoActivity.java, kiểm tra giá trị userId
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        int CustomerId = preferences.getInt("customerId", -1);
        dbDatSan.loadAvatarFromDatabase(imgAvatar, CustomerId);
        Log.d("UserID", "userId: " + userId);

        refreshUserInfo(userId);
        // Hiển thị thông tin người dùng
        if (userId != -1) {
            displayUserInfo(userId);
        }
        // Thêm nút sửa thông tin trong InfoActivity
        Button btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, EditInfoActivity.class);
                intent.putExtra("user_id", userId); // Truyền userId
                startActivity(intent);
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

    @Override
    protected void onResume() {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        Log.d("UserID", "userId: " + userId);
        super.onResume();
        refreshUserInfo(userId); // Tải lại thông tin khi activity quay lại
    }

    private void refreshUserInfo(int userId) {
        db = dbDatSan.getReadableDatabase();
        // Thực hiện truy vấn lấy thông tin người dùng từ cơ sở dữ liệu
        String query = "SELECT u.username, c.customer_name, c.gender, c.phone_number, c.email " +
                "FROM User u " +
                "JOIN Customer c ON u.user_id = c.user_id " +
                "WHERE u.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            tvUsername.setText(cursor.getString(0));
            tvName.setText(cursor.getString(1));
            tvGender.setText(cursor.getString(2));
            tvPhone.setText(cursor.getString(3));
            tvEmail.setText(cursor.getString(4));
        }
        cursor.close();
        db.close();
    }

    private void displayUserInfo(int userId) {
        db = dbDatSan.getReadableDatabase();
        // Câu truy vấn lấy thông tin người dùng từ bảng User và Customer
        String query = "SELECT u.username, u.role, c.customer_name, c.gender, c.phone_number, c.email " +
                "FROM User u " +
                "JOIN Customer c ON u.user_id = c.user_id " +
                "WHERE u.user_id = ?";


        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);       // u.username
            String role = cursor.getString(1);           // u.role
            String customerName = cursor.getString(2);   // c.customer_name
            String gender = cursor.getString(3);         // c.gender
            String phoneNumber = cursor.getString(4);    // c.phone_number
            String email = cursor.getString(5);          // c.email

            // Set dữ liệu lên các TextView
            tvUsername.setText(username);
            tvName.setText(customerName);
            tvGender.setText(gender);
            tvPhone.setText(phoneNumber);
            tvEmail.setText(email);
        }
        cursor.close();
        db.close();
    }
}
