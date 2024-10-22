package com.example.caulong.booking;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Booking_yard extends AppCompatActivity {

    private Spinner spinnerSan;
    private GridView gridViewGio;
    private Button buttonChonNgay, buttonXacNhan, buttonDichVuKhac; // Thêm buttonDichVuKhac
    private TextView textViewNgay, textViewGiaSan;
    private String selectedDate = ""; // Lưu ngày đã chọn
    private static final int GIA_TIEN_MOI_GIO = 25000;

    private SQLiteDatabase database; // Khởi tạo SQLiteDatabase
    private Set<String> selectedSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Ánh xạ View
        spinnerSan = findViewById(R.id.spinner_san);
        gridViewGio = findViewById(R.id.gridView_gio);
        buttonChonNgay = findViewById(R.id.btndate);
        buttonXacNhan = findViewById(R.id.btnxacnhan);
        textViewNgay = findViewById(R.id.textview_ngay);
        textViewGiaSan = findViewById(R.id.txtGiaSan);
        buttonDichVuKhac = findViewById(R.id.btn_dichvu_khac); // Khai báo buttonDichVuKhac

        // Tạo và mở SQLite
        database = openOrCreateDatabase("LichDatSan", MODE_PRIVATE, null);
        createTableIfNeeded(); // Tạo bảng nếu chưa có

        // Ẩn spinner sân ban đầu
        spinnerSan.setVisibility(View.GONE);

        // Danh sách sân
        String[] danhSachSan = {"Sân 1", "Sân 2", "Sân 3", "Sân 4", "Sân 5"};
        ArrayAdapter<String> adapterSan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhSachSan);
        adapterSan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSan.setAdapter(adapterSan);

        // Danh sách giờ
        String[] gio = {"8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "13:00", "13:30", "14:00",
                "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"};
        ArrayAdapter<String> adapterGio = new ArrayAdapter<>(this, R.layout.item_time, gio);
        gridViewGio.setAdapter(adapterGio);

        // Xử lý chọn giờ
        gridViewGio.setOnItemClickListener((parent, view, position, id) -> {
            String time = gio[position];
            TextView textView = (TextView) view;

            if (selectedSet.contains(time)) {
                selectedSet.remove(time); // Bỏ chọn
                textView.setBackgroundColor(getResources().getColor(R.color.primary_100));
            } else {
                selectedSet.add(time); // Chọn giờ
                textView.setBackgroundColor(getResources().getColor(R.color.light_green));
            }
            updateTotalPrice(); // Cập nhật tổng tiền
        });

        // Xử lý chọn ngày
        buttonChonNgay.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Thiết lập ngày tối thiểu là ngày hôm nay
            DatePickerDialog datePickerDialog = new DatePickerDialog(Booking_yard.this,
                    (view, year1, month1, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        textViewNgay.setText("Ngày đã chọn: " + selectedDate);
                        spinnerSan.setVisibility(View.VISIBLE); // Hiển thị spinner sân
                    }, year, month, day);

            // Thiết lập ngày tối thiểu là ngày hôm nay
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        });


        // Xử lý sự kiện xác nhận
        buttonXacNhan.setOnClickListener(v -> {
            if (selectedSet.isEmpty()) {
                Toast.makeText(Booking_yard.this, "Vui lòng chọn ít nhất một giờ", Toast.LENGTH_SHORT).show();
                return;
            }

            String sanDaChon = spinnerSan.getSelectedItem().toString();
            String gioDaChon = selectedSet.toString();
            int tongTien = selectedSet.size() * GIA_TIEN_MOI_GIO;

            // Lưu thông tin vào SQLite
            ContentValues values = new ContentValues();
            values.put("ngay", selectedDate);
            values.put("san", sanDaChon);
            values.put("gio", gioDaChon);
            values.put("gia_tien", sanDaChon);
            database.insert("lich_su", null, values);

            // Hiển thị thông tin đặt sân
            new AlertDialog.Builder(Booking_yard.this)
                    .setTitle("Thông tin đặt sân")
                    .setMessage("Ngày: " + selectedDate + "\nSân: " + sanDaChon +
                            "\nGiờ: " + gioDaChon + "\nGiá sân: " + formatCurrency(tongTien))
                    .setPositiveButton("OK", null)
                    .show();
        });

        // Xử lý sự kiện cho nút "Dịch Vụ Khác"
        buttonDichVuKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Booking_yard.this, DichVuActivity.class);
                startActivity(intent);
            }
        });

    }

    // Tạo bảng lịch sử nếu chưa có
    private void createTableIfNeeded() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS lich_su (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ngay TEXT, san TEXT, gio TEXT, gia_san INTEGER)";
        database.execSQL(createTableQuery);
    }

    // Cập nhật tổng tiền
    private void updateTotalPrice() {
        int totalPrice = selectedSet.size() * GIA_TIEN_MOI_GIO;
        textViewGiaSan.setText("Giá sân: " + formatCurrency(totalPrice));
    }

    // Định dạng tiền tệ (ví dụ: 25.000 VND)
    private String formatCurrency(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + " VND";
    }
}
