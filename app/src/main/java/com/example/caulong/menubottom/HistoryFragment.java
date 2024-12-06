package com.example.caulong.menubottom;

import static android.content.Context.MODE_PRIVATE;

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
import android.widget.Toast;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.BookingHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private List<BookingHistory> bookingHistories;
    DataDatSan helper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        if (getContext() != null) {
            helper = new DataDatSan(getContext());
            db = helper.getReadableDatabase();
            SharedPreferences preferences = requireContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
            int customerId = preferences.getInt("customerId", -1);
            if (customerId != -1) {
                bookingHistories = getBookingHistoryByCustomerId(customerId);
            }
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        BookingHistoryAdapter adapter = new BookingHistoryAdapter(bookingHistories);
        recyclerView.setAdapter(adapter);

        adapter.setOnDataChangeListener(this::reloadData); // Thiết lập listener để gọi lại reloadData()
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void reloadData() {
        SharedPreferences preferences = requireContext().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int customerId = preferences.getInt("customerId", -1);
        if (customerId != -1) {
            bookingHistories = getBookingHistoryByCustomerId(customerId);

            // Cập nhật Adapter với danh sách dữ liệu mới
            RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_history);
            BookingHistoryAdapter adapter = (BookingHistoryAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                adapter.updateData(bookingHistories);
            }
        }
    }

    // Hàm lấy lịch sử đặt sân và thời gian đặt sân cho mỗi booking
    public List<BookingHistory> getBookingHistoryByCustomerId(int customerId) {
        List<BookingHistory> bookingHistories = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT booking_id, court_id, present_date, booking_date, status FROM Booking WHERE customer_id = ?",
                new String[]{String.valueOf(customerId)});

        if (cursor.moveToFirst()) {
            do {
                long bookingId = cursor.getLong(cursor.getColumnIndexOrThrow("booking_id"));
                int courtId = cursor.getInt(cursor.getColumnIndexOrThrow("court_id"));
                String courtName = helper.getCourtName(courtId);
                String presentDate = cursor.getString(cursor.getColumnIndexOrThrow("present_date"));
                String bookingDate = cursor.getString(cursor.getColumnIndexOrThrow("booking_date"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                // Lấy danh sách thời gian đã đặt cho booking hiện tại
                List<String> times = helper.getBookingTimes(bookingId);

                bookingHistories.add(new BookingHistory(bookingId, courtName, presentDate, bookingDate, status, times));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bookingHistories;
    }

}