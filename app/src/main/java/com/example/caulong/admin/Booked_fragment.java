package com.example.caulong.admin;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.BookingHistory;
import com.example.caulong.entities.court;
import com.example.caulong.menubottom.BookingHistoryAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Booked_fragment extends Fragment {
    private Spinner spinnerSan;
    private EditText edtSelectedDate;
    private RecyclerView recyclerView;
    private String selectedDate = ""; // Lưu ngày đã chọn
    ArrayList<court> CourtList = new ArrayList<court>();
    ArrayAdapter<court> adapter_court;
    private ArrayList<BookingHistory> bookings = new ArrayList<>();
    SQLiteDatabase db;
    DataDatSan helper;
    int posSpinner = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    void init(View view){
        spinnerSan = view.findViewById(R.id.ad_spnCourts);
        edtSelectedDate = view.findViewById(R.id.ad_edtDatePicker);
        recyclerView = view.findViewById(R.id.ad_rvBookingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_booking_management, container, false);
        helper = new DataDatSan(getContext());
        init(view);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        selectedDate = day + "/" + (month+1) + "/" + year;
        edtSelectedDate.setText(selectedDate);
        getCourtList(selectedDate);
        edtSelectedDate.setOnClickListener(v -> {

            // Thiết lập ngày tối thiểu là ngày hôm nay
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (datePickerview, year1, month1, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        edtSelectedDate.setText(selectedDate);
                    }, year, month, day);

            // Thiết lập ngày tối thiểu là ngày hôm nay
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            datePickerDialog.show();
        });

        spinnerSan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                posSpinner = i;
                int courtId = Integer.parseInt(CourtList.get(posSpinner).getCourt_id());
                bookings = getBookingByCourtAndDate(selectedDate,courtId);
                BookingAdapter bookingAdapter = new BookingAdapter(bookings);
                recyclerView.setAdapter(bookingAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtSelectedDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                getCourtList(selectedDate);
                if (posSpinner >= 0 && posSpinner < CourtList.size()) {
                    int courtId = Integer.parseInt(CourtList.get(posSpinner).getCourt_id());
                    bookings = getBookingByCourtAndDate(selectedDate, courtId);
                    BookingAdapter bookingAdapter = new BookingAdapter(bookings);
                    recyclerView.setAdapter(bookingAdapter);
                }
            }
        });
        return view;
    }
    private void getCourtList(String date) {
        try {
            CourtList.clear();
            db = helper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT c.* FROM Booking b " +
                            "INNER JOIN Court c ON c.court_id = b.court_id " +
                            "WHERE b.booking_date = ?",
                    new String[]{date});
            if (c.getCount() > 0) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    CourtList.add(new court(c.getInt(0) + "", c.getString(1).toString(),
                            c.getDouble(2), c.getString(3).toString()));
                    c.moveToNext();
                }
            } else {
                Toast.makeText(getContext(), "Không có sân được đặt", Toast.LENGTH_SHORT).show();
            }
            c.close();
            db.close();
            adapter_court = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, CourtList);
            adapter_court.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spinnerSan.setAdapter(adapter_court);
            adapter_court.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public ArrayList<BookingHistory> getBookingByCourtAndDate(String date, int courtid) {
        ArrayList<BookingHistory> bookingHistories = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT b.booking_id,c.customer_id,c.customer_name,b.total_time,b.total_item, b.present_date,b.booking_date,b.status, b.court_id " +
                        "FROM Booking b INNER JOIN Customer c ON c.customer_id = b.customer_id " +
                        "WHERE b.court_id = ? and b.booking_date = ? and b.status = ?",
                new String[]{String.valueOf(courtid),date,"Đã đặt"});

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
                int court_Id = cursor.getInt(8);

                // Lấy danh sách thời gian đã đặt cho booking hiện tại
                List<String> times = getBookingTimes(bookingId);

                bookingHistories.add(new BookingHistory(bookingId,customerId,customerName,total_time,
                        total_item,times, status, presentDate,bookingDate,court_Id));
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
