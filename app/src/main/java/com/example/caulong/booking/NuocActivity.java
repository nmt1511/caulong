package com.example.caulong.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class NuocActivity extends AppCompatActivity {

    private List<Drink> drinks;
    private TextView tvTotalPrice;
    private Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Khởi tạo danh sách nước uống
        drinks = new ArrayList<>();
        drinks.add(new Drink("Coca Cola", 10000, 0));
        drinks.add(new Drink("Pepsi", 10000, 0));
        drinks.add(new Drink("7Up", 9000, 0));
        drinks.add(new Drink("Sting", 12000, 0));
        drinks.add(new Drink("Trà xanh", 10000, 0));
        drinks.add(new Drink("Bia", 20000, 0));

        // Kết nối RecyclerView với Adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DrinkAdapter adapter = new DrinkAdapter(drinks);
        recyclerView.setAdapter(adapter);

        tvTotalPrice = findViewById(R.id.tv_GiaNuoc);
        btnAddToCart = findViewById(R.id.btn_ThemDrink);

        // Sự kiện nút thêm vào giỏ hàng
        btnAddToCart.setOnClickListener(v -> {
            int totalPrice = calculateTotalPrice();
            tvTotalPrice.setText("Tổng giá: " + NumberFormat.getInstance().format(totalPrice) + " VND");
            // Thêm logic cho việc thêm vào giỏ hàng nếu cần
        });
    }

    // Phương thức tính tổng giá của các nước đã được chọn
    private int calculateTotalPrice() {
        int total = 0;
        for (Drink drink : drinks) {
            total += drink.getPrice() * drink.getQuantity();
        }
        return total;
    }

    // Lớp Drink (dữ liệu cho một loại nước uống)
    private static class Drink {
        private String name;
        private int price;
        private int quantity;

        public Drink(String name, int price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() { return name; }
        public int getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    // Adapter cho RecyclerView
    private class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
        private List<Drink> drinks;

        public DrinkAdapter(List<Drink> drinks) {
            this.drinks = drinks;
        }

        @Override
        public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
            return new DrinkViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DrinkViewHolder holder, int position) {
            Drink drink = drinks.get(position);

            // Định dạng giá tiền thành dạng "10.000"
            String formattedPrice = NumberFormat.getInstance().format(drink.getPrice());

            holder.tvDrink.setText(drink.getName());
            holder.tvPrice.setText(formattedPrice);
            holder.tvQuantity.setText(String.valueOf(drink.getQuantity()));

            // Xử lý sự kiện khi nhấn nút tăng
            holder.btnIncrease.setOnClickListener(v -> {
                drink.setQuantity(drink.getQuantity() + 1);
                holder.tvQuantity.setText(String.valueOf(drink.getQuantity()));
                tvTotalPrice.setText("Tổng giá: " + NumberFormat.getInstance().format(calculateTotalPrice()) + " VND");
            });

            // Xử lý sự kiện khi nhấn nút giảm
            holder.btnDecrease.setOnClickListener(v -> {
                if (drink.getQuantity() > 0) {
                    drink.setQuantity(drink.getQuantity() - 1);
                    holder.tvQuantity.setText(String.valueOf(drink.getQuantity()));
                    tvTotalPrice.setText("Tổng giá: " + NumberFormat.getInstance().format(calculateTotalPrice()) + " VND");
                }
            });
        }

        @Override
        public int getItemCount() {
            return drinks.size();
        }

        public class DrinkViewHolder extends RecyclerView.ViewHolder {
            TextView tvDrink, tvPrice, tvQuantity;
            Button btnIncrease, btnDecrease;

            public DrinkViewHolder(View itemView) {
                super(itemView);
                tvDrink = itemView.findViewById(R.id.tvDrink);
                tvPrice = itemView.findViewById(R.id.tv_price);
                tvQuantity = itemView.findViewById(R.id.tv_quantity);
                btnIncrease = itemView.findViewById(R.id.btn_increase);
                btnDecrease = itemView.findViewById(R.id.btn_decrease);
            }
        }
    }
}
