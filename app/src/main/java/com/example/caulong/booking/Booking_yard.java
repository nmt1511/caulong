package com.example.caulong.booking;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.court;
import com.example.caulong.entities.times;
import com.example.caulong.user.LoginActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Booking_yard extends AppCompatActivity {

    private Spinner spinnerSan;
    private GridView gridViewGio;
    private Button buttonChonNgay, buttonXacNhan; // Thêm buttonDichVuKhac
    private TextView textViewNgay, textViewGiaSan;
    private EditText edtSelectedDate;
    private String selectedDate = ""; // Lưu ngày đã chọn
    ArrayList<court> CourtList = new ArrayList<court>();
    ArrayAdapter<court> adapter_court;
    int posSpinner = -1;
    ArrayList<times> TimeList = new ArrayList<times>();
    ArrayAdapter<times> adapter_time;
    int courtId;
    Double courtPrice;

    private SQLiteDatabase db;
    private Set<String> selectedSet = new HashSet<>();
    private Set<String> bookedTimes = new HashSet<>();
    private double totalTimeSelected = 0.0;

    void init() {
        spinnerSan = findViewById(R.id.spinner_san);
        gridViewGio = findViewById(R.id.gridView_gio);
        buttonChonNgay = findViewById(R.id.btndate);
        buttonXacNhan = findViewById(R.id.btnxacnhan);
        textViewNgay = findViewById(R.id.textview_ngay);
        textViewGiaSan = findViewById(R.id.txtGiaSan);
        //buttonDichVuKhac = findViewById(R.id.btn_dichvu_khac);
        edtSelectedDate = findViewById(R.id.edtSelectedDate);
    }

    private void getCourtList() {
        try {
            DataDatSan helper = new DataDatSan(this);
            db = helper.getReadableDatabase();
            Cursor c = db.query("Court", null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                CourtList.add(new court(c.getInt(0) + "", c.getString(1).toString(),
                        c.getDouble(2), c.getString(3).toString()));
                c.moveToNext();
            }
            adapter_court = new ArrayAdapter<court>(this, android.R.layout.simple_list_item_1, CourtList);
            adapter_court.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spinnerSan.setAdapter(adapter_court);
        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getTimeList() {
        try {
            DataDatSan helper = new DataDatSan(this);
            db = helper.getReadableDatabase();
            Cursor c = db.query("Time", null, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                TimeList.add(new times(c.getInt(0) + "", c.getString(1).toString(), c.getDouble(2)));
                c.moveToNext();
            }
            // Sử dụng ArrayAdapter để hiển thị thời gian trong GridView
            adapter_time = new ArrayAdapter<>(this, R.layout.item_time, TimeList);
            gridViewGio.setAdapter(adapter_time);

        } catch (Exception e) {
            Toast.makeText(getApplication(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        init();
        // mở SQLite
        DataDatSan helper = new DataDatSan(this);
        db = helper.getReadableDatabase();
        // Ẩn spinner sân ban đầu
        spinnerSan.setVisibility(View.GONE);
        //dsSan vào Spinner
        getCourtList();
        //dsTime vào GridView
        getTimeList();
        gridViewGio.setVisibility(View.GONE);
        // Xử lý chọn giờ
        gridViewGio.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy thời gian tại vị trí được nhấp
            times selectedTime = TimeList.get(position);
            TextView textView = (TextView) view;
            if(bookedTimes.contains(selectedTime.getTime_id())){
                textView.setEnabled(false);
            }
            // Kiểm tra xem thời gian đã được chọn chưa
            else if (selectedSet.contains(selectedTime.getTime_id())) {
                selectedSet.remove(selectedTime.getTime_id());// Bỏ chọn
                totalTimeSelected -= selectedTime.getValue();
                textView.setBackgroundColor(getResources().getColor(R.color.primary_100));
            } else {
                selectedSet.add(selectedTime.getTime_id()); // Chọn thời gian
                totalTimeSelected += selectedTime.getValue();
                textView.setBackgroundColor(getResources().getColor(R.color.light_green));
            }

            // Cập nhật tổng số tiền dựa trên thời gian đã chọn
            updateTotalPrice();
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
                        edtSelectedDate.setText(selectedDate);
                        spinnerSan.setVisibility(View.VISIBLE); // Hiển thị spinner sân
                    }, year, month, day);

            // Thiết lập ngày tối thiểu là ngày hôm nay
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();

        });

        edtSelectedDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //cập nhật GridView với giờ đã đặt sau khi chọn ngày và sân
                if (posSpinner != -1 && !selectedDate.isEmpty()) {
                    bookedTimes.clear();
                    int selectedCourtId = Integer.parseInt(CourtList.get(posSpinner).getCourt_id());
                    bookedTimes = getBookedTimes(selectedDate, selectedCourtId);
                    updateGridViewTimes(bookedTimes);
                    gridViewGio.setVisibility(View.VISIBLE);
                }else if(posSpinner == -1){
                    Toast.makeText(Booking_yard.this, "Hãy chọn sân!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinnerSan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                posSpinner = i;
                if (posSpinner != -1 && !selectedDate.isEmpty()) {
                    bookedTimes.clear();
                    int selectedCourtId = Integer.parseInt(CourtList.get(posSpinner).getCourt_id());
                    bookedTimes = getBookedTimes(selectedDate, selectedCourtId);
                    updateGridViewTimes(bookedTimes);
                    gridViewGio.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String currentDate = day + "/" + month + "/" + year;
        // Xử lý sự kiện xác nhận
        buttonXacNhan.setOnClickListener(v -> {
            if (selectedSet.isEmpty()) {
                Toast.makeText(Booking_yard.this, "Vui lòng chọn ít nhất một giờ", Toast.LENGTH_SHORT).show();
                return;
            }
            //Lấy thông tin sân
            courtId = Integer.parseInt(CourtList.get(posSpinner).getCourt_id());
            courtPrice = CourtList.get(posSpinner).getPrice();
            double tongTienGio = totalTimeSelected * courtPrice;
            SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            int customerId = preferences.getInt("customerId", -1);  // -1 if not logged in
            if (customerId != -1) {
                // Lưu vào bảng Booking
                ContentValues bookingValues = new ContentValues();
                bookingValues.put("customer_id", customerId);
                bookingValues.put("court_id", courtId);
                bookingValues.put("present_date", currentDate);
                bookingValues.put("booking_date", selectedDate);
                bookingValues.put("total_time", tongTienGio);
                bookingValues.put("status", "Đã đặt");
                long bookingId = db.insert("Booking", null, bookingValues);

                // Lưu từng giờ vào bảng Booking_time
                for (String timeId : selectedSet) {
                    ContentValues bookingTimeValues = new ContentValues();
                    bookingTimeValues.put("time_id", timeId);
                    bookingTimeValues.put("booking_id", bookingId);
                    db.insert("Booking_time", null, bookingTimeValues);
                }
                // Khởi tạo StringBuilder để lưu danh sách tên giờ
                StringBuilder selectedTimeNames = new StringBuilder();
                for (String id : selectedSet) {
                    for (times timeSlot : TimeList) {
                        if (timeSlot.getTime_id() == id) {
                            selectedTimeNames.append(timeSlot.getTime_name()).append(", ");
                            break;
                        }
                    }
                }
                // Loại bỏ dấu phẩy cuối cùng, nếu có
                if (selectedTimeNames.length() > 0) {
                    selectedTimeNames.setLength(selectedTimeNames.length() - 2); // Bỏ dấu phẩy cuối cùng
                }
                // Hiển thị thông tin đặt sân
                new AlertDialog.Builder(Booking_yard.this)
                        .setTitle("Thông tin đặt sân")
                        .setMessage("Ngày: " + selectedDate + "\nSân: " + CourtList.get(posSpinner).getCourt_name() +
                                "\nGiờ: " + selectedTimeNames.toString() + "\nTổng tiền: " + formatCurrency((int) tongTienGio))
                        .setNegativeButton("Dịch vụ khác", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Booking_yard.this, ServiceTypeListActivity.class);
                                intent.putExtra("booking_id",bookingId);
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("OK", null)
                        .show();
                // Cập nhật màu đỏ cho các ô giờ đã chọn
                bookedTimes = getBookedTimes(selectedDate, courtId);//?
                bookedTimes.addAll(selectedSet);
                updateGridViewTimes(bookedTimes);
                bookedTimes.clear();
                selectedSet.clear();
                totalTimeSelected = 0;
                updateTotalPrice();
            } else {
                Toast.makeText(Booking_yard.this, "Vui lòng đăng nhập để đặt sân!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Booking_yard.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện cho nút "Dịch Vụ Khác"
//        buttonDichVuKhac.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Booking_yard.this, ServiceTypeListActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    // Cập nhật tổng tiền
    private void updateTotalPrice() {
        Double price = CourtList.get(posSpinner).getPrice();
        double tongTienGio = totalTimeSelected * price;
        textViewGiaSan.setText("Giá sân theo giờ: " + formatCurrency((int) tongTienGio));
    }

    // Định dạng tiền tệ (ví dụ: 25.000 VND)
    private String formatCurrency(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + " VND";
    }

    //lấy ds Time đã được đặt
    private Set<String> getBookedTimes(String date, int courtId) {
        Cursor cursor = db.rawQuery("SELECT time_id FROM Booking_time " +
                        "INNER JOIN Booking ON Booking_time.booking_id = Booking.booking_id " +
                        "WHERE Booking.court_id = ? AND Booking.booking_date = ? AND Booking.status != ?",
                new String[]{String.valueOf(courtId), date, "Đã hủy"});

        if (cursor.moveToFirst()) {
            do {
                bookedTimes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookedTimes;
    }

    //cập nhật màu sắc v trạng thái ô giờ
    private void updateGridViewTimes(Set<String> bookedTimes) {
        for (int i = 0; i < TimeList.size(); i++) {
            // Lấy item tại vị trí i trong GridView
            View gridItem = gridViewGio.getChildAt(i);

            // Kiểm tra nếu item không null và là một TextView
            if (gridItem != null && gridItem instanceof TextView) {
                TextView timeSlotTextView = (TextView) gridItem;
                times timeSlot = TimeList.get(i);

                // Đổi màu sắc cho thời gian đã đặt
                if (bookedTimes.contains(timeSlot.getTime_id())) {
                    timeSlotTextView.setBackgroundColor(getResources().getColor(R.color.red));
                    timeSlotTextView.setEnabled(false);
                }else if (selectedSet.contains(timeSlot.getTime_id())) {
                    timeSlotTextView.setBackgroundColor(getResources().getColor(R.color.light_green)); // Selected color
                }
                else {
                    timeSlotTextView.setBackgroundColor(getResources().getColor(R.color.primary_100));
                    timeSlotTextView.setEnabled(true);
                }
            }
        }
    }
}