package com.example.caulong.booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;

public class DichVuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dichvu);

        // Khai báo các nút
        Button btnDrink = findViewById(R.id.btn_drink);
        Button btnFood = findViewById(R.id.btn_food);
        Button btnBookLumCau = findViewById(R.id.btn_book_lum_cau);

        // Sự kiện khi người dùng nhấn chọn Nước
        btnDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở activity hiển thị layout nước
                Intent intent = new Intent(DichVuActivity.this, NuocActivity.class);
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện nhấn nút cho "Đồ ăn"
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DichVuActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện nhấn nút cho "Book người lụm cầu"
        btnBookLumCau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện hành động khác nếu cần
            }
        });
    }
}
