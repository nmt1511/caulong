package com.example.caulong.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.caulong.R;

import java.util.ArrayList;

public class HourAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<String> hours; // Danh sách giờ
    private final ArrayList<String> selectedHours; // Giờ đã chọn

    // Constructor nhận context và danh sách giờ
    public HourAdapter(Context context, ArrayList<String> hours) {
        this.context = context;
        this.hours = hours;
        this.selectedHours = new ArrayList<>();
    }

    // Số lượng phần tử trong GridView (tương đương số ô hiển thị)
    @Override
    public int getCount() {
        return hours.size();
    }

    // Lấy phần tử tại một vị trí cụ thể
    @Override
    public Object getItem(int position) {
        return hours.get(position);
    }

    // Trả về ID của phần tử tại vị trí cụ thể
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Tạo hoặc tái sử dụng View cho từng ô trong GridView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Nạp giao diện của từng ô (item)
            convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // Lấy TextView để hiển thị giờ
        TextView textView = (TextView) convertView;
        textView.setText(hours.get(position)); // Hiển thị giờ

        // Đổi màu nếu giờ đã được chọn
        if (selectedHours.contains(hours.get(position))) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.primary_300));
            textView.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            textView.setBackgroundColor(context.getResources().getColor(R.color.off_white));
            textView.setTextColor(context.getResources().getColor(R.color.text_primary));
        }

        // Xử lý sự kiện khi người dùng click vào ô giờ
        convertView.setOnClickListener(v -> {
            String selectedHour = hours.get(position);
            if (selectedHours.contains(selectedHour)) {
                selectedHours.remove(selectedHour); // Bỏ chọn giờ nếu đã chọn trước đó
            } else {
                selectedHours.add(selectedHour); // Thêm giờ vào danh sách đã chọn
            }
            notifyDataSetChanged(); // Cập nhật giao diện
        });

        return convertView;
    }

    // Trả về danh sách giờ đã chọn
    public ArrayList<String> getSelectedHours() {
        return selectedHours;
    }
}

