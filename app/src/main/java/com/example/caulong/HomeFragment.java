package com.example.caulong;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caulong.admin.BookingAdapter;
import com.example.caulong.booking.Booking_yard;
import com.example.caulong.booking.CourtBookingAdapter;
import com.example.caulong.booking.ServiceTypeListActivity;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.BookingHistory;
import com.example.caulong.menubottom.HistoryFragment;
import com.example.caulong.menuleft.feedback;
import com.example.caulong.menubottom.Account;
import com.example.caulong.menuleft.support;
import com.example.caulong.user.InfoActivity;
import com.example.caulong.user.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView txtGreeting, txtTournamentname, txtStartDate, txtEndDate, txtdecription;
    ImageView imgAvatar;
    LinearLayout datSanLayout, dichVuLayout, thongTinCaNhanLayout, hoTroLayout, lienHeLayout;
    SQLiteDatabase db;
    DataDatSan helper;

    public HomeFragment() {
        // Required empty public constructor
    }
    void init(View view){
        datSanLayout = view.findViewById(R.id.dat_san);
        dichVuLayout = view.findViewById(R.id.dich_vu);
        thongTinCaNhanLayout = view.findViewById(R.id.thong_tin_ca_nhan);
        hoTroLayout = view.findViewById(R.id.ho_tro);
        lienHeLayout = view.findViewById(R.id.lien_he);
        txtGreeting = view.findViewById(R.id.tvGreeting);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        txtTournamentname = view.findViewById(R.id.TournamentName);
        txtStartDate = view.findViewById(R.id.startDate);
        txtEndDate = view.findViewById(R.id.endDate);
        txtdecription = view.findViewById(R.id.txtdecription);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        helper = new DataDatSan(getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        int CustomerId = preferences.getInt("customerId", -1);
        ShowNewTournament();
        if (userId != -1) {
            db = helper.getReadableDatabase();
            String welcome="";
            Cursor c= db.rawQuery("SELECT * FROM Customer WHERE user_id = ?", new String[]{String.valueOf(userId)});
            if(c.moveToFirst()){
                welcome = c.getString(1);
            }
            txtGreeting.setText("Hello,\n"+welcome.toString());
            helper.loadAvatarFromDatabase(imgAvatar, CustomerId);
        } else {
            Toast.makeText(getContext(), "Vui lòng đăng nhập để đặt sân!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        // Gán sự kiện click cho từng chức năng
        datSanLayout.setOnClickListener(v -> {
            // Mở activity để đặt sân
            Intent intent = new Intent(getActivity(), Booking_yard.class);
            startActivity(intent);
        });

        dichVuLayout.setOnClickListener(v -> {
            showBookingDialog(CustomerId);
        });

        thongTinCaNhanLayout.setOnClickListener(v -> {
            // Mở activity để xem thông tin cá nhân
            Intent intent = new Intent(getActivity(), InfoActivity.class);
            startActivity(intent);
        });

        hoTroLayout.setOnClickListener(v -> {
            // Mở activity để hỗ trợ
            Intent intent = new Intent(getActivity(), support.class);
            startActivity(intent);
        });
        lienHeLayout.setOnClickListener(v -> {
            // Mở activity để liên hệ
            Intent intent = new Intent(getActivity(), feedback.class);
            startActivity(intent);
        });

        return view;
    }
    public void ShowNewTournament() {
        // Mở cơ sở dữ liệu ở chế độ đọc
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT tournament_id, name, start_date, end_date, description " +
                        "FROM Tournament " +
                        "ORDER BY start_date DESC LIMIT 1",
                null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
            String endDate = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

            txtTournamentname.setText(name);
            txtStartDate.setText(startDate);
            txtEndDate.setText(endDate);
            txtdecription.setText(description);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }
    private void showBookingDialog(int customerId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_booking_list, null);
        builder.setView(view);

        RecyclerView rvBookings = view.findViewById(R.id.rvBooking);
        rvBookings.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy dữ liệu từ cơ sở dữ liệu
        List<BookingHistory> bookingList = getBookingListByCustomerId(customerId);

        CourtBookingAdapter adapter = new CourtBookingAdapter(bookingList, booking -> {
            Intent intent = new Intent(getContext(), ServiceTypeListActivity.class);
            intent.putExtra("booking_id", booking.getBooking_id());
            startActivity(intent);
        });

        rvBookings.setAdapter(adapter);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private List<BookingHistory> getBookingListByCustomerId(int customerId) {
        List<BookingHistory> bookingList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT b.booking_id, c.court_name, b.present_date, b.booking_date " +
                "FROM Booking b JOIN Court c ON b.court_id = c.court_id " +
                "WHERE b.customer_id = ? and b.status = ?", new String[]{String.valueOf(customerId),"Đã đặt"});
        if (cursor.moveToFirst()) {
            do {
                bookingList.add(new BookingHistory(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookingList;
    }

}
