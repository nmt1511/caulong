package com.example.caulong.admin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.entities.Customer;
import com.example.caulong.entities.tournament;

import java.io.Serializable;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.TournamentViewHolder> {
    private List<Customer> customerList;

    public CustomerAdapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.name.setText(customer.getCustomer_name());
        String avatarPath = customer.getAvatar();
        if (avatarPath != null) {
            Bitmap avatar = BitmapFactory.decodeFile(avatarPath);
            // Hiển thị ảnh từ đường dẫn
            holder.avatar.setImageBitmap(avatar);
        }
        else{
            holder.avatar.setImageResource(R.drawable.ic_profile);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(v.getContext(), DetailCustomerActivity.class);
                bundle.putSerializable("customer", customer);
                intent.putExtra("data", bundle);
                ((Activity) v.getContext()).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class TournamentViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView avatar;

        public TournamentViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_Customername);
            avatar = itemView.findViewById(R.id.imgAvatar);
        }
    }
}
