package com.example.caulong.menubottom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.BookingHistoryViewHolder> {

    private List<BookingHistory> bookingHistories;

    public BookingHistoryAdapter(List<BookingHistory> bookingHistories) {
        this.bookingHistories = bookingHistories;
    }

    @NonNull
    @Override
    public BookingHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_history, parent, false);
        return new BookingHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHistoryViewHolder holder, int position) {
        BookingHistory bookingHistory = bookingHistories.get(position);
        holder.courtName.setText(bookingHistory.getCourtName());
        holder.present_date.setText("Ngày đặt sân: " +bookingHistory.getPresentdate());
        holder.booking_date.setText("Ngày chơi: "+ bookingHistory.getBookingDate());
        // Hiển thị danh sách thời gian đã đặt
        StringBuilder timesText = new StringBuilder();
        for (String time : bookingHistory.getTimes()) {
            timesText.append(time).append(", ");
        }
        holder.time.setText("Thời gian đã đặt: " + (timesText.length() > 0 ? timesText.substring(0, timesText.length() - 2) : ""));
        holder.status.setText("Tình trạng: "+bookingHistory.getStatus());
    }

    @Override
    public int getItemCount() {
        return bookingHistories.size();
    }

    static class BookingHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView courtName;
        TextView present_date;
        TextView booking_date;
        TextView time;
        TextView status;

        BookingHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            courtName = itemView.findViewById(R.id.text_view_court_name);
            present_date = itemView.findViewById(R.id.text_view_presentdate);
            booking_date = itemView.findViewById(R.id.text_view_bookingdate);
            time = itemView.findViewById(R.id.text_view_time);
            status = itemView.findViewById(R.id.text_view_status);
        }
    }
}
