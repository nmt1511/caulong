package com.example.caulong.booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.entities.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailAdapter extends RecyclerView.Adapter<ServiceDetailAdapter.ViewHolder> {
    private ArrayList<Service> serviceList;

    public ServiceDetailAdapter(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.serviceName.setText(service.getService_name());
        holder.quantity.setText(String.valueOf(service.getQuantity()));
        holder.price.setText(String.format("%,.0f", service.getService_price()));
        holder.totalPrice.setText(String.format("%,.0f", service.getTotal_price()));
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName, quantity, totalPrice, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.tvServiceName);
            quantity = itemView.findViewById(R.id.tvQuantity);
            price = itemView.findViewById(R.id.tvServicePrice);
            totalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}

