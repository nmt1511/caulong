package com.example.caulong;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo danh sách lịch sử đặt sân giả
        bookingHistories = new ArrayList<>();
        bookingHistories.add(new BookingHistory("Sân A", "20/10/2024", "14:00", "Đã hoàn tất"));
        bookingHistories.add(new BookingHistory("Sân B", "21/10/2024", "16:00", "Đã hủy"));
        bookingHistories.add(new BookingHistory("Sân C", "22/10/2024", "18:00", "Đã hoàn tất"));
        bookingHistories.add(new BookingHistory("Sân D", "23/10/2024", "10:00", "Đang chờ"));
        bookingHistories.add(new BookingHistory("Sân A", "24/10/2024", "12:00", "Đã hoàn tất"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        BookingHistoryAdapter adapter = new BookingHistoryAdapter(bookingHistories);
        recyclerView.setAdapter(adapter);

        return view;
    }

}