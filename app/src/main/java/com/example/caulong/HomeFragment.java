package com.example.caulong;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.caulong.menubottom.Account;
import com.example.caulong.menubottom.AccountFragment;

public class HomeFragment extends Fragment {

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

        // Gán sự kiện click cho từng chức năng
        datSanLayout.setOnClickListener(v -> {
            // Mở activity để đặt sân
            Intent intent = new Intent(getActivity(), Setup_yard.class);
            startActivity(intent);
        });

        dichVuLayout.setOnClickListener(v -> {
            // Mở activity để xem dịch vụ
            Intent intent = new Intent(getActivity(), Setup_yard.class);
            startActivity(intent);
        });

        lichSuLayout.setOnClickListener(v -> {
            // Mở activity để xem lịch sử
            Intent intent = new Intent(getActivity(), Setup_yard.class);
            startActivity(intent);
        });

        thongTinCaNhanLayout.setOnClickListener(v -> {
            // Mở activity để xem thông tin cá nhân
            Intent intent = new Intent(getActivity(), Account.class);
            startActivity(intent);
        });

        hoTroLayout.setOnClickListener(v -> {
            // Mở activity để hỗ trợ
            Intent intent = new Intent(getActivity(), Setup_yard.class);
            startActivity(intent);
        });

        return view;
    }
}
