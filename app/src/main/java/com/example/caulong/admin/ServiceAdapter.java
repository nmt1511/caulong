package com.example.caulong.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.entities.Service;
import com.example.caulong.entities.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private ArrayList<Service> serviceList;

    public ServiceAdapter(ArrayList<Service> ServiceList) {
        this.serviceList = ServiceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_admin, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.name.setText(service.getService_name());
        holder.quantity.setText(String.valueOf(service.getQuantity()));
        holder.price.setText(String.format("%,.0f VND", service.getService_price()));
        holder.type.setText(service.getType_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(v.getContext(), EditServiceActivity.class);
                //intent.putExtra("tournament_id", tournament.getTournamentId());
                bundle.putSerializable("service",service);
                intent.putExtra("data", bundle);
                ((Activity) v.getContext()).startActivityForResult(intent, ServiceList.EDIT_SERVICE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView name, type, quantity, price;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.serviceName);
            type = itemView.findViewById(R.id.servicetype);
            quantity = itemView.findViewById(R.id.servicequantity);
            price = itemView.findViewById(R.id.serviceprice);
        }
    }
}
