package com.example.caulong.booking;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.data.DataDatSan;

import com.example.caulong.R;
import com.example.caulong.entities.Service;

import java.util.ArrayList;
import java.util.List;

public class BookingDetailActivity extends AppCompatActivity {
    private long booking_id;
    DataDatSan helper;
    SQLiteDatabase db;
    private TextView courtname, court_price, status,
                    booking_date, phaying_date, booking_time, total, CourtTotal, ServiceTotal;
    private RecyclerView recyclerView_Service;
    private ArrayList<Service> serviceList;
    private ServiceDetailAdapter serviceAdapter;

    void init(){
        courtname = findViewById(R.id.d_tvCourtName);
        court_price = findViewById(R.id.d_tvCourtPrice);
        status = findViewById(R.id.d_tvBookingStatus);
        booking_date = findViewById(R.id.d_tvPresntDate);
        phaying_date = findViewById(R.id.d_tvBookingDate);
        booking_time = findViewById(R.id.d_tvBookingTimes);
        CourtTotal = findViewById(R.id.d_tvToTalCourtPrice);
        ServiceTotal = findViewById(R.id.d_tvTotalServicePrice);
        total = findViewById(R.id.d_tvTotalPrice);

        recyclerView_Service = findViewById(R.id.d_rvServices);
        recyclerView_Service.setLayoutManager(new LinearLayoutManager(this));
        serviceList = new ArrayList<>();
        serviceAdapter = new ServiceDetailAdapter(serviceList);
        recyclerView_Service.setAdapter(serviceAdapter);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_detail);
        helper = new DataDatSan(this);
        init();
        booking_id = getIntent().getLongExtra("booking_id",-1);
        if (booking_id == -1) {
            finish();
        } else{
            loadBookingDetails(booking_id);
            loadBookingServices(booking_id);
        }
    }

    private void loadBookingDetails(long bookingId) {
        db = helper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT c.court_name, c.price, b.status, b.present_date, " +
                            "b.booking_date, b.total_time, b.total_item " +
                            "FROM Booking b JOIN Court c ON b.court_id = c.court_id " +
                            "WHERE b.booking_id = ?",
                    new String[]{String.valueOf(bookingId)}
            );

            if (cursor.moveToFirst()) {
                courtname.setText(cursor.getString(cursor.getColumnIndexOrThrow("court_name")));
                court_price.setText(String.format("%,.0f", cursor.getDouble(cursor.getColumnIndexOrThrow("price"))));
                status.setText(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                phaying_date.setText(cursor.getString(cursor.getColumnIndexOrThrow("booking_date")));
                booking_date.setText(cursor.getString(cursor.getColumnIndexOrThrow("present_date")));

                List<String> bookingTimes = helper.getBookingTimes(bookingId);
                StringBuilder timesText = new StringBuilder();
                for (String time : bookingTimes) {
                    timesText.append(time).append(", ");
                }
                booking_time.setText("Thời gian đã đặt: " + (timesText.length() > 0 ? timesText.substring(0, timesText.length() - 2) : ""));

                double total_time = cursor.getDouble(cursor.getColumnIndexOrThrow("total_time"));
                double total_item = cursor.getDouble(cursor.getColumnIndexOrThrow("total_item"));
                double tong = total_item + total_time;

                CourtTotal.setText(String.format("%,.0f", total_time));
                ServiceTotal.setText(String.format("%,.0f", total_item));
                total.setText(String.format("%,.0f", tong));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        db.close();
    }


    private void loadBookingServices(long bookingId) {
        db = helper.getReadableDatabase();
        serviceList.clear();
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(
                    "SELECT s.service_name, bs.quantity,s.service_price,bs.total_price " +
                            "FROM Booking_service bs " +
                            "JOIN Service s ON bs.service_id = s.service_id " +
                            "WHERE bs.booking_id = ?", new String[]{String.valueOf(bookingId)});
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("service_name"));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                    double price = cursor.getInt(cursor.getColumnIndexOrThrow("service_price"));
                    double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));
                    serviceList.add(new Service(name, quantity, price, total));
                } while (cursor.moveToNext());
            }
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        serviceAdapter.notifyDataSetChanged();
        db.close();
    }
}
