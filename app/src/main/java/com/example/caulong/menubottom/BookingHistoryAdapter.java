package com.example.caulong.menubottom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.booking.BookingDetailActivity;
import com.example.caulong.booking.ServiceTypeListActivity;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.BookingHistory;

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
        if(bookingHistory.getStatus().equals("Chờ hủy"))
            holder.status.setTextColor(ContextCompat.getColor(holder.status.getContext(), R.color.notice_color));
        else if(bookingHistory.getStatus().equals("Đã hủy"))
            holder.status.setTextColor(ContextCompat.getColor(holder.status.getContext(), R.color.red));
        else
            holder.status.setTextColor(ContextCompat.getColor(holder.status.getContext(), R.color.primary));

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
                ConfirmCancel(bookingId, context);
                return true;
            }
            return false;
        }
    }

    private void ConfirmCancel(long bookingID, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_confirm_cancelbooking, null);
        builder.setView(dialogView);

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);

        AlertDialog alertDialog = builder.create();

        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        btnConfirm.setOnClickListener(v -> {
            DataDatSan dbHelper = new DataDatSan(context);
            boolean isUpdated = dbHelper.updateBookingStatus(bookingID, "Chờ hủy");
            if (isUpdated) {
                Toast.makeText(context,"Đang trong danh sách chờ hủy!", Toast.LENGTH_SHORT).show();
                if (onDataChangeListener != null) {
                    onDataChangeListener.onDataChanged();  // Gọi callback để cập nhật dữ liệu
                }
            } else {
                Toast.makeText(context, "Hủy thất bại!", Toast.LENGTH_SHORT).show();
            }
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    public void updateData(List<BookingHistory> newBookingHistories) {
        this.bookingHistories.clear();
        this.bookingHistories.addAll(newBookingHistories);
        notifyDataSetChanged();
    }

    public interface OnDataChangeListener {
        void onDataChanged();
    }

    private OnDataChangeListener onDataChangeListener;

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.onDataChangeListener = listener;
    }


}
