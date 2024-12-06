package com.example.caulong.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.booking.BookingDetailActivity;
import com.example.caulong.booking.ServiceAdapter;
import com.example.caulong.booking.ServiceDetailAdapter;
import com.example.caulong.booking.ServiceTypeListActivity;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.BookingHistory;
import com.example.caulong.entities.Service;
import com.example.caulong.menubottom.BookingHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<BookingHistory> bookings;
    private DataDatSan dbHelper;

    public BookingAdapter(List<BookingHistory> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingHistory booking = bookings.get(position);
        holder.customerName.setText("Khách hàng: " + booking.getCustomerName());
        holder.presentDate.setText("Ngày đặt sân: " + booking.getPresentdate());
        holder.timeList.setText("Thời gian: " + booking.getTimes());
        holder.totalPrice.setText("Tổng tiền: " + booking.getTotalPrice());
        holder.status.setText("Trạng thái: " + booking.getStatus());
        if (booking.getStatus().equals("Chờ hủy"))
            holder.status.setTextColor(ContextCompat.getColor(holder.status.getContext(), R.color.notice_color));
        else if (booking.getStatus().equals("Đã hủy"))
            holder.status.setTextColor(ContextCompat.getColor(holder.status.getContext(), R.color.red));
        else if(booking.getStatus().equals("Hoàn thành"))
            holder.status.setTextColor(ContextCompat.getColor(holder.status.getContext(), R.color.light_green));
        else
            holder.status.setTextColor(ContextCompat.getColor(holder.status.getContext(), R.color.primary));
        holder.itemView.setOnClickListener(v -> {
            showBookingDetailDialog(v.getContext(), booking);
        });

    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, presentDate, timeList, totalPrice, status;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.ad_tvCustomername);
            presentDate = itemView.findViewById(R.id.ad_tvpresentdate);
            timeList = itemView.findViewById(R.id.ad_tvTimeList);
            totalPrice = itemView.findViewById(R.id.ad_tvTotalPrice);
            status = itemView.findViewById(R.id.ad_tvstatus);
        }
    }

    private void showBookingDetailDialog(Context context, BookingHistory booking) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_bookingdetail, null);
        dialogView.setBackgroundResource(R.drawable.dialog_background);
        builder.setView(dialogView);

        dbHelper = new DataDatSan(context);
        String CourtName = dbHelper.getCourtName(booking.getCourt_id());

        TextView customerName = dialogView.findViewById(R.id.d_tvCustomerName);
        TextView court = dialogView.findViewById(R.id.d_tvCourtName);
        TextView presentDate = dialogView.findViewById(R.id.d_tvPresntDate);
        TextView bookingDate = dialogView.findViewById(R.id.d_tvBookingDate);
        TextView timeList = dialogView.findViewById(R.id.d_tvBookingTimes);
        TextView totalPrice = dialogView.findViewById(R.id.d_tvTotalPrice);
        TextView totalCourtPrice = dialogView.findViewById(R.id.d_tvToTalCourtPrice);
        TextView totalServicePrice = dialogView.findViewById(R.id.d_tvTotalServicePrice);
        Button confirmButton = dialogView.findViewById(R.id.btnConfirmCancel);
        RecyclerView recyclerView_Service = dialogView.findViewById(R.id.d_rvServices);
        recyclerView_Service.setLayoutManager(new LinearLayoutManager(context));

        ArrayList<Service> serviceList = dbHelper.loadBookingServices(booking.getBooking_id());
        ServiceDetailAdapter serviceAdapter = new ServiceDetailAdapter(serviceList);
        recyclerView_Service.setAdapter(serviceAdapter);

        customerName.setText(booking.getCustomerName());
        presentDate.setText(booking.getPresentdate());
        bookingDate.setText(booking.getBookingDate());
        timeList.setText(booking.getTimes().toString());
        court.setText(CourtName.toString());
        totalPrice.setText(String.format("%,.0f",booking.getTotalPrice()));
        totalCourtPrice.setText(String.format("%,.0f",booking.getTotalTime()));
        totalServicePrice.setText(String.format("%,.0f",booking.getTotalItem()));

        if (booking.getStatus().equals("Chờ hủy")) {
            confirmButton.setVisibility(View.VISIBLE);
        }else if (booking.getStatus().equals("Đã đặt")) {
            confirmButton.setText("Complete");
        }
        else {
            confirmButton.setVisibility(View.GONE);
        }

        AlertDialog dialog = builder.create();
        dialog.show();

        confirmButton.setOnClickListener(view -> {
            dbHelper = new DataDatSan(context);
            if(!booking.getStatus().equals("Đã đặt")){
                boolean isUpdated = dbHelper.updateBookingStatus(booking.getBooking_id(), "Đã hủy");
                if (isUpdated) {
                    Toast.makeText(context,"Đã thực hiện yêu cầu hủy sân!", Toast.LENGTH_SHORT).show();
                    if (onDataChangeListener != null) {
                        onDataChangeListener.onDataChanged();
                    }
                } else {
                    Toast.makeText(context, "Hủy thất bại!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }else{
                long id = dbHelper.savePayment(booking.getBooking_id(),context);
                if(id != -1){
                    boolean isUpdated = dbHelper.updateBookingStatus(booking.getBooking_id(), "Hoàn thành");
                    if (isUpdated) {
                        Toast.makeText(context, "Hoàn tất thanh toán!", Toast.LENGTH_SHORT).show();
                        if (onDataChangeListener != null) {
                            onDataChangeListener.onDataChanged();  // Gọi callback để cập nhật dữ liệu
                        }
                    } else {
                        Toast.makeText(context, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Lỗi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void ConfirmComplete(long bookingID, Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View dialogView = inflater.inflate(R.layout.dialog_confirm_cancelbooking, null);
//        builder.setView(dialogView);
//
//        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
//        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);
//        TextView txtTitle = dialogView.findViewById(R.id.tvTitle);
//        TextView txtMessage = dialogView.findViewById(R.id.tvMessage);
//        txtTitle.setText("Xác nhận hoàn thành");
//        txtMessage.setText("Bạn có chắc chắn muốn hoàn thành không?");
//
//        AlertDialog alertDialog = builder.create();
//
//        btnCancel.setOnClickListener(v -> alertDialog.dismiss());
//
//        btnConfirm.setOnClickListener(v -> {
//            DataDatSan dbHelper = new DataDatSan(context);
//            long id = dbHelper.savePayment(bookingID,context);
//            if(id != -1){
//                boolean isUpdated = dbHelper.updateBookingStatus(bookingID, "Hoàn thành");
//                if (isUpdated) {
//                    Toast.makeText(context, "Hoàn tất thanh toán!", Toast.LENGTH_SHORT).show();
//                    if (onDataChangeListener != null) {
//                        onDataChangeListener.onDataChanged();  // Gọi callback để cập nhật dữ liệu
//                    }
//                } else {
//                    Toast.makeText(context, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
//                }
//            }else {
//                Toast.makeText(context, "Lỗi!", Toast.LENGTH_SHORT).show();
//            }
//            alertDialog.dismiss();
//        });
//    }


    public void updateData(List<BookingHistory> newBookingHistories) {
        this.bookings.clear();
        this.bookings.addAll(newBookingHistories);
        notifyDataSetChanged();
    }

    public interface OnDataChangeListener {
        void onDataChanged();
    }

    private BookingHistoryAdapter.OnDataChangeListener onDataChangeListener;

    public void setOnDataChangeListener(BookingHistoryAdapter.OnDataChangeListener listener) {
        this.onDataChangeListener = listener;
    }
}