package com.example.caulong.menubottom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.caulong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private List<CustomNotification> notifications;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo danh sách thông báo giả
        notifications = new ArrayList<>();
        notifications.add(new CustomNotification("Đặt sân thành công", "Bạn đã đặt sân cầu lông vào ngày 20/10/2024 lúc 14:00.", "20/10/2024 12:00"));
        notifications.add(new CustomNotification("Cập nhật lịch thi đấu", "Lịch thi đấu của bạn đã được cập nhật. Vui lòng kiểm tra lại.", "19/10/2024 09:00"));
        notifications.add(new CustomNotification("Thông báo hủy đặt sân", "Đặt sân vào ngày 21/10/2024 đã bị hủy. Vui lòng xem lại thông tin.", "19/10/2024 08:30"));
        notifications.add(new CustomNotification("Giảm giá đặc biệt", "Nhận ngay ưu đãi giảm giá 20% cho tất cả các đơn đặt sân trong tuần này!", "18/10/2024 15:00"));
        notifications.add(new CustomNotification("Nhắc nhở đặt sân", "Bạn chưa đặt sân cho tuần này. Hãy nhanh tay đặt sân ngay nhé!", "17/10/2024 18:00"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        NotificationAdapter adapter = new NotificationAdapter(notifications);
        recyclerView.setAdapter(adapter);

        return view;
    }

}