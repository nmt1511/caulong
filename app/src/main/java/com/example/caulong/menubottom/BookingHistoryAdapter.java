package com.example.caulong.menubottom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.booking.BookingDetailActivity;
import com.example.caulong.booking.Booking_yard;
import com.example.caulong.booking.ServiceTypeListActivity;

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

    public class BookingHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView courtName;
        TextView present_date;
        TextView booking_date;
        TextView time;
        TextView status;

        BookingHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(view -> {
                // Hiển thị PopupMenu khi nhấn giữ
                showPopupMenu(view, getAdapterPosition());
                return true;
            });
            courtName = itemView.findViewById(R.id.text_view_court_name);
            present_date = itemView.findViewById(R.id.text_view_presentdate);
            booking_date = itemView.findViewById(R.id.text_view_bookingdate);
            time = itemView.findViewById(R.id.text_view_time);
            status = itemView.findViewById(R.id.text_view_status);
        }
        private void showPopupMenu(View view, int position) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_booking_options, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> handleMenuClick(item, position));
            popupMenu.show();
        }
        private boolean handleMenuClick(MenuItem item, int position) {
            long bookingId = bookingHistories.get(position).getBooking_id();
            Context context = itemView.getContext();
            if(item.getItemId() == R.id.menu_view_details){
                Intent intent = new Intent(context, BookingDetailActivity.class);
                intent.putExtra("booking_id", bookingId);
                context.startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.menu_view_addService) {
                Intent intent = new Intent(context, ServiceTypeListActivity.class);
                intent.putExtra("booking_id",bookingId);
                context.startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.menu_delete) {
                Toast.makeText(context, "Xóa booking ID: " + bookingId, Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }
}
