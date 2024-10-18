package com.example.caulong;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Setup_yard extends AppCompatActivity {
    private EditText editTextDate;
    private Button btnXacNhan;
    private ListView listViewYard;
    private List<String> danhSachSan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setup_yard);

        // Ánh xạ các thành phần giao diện
        editTextDate = findViewById(R.id.editTextDate);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        listViewYard = findViewById(R.id.listViewYards);

        // Tạo danh sách các sân giả định
        danhSachSan = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            danhSachSan.add("Sân " + i);
        }

        // Ẩn ListView ban đầu
        listViewYard.setVisibility(View.GONE);

        // Thiết lập sự kiện chọn ngày
        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        // Thiết lập sự kiện xác nhận
        btnXacNhan.setOnClickListener(v -> updateYardList());

        // Thiết lập sự kiện khi nhấn vào từng item trong ListView
        listViewYard.setOnItemClickListener((parent, view, position, id) -> {
            String sanDuocChon = danhSachSan.get(position);
            Toast.makeText(Setup_yard.this, "Bạn đã chọn " + sanDuocChon, Toast.LENGTH_SHORT).show();
        });
    }

    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Setup_yard.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Cập nhật EditText với ngày đã chọn
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editTextDate.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void updateYardList() {
        // Kiểm tra xem ngày đã được chọn chưa
        String selectedDate = editTextDate.getText().toString();
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị danh sách sân
        listViewYard.setVisibility(View.VISIBLE);

        // Sử dụng YardAdapter cho ListView
        YardAdapter adapter = new YardAdapter(this, danhSachSan);
        listViewYard.setAdapter(adapter);
    }
}
