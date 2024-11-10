package com.example.caulong.admin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.BookingHistory;

import java.util.ArrayList;
import java.util.List;

public class Canceled_fragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView header;
    private ArrayList<BookingHistory> bookings = new ArrayList<>();
    SQLiteDatabase db;
    DataDatSan helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cancel_fragment, container, false);
        helper = new DataDatSan(getContext());
        recyclerView = view.findViewById(R.id.d_rvServices);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        header = view.findViewById(R.id.headerTitle);
        header.setText("Danh Sách Đã Hủy Sân");
        bookings = getBookingByCourtAndDate();
        BookingAdapter bookingAdapter = new BookingAdapter(bookings);
        recyclerView.setAdapter(bookingAdapter);
        return view;
    }

    public ArrayList<BookingHistory> getBookingByCourtAndDate() {
        ArrayList<BookingHistory> bookingHistories = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT b.booking_id,c.customer_id,c.customer_name,b.total_time," +
                        "b.total_item, b.present_date,b.booking_date, b.status, b.court_id " +
                        "FROM Booking b INNER JOIN Customer c ON c.customer_id = b.customer_id " +
                        "WHERE b.status = ?",
                new String[]{"Đã hủy"});

        if (cursor.moveToFirst()) {
            do {
                long bookingId = cursor.getLong(0);
                int customerId = cursor.getInt(1);
                String customerName = cursor.getString(2);
                double total_time = cursor.getDouble(3);
                double total_item = cursor.getDouble(4);
                String presentDate = cursor.getString(5);
                String bookingDate = cursor.getString(6);
                String status = cursor.getString(7);
                int court_id = cursor.getInt(8);

                // Lấy danh sách thời gian đã đặt cho booking hiện tại
                List<String> times = getBookingTimes(bookingId);

                bookingHistories.add(new BookingHistory(bookingId,customerId,customerName,total_time,
                        total_item,times, status, presentDate,bookingDate, court_id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bookingHistories;
    }
    // Lấy danh sách thời gian đã đặt cho một booking cụ thể
    public List<String> getBookingTimes(long bookingId) {
        List<String> bookingTimes = new ArrayList<>();
        db = helper.getReadableDatabase();

        String query = "SELECT t.time_name FROM Booking_time bt " +
                "JOIN Time t ON bt.time_id = t.time_id " +
                "WHERE bt.booking_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(bookingId)});

        if (cursor.moveToFirst()) {
            do {
                String timeName = cursor.getString(0);
                bookingTimes.add(timeName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bookingTimes;
    }
}
