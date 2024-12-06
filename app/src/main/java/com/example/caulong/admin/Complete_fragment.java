package com.example.caulong.admin;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.BookingHistory;

import java.util.ArrayList;
import java.util.List;


public class Complete_fragment extends Fragment {
    private RecyclerView recyclerView;
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
        View view = inflater.inflate(R.layout.complete_fragment, container, false);
        helper = new DataDatSan(getContext());
        recyclerView = view.findViewById(R.id.d_rvServices);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookings = getBookingByCourtAndDate();
        BookingAdapter bookingAdapter = new BookingAdapter(bookings);
        recyclerView.setAdapter(bookingAdapter);

        //bookingAdapter.setOnDataChangeListener(this::reloadData); // Thiết lập listener để gọi lại reloadData()
        recyclerView.setAdapter(bookingAdapter);
        return view;
    }
//    public void reloadData() {
//        bookings = getBookingByCourtAndDate();
//        RecyclerView recyclerView = getView().findViewById(R.id.d_rvServices);
//        BookingAdapter adapter = (BookingAdapter) recyclerView.getAdapter();
//        if (adapter != null) {
//            adapter.updateData(bookings);
//        }
//    }
    public ArrayList<BookingHistory> getBookingByCourtAndDate() {
        ArrayList<BookingHistory> bookingHistories = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT b.booking_id,c.customer_id,c.customer_name,b.total_time,b.total_item, b.present_date,b.booking_date, b.status, b.court_id " +
                        "FROM Booking b INNER JOIN Customer c ON c.customer_id = b.customer_id " +
                        "WHERE b.status = ?",
                new String[]{"Hoàn thành"});

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
                List<String> times = helper.getBookingTimes(bookingId);

                bookingHistories.add(new BookingHistory(bookingId,customerId,customerName,total_time,
                        total_item,times, status, presentDate,bookingDate, court_id));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bookingHistories;
    }
}