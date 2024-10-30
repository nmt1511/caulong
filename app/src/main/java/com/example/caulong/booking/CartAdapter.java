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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<Service> cartItems;

    public CartAdapter(ArrayList<Service> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Service service = cartItems.get(position);
        holder.txtCartItemName.setText(service.getService_name());
        holder.txtCartItemPrice.setText(String.format("%,.0f VND", service.getService_price()));
        holder.txtCartItemQuantity.setText(String.valueOf(service.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtCartItemName, txtCartItemPrice, txtCartItemQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCartItemName = itemView.findViewById(R.id.txtCartItemName);
            txtCartItemPrice = itemView.findViewById(R.id.txtCartItemPrice);
            txtCartItemQuantity = itemView.findViewById(R.id.txtCartItemQuantity);
        }
    }
}
