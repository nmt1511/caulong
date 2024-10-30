package com.example.caulong.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.entities.Service;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }
    private ArrayList<Service> ServiceList;
    private Context context;
    private OnQuantityChangeListener onQuantityChangeListener;

    public ServiceAdapter(ArrayList<Service> serviceList, Context context, OnQuantityChangeListener listener){
        this.ServiceList = serviceList;
        this.context = context;
        this.onQuantityChangeListener = listener;
    }

    @Override
    public int getItemCount() {
        return ServiceList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = ServiceList.get(position);
        holder.itemName.setText(service.getService_name());
        holder.itemPrice.setText(String.format("%,.0f", service.getService_price()));
        // Hiển thị số lượng hiện tại
        holder.tvQuantity.setText(String.valueOf(service.getQuantity()));

        // Thiết lập sự kiện tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            int currentQuantity = service.getQuantity();
            service.setQuantity(currentQuantity + 1);
            holder.tvQuantity.setText(String.valueOf(service.getQuantity()));
            onQuantityChangeListener.onQuantityChanged();
        });

        // Thiết lập sự kiện giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = service.getQuantity();
            if (currentQuantity > 0) {
                service.setQuantity(currentQuantity - 1);
                holder.tvQuantity.setText(String.valueOf(service.getQuantity()));
                onQuantityChangeListener.onQuantityChanged();
            }
        });
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }
    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, tvQuantity;
        Button btnDecrease, btnIncrease;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.txtItemName);
            itemPrice = itemView.findViewById(R.id.txt_ItemPrice);
            tvQuantity = itemView.findViewById(R.id.txt_quantity);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
        }
    }
}
