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
        holder.date.setText(bookingHistory.getDate());
        holder.time.setText(bookingHistory.getTime());
        holder.status.setText(bookingHistory.getStatus());
    }

    @Override
    public int getItemCount() {
        return bookingHistories.size();
    }

    static class BookingHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView courtName;
        TextView date;
        TextView time;
        TextView status;

        BookingHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            courtName = itemView.findViewById(R.id.text_view_court_name);
            date = itemView.findViewById(R.id.text_view_date);
            time = itemView.findViewById(R.id.text_view_time);
            status = itemView.findViewById(R.id.text_view_status);
        }
    }
}
