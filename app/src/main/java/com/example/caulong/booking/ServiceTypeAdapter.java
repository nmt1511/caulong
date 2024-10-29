package com.example.caulong.booking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.entities.ServiceType;

import java.util.List;

public class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.ViewHolder> {

    private List<ServiceType> serviceTypeList;
    private Context context;

    public ServiceTypeAdapter(List<ServiceType> serviceTypeList, Context context) {
        this.serviceTypeList = serviceTypeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return serviceTypeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceTypeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceTypeName = itemView.findViewById(R.id.serviceTypeName);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ServiceType serviceType);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceType serviceType = serviceTypeList.get(position);
        holder.serviceTypeName.setText(serviceType.getType_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(serviceType);
                }
            }
        });
    }

}

