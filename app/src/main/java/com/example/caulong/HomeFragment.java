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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caulong.booking.Booking_yard;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.menuleft.feedback;
import com.example.caulong.menubottom.Account;
import com.example.caulong.menuleft.support;
import com.example.caulong.user.InfoActivity;
import com.example.caulong.user.LoginActivity;

public class HomeFragment extends Fragment {
    TextView txtGreeting;
    ImageView imgAvatar;
    LinearLayout datSanLayout, dichVuLayout, lichSuLayout, thongTinCaNhanLayout, hoTroLayout, lienHeLayout;
    SQLiteDatabase db;
    DataDatSan helper;

    public HomeFragment() {
        // Required empty public constructor
    }
    void init(View view){
        datSanLayout = view.findViewById(R.id.dat_san);
        dichVuLayout = view.findViewById(R.id.dich_vu);
        lichSuLayout = view.findViewById(R.id.lich_su);
        thongTinCaNhanLayout = view.findViewById(R.id.thong_tin_ca_nhan);
        hoTroLayout = view.findViewById(R.id.ho_tro);
        lienHeLayout = view.findViewById(R.id.lien_he);
        txtGreeting = view.findViewById(R.id.tvGreeting);
        imgAvatar = view.findViewById(R.id.imgAvatar);
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
}
