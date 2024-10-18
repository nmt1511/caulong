package com.example.caulong; // Thay đổi theo package của bạn

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Setup_yard extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_setup_yard); // Thay activity_main bằng tên layout chính của bạn

            // Tìm các View sân
            findViewById(R.id.img1).setOnClickListener(v -> showTimePickerDialog(1));
            findViewById(R.id.img2).setOnClickListener(v -> showTimePickerDialog(2));
            findViewById(R.id.img3).setOnClickListener(v -> showTimePickerDialog(3));
            findViewById(R.id.img4).setOnClickListener(v -> showTimePickerDialog(4));
            findViewById(R.id.img5).setOnClickListener(v -> showTimePickerDialog(5));
            findViewById(R.id.img6).setOnClickListener(v -> showTimePickerDialog(6));
            findViewById(R.id.img7).setOnClickListener(v -> showTimePickerDialog(7));
            findViewById(R.id.img8).setOnClickListener(v -> showTimePickerDialog(8));
            findViewById(R.id.img9).setOnClickListener(v -> showTimePickerDialog(9));
        }

        // Hàm hiển thị dialog chọn giờ
        private void showTimePickerDialog(int sanSo) {
            // Tạo một Dialog để hiển thị bảng chọn giờ
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.activity_time_setupyard); // Thay dialog_chon_gio bằng tên layout bảng chọn giờ của bạn

            // Tìm và xử lý các TextView hoặc Button trong dialog
            TextView title = dialog.findViewById(R.id.);
            title.setText("Chọn giờ cho sân số " + sanSo);

            Button confirmButton = dialog.findViewById(R.id.);
            confirmButton.setOnClickListener(v -> {
                // Xử lý sự kiện khi người dùng chọn giờ và nhấn "CHỌN"
                dialog.dismiss(); // Đóng dialog
                // Thực hiện hành động lưu thời gian vào hệ thống để ngăn trùng lịch
            });

            // Hiển thị dialog lên màn hình
            dialog.show();
        }
    }
