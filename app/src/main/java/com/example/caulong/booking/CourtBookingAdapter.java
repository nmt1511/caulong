package com.example.caulong.booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.entities.BookingHistory;

import java.util.List;

public class CourtBookingAdapter extends RecyclerView.Adapter<CourtBookingAdapter.BookingViewHolder> {
    private List<BookingHistory> bookingList;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(BookingHistory booking);
    }

    public CourtBookingAdapter(List<BookingHistory> bookingList, OnItemClickListener listener) {
        this.bookingList = bookingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_court_dialog, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingHistory booking = bookingList.get(position);
        holder.courtName.setText(booking.getCourtName());
        holder.presentDate.setText("Ngày đặt: " + booking.getPresentdate());
        holder.bookingDate.setText("Ngày chơi: " + booking.getBookingDate());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(booking));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder{
        TextView courtName, presentDate, bookingDate;
        public BookingViewHolder(View itemView){
            super(itemView);
            courtName = itemView.findViewById(R.id.tvCourtName);
            presentDate = itemView.findViewById(R.id.tvPresentDate);
            bookingDate = itemView.findViewById(R.id.tvBookingDate);
        }
    }
}
