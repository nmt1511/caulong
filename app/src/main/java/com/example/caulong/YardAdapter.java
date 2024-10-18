package com.example.caulong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class YardAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> yards;

    public YardAdapter(Context context, List<String> yards) {
        super(context, R.layout.yard_item, yards);
        this.context = context;
        this.yards = yards;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.yard_item, parent, false);

        // Lấy thành phần giao diện
        ImageView imgYard = rowView.findViewById(R.id.imgYard);
        TextView tvYardNumber = rowView.findViewById(R.id.tvYardNumber);

        // Cập nhật dữ liệu
        tvYardNumber.setText(yards.get(position));

        return rowView;
    }
}
