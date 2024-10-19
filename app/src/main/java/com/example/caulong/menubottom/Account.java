package com.example.caulong.menubottom;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity; // Thêm import cho AppCompatActivity
import com.example.caulong.R;

public class Account extends AppCompatActivity { // Kế thừa từ AppCompatActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Thiết lập sự kiện click cho nút "Trở lại"
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Trở lại activity trước đó
            }
        });
    }
}
