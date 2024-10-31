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

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
            bookingHistories = getBookingHistoryByCustomerId(customerId);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        BookingHistoryAdapter adapter = new BookingHistoryAdapter(bookingHistories);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Lấy danh sách thời gian đã đặt cho một booking cụ thể
    public List<String> getBookingTimes(int bookingId) {
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
    // Lấy tên sân dựa trên court_id
    public String getCourtName(int courtId) {
        String courtName = "";
        db = helper.getReadableDatabase();

        String query = "SELECT court_name FROM Court WHERE court_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courtId)});

        if (cursor.moveToFirst()) {
            courtName = cursor.getString(cursor.getColumnIndexOrThrow("court_name"));
        }
        cursor.close();
        db.close();

        return courtName;
    }

    // Hàm lấy lịch sử đặt sân và thời gian đặt sân cho mỗi booking
    public List<BookingHistory> getBookingHistoryByCustomerId(int customerId) {
        List<BookingHistory> bookingHistories = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT booking_id, court_id, present_date, booking_date, status FROM Booking WHERE customer_id = ?",
                new String[]{String.valueOf(customerId)});

        if (cursor.moveToFirst()) {
            do {
                int bookingId = cursor.getInt(cursor.getColumnIndexOrThrow("booking_id"));
                int courtId = cursor.getInt(cursor.getColumnIndexOrThrow("court_id"));
                String courtName = getCourtName(courtId);
                String presentDate = cursor.getString(cursor.getColumnIndexOrThrow("present_date"));
                String bookingDate = cursor.getString(cursor.getColumnIndexOrThrow("booking_date"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                // Lấy danh sách thời gian đã đặt cho booking hiện tại
                List<String> times = getBookingTimes(bookingId);

                bookingHistories.add(new BookingHistory(bookingId, courtName, presentDate, bookingDate, status, times));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return bookingHistories;
    }

}