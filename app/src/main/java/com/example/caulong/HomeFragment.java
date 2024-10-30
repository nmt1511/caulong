package com.example.caulong;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caulong.booking.Booking_yard;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.feedback.feedback;
import com.example.caulong.menubottom.Account;
import com.example.caulong.menubottom.AccountFragment;
import com.example.caulong.support.support;
import com.example.caulong.user.LoginActivity;

public class HomeFragment extends Fragment {
    TextView txtGreeting;
    SQLiteDatabase db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Khởi tạo các LinearLayout
        LinearLayout datSanLayout = view.findViewById(R.id.dat_san);
        LinearLayout dichVuLayout = view.findViewById(R.id.dich_vu);
        LinearLayout lichSuLayout = view.findViewById(R.id.lich_su);
        LinearLayout thongTinCaNhanLayout = view.findViewById(R.id.thong_tin_ca_nhan);
        LinearLayout hoTroLayout = view.findViewById(R.id.ho_tro);
        LinearLayout lienHeLayout = view.findViewById(R.id.lien_he);
        txtGreeting = view.findViewById(R.id.tvGreeting);
        SharedPreferences preferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        if (userId != -1) {
            DataDatSan helper = new DataDatSan(getContext());
            db = helper.getReadableDatabase();
            String welcome="";
            Cursor c= db.rawQuery("SELECT * FROM Customer WHERE user_id = ?", new String[]{String.valueOf(userId)});
            if(c.moveToFirst()){
                welcome = c.getString(1);
            }
            txtGreeting.setText("Hello,\n"+welcome.toString());
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
            // Mở activity để xem dịch vụ
            Intent intent = new Intent(getActivity(), Booking_yard.class);
            startActivity(intent);
        });

        lichSuLayout.setOnClickListener(v -> {
            // Mở activity để xem lịch sử
            Intent intent = new Intent(getActivity(), Booking_yard.class);
            startActivity(intent);
        });

        thongTinCaNhanLayout.setOnClickListener(v -> {
            // Mở activity để xem thông tin cá nhân
            Intent intent = new Intent(getActivity(), Account.class);
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
}
