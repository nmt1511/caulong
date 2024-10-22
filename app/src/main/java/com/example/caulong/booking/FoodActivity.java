package com.example.caulong.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;
    private TextView tvGiaFood;
    private Button btnThemFood;
    private List<Food> cart = new ArrayList<>(); // Danh sách giỏ hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách món ăn
        foodList = new ArrayList<>();
        foodList.add(new Food("Bánh mì", 15000, 0));
        foodList.add(new Food("Phở", 30000, 0));
        foodList.add(new Food("Pizza", 50000, 0));
        foodList.add(new Food("Mì gói trứng", 15000, 0));
        foodList.add(new Food("Bánh bao chay", 10000, 0));
        foodList.add(new Food("Bánh bao thịt", 20000, 0));
        foodList.add(new Food("Hủ tiếu", 20000, 0));
        foodList.add(new Food("Bún thịt nướng", 20000, 0));

        foodAdapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(foodAdapter);

        tvGiaFood = findViewById(R.id.tv_GiaFood);
        btnThemFood = findViewById(R.id.btn_ThemFood);

        // Xử lý khi bấm nút thêm vào giỏ hàng
        btnThemFood.setOnClickListener(v -> addToCart());
    }

    // Phương thức tính tổng giá của các món đã được thêm
    private int calculateTotalPrice() {
        int total = 0;
        for (Food food : foodList) {
            total += food.getPrice() * food.getQuantity();
        }
        return total;
    }

    // Phương thức cập nhật tổng giá
    private void updateTotalPrice() {
        int totalPrice = calculateTotalPrice();
        tvGiaFood.setText("Giá đồ ăn: " + String.format("%,d", totalPrice) + " VND");
    }

    // Phương thức thêm món ăn vào giỏ hàng
    private void addToCart() {
        cart.clear(); // Xóa giỏ hàng hiện tại để chỉ thêm những món có số lượng lớn hơn 0
        for (Food food : foodList) {
            if (food.getQuantity() > 0) {
                cart.add(food);
            }
        }

        // Hiển thị thông báo hoặc cập nhật giao diện giỏ hàng tại đây
        if (cart.size() > 0) {
            // Ví dụ: Hiển thị thông báo món đã được thêm vào giỏ hàng
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Chưa có món nào được chọn", Toast.LENGTH_SHORT).show();
        }
    }

    // Lớp đại diện cho món ăn
    public static class Food {
        private String name;
        private int price;
        private int quantity;

        public Food(String name, int price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // Adapter cho RecyclerView
    public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

        private List<Food> foodList;

        public FoodAdapter(List<Food> foodList) {
            this.foodList = foodList;
        }

        @NonNull
        @Override
        public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_food, parent, false);
            return new FoodViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
            Food food = foodList.get(position);
            holder.tvFood.setText(food.getName());

            // Định dạng giá tiền với dấu chấm cho hàng nghìn
            String formattedPrice = String.format("%,d", food.getPrice());
            holder.tvPrice.setText(formattedPrice);

            holder.tvQuantity.setText(String.valueOf(food.getQuantity()));

            // Gán sự kiện cho nút giảm số lượng
            holder.btnDecrease.setOnClickListener(v -> {
                if (food.getQuantity() > 0) {
                    food.setQuantity(food.getQuantity() - 1);
                    holder.tvQuantity.setText(String.valueOf(food.getQuantity()));
                    // Cập nhật tổng giá sau khi thay đổi số lượng
                    updateTotalPrice();
                }
            });

            // Gán sự kiện cho nút tăng số lượng
            holder.btnIncrease.setOnClickListener(v -> {
                food.setQuantity(food.getQuantity() + 1);
                holder.tvQuantity.setText(String.valueOf(food.getQuantity()));
                // Cập nhật tổng giá sau khi thay đổi số lượng
                updateTotalPrice();
            });
        }

        @Override
        public int getItemCount() {
            return foodList.size();
        }

        class FoodViewHolder extends RecyclerView.ViewHolder {
            TextView tvFood, tvPrice, tvQuantity;
            Button btnDecrease, btnIncrease;

            public FoodViewHolder(@NonNull View itemView) {
                super(itemView);
                tvFood = itemView.findViewById(R.id.tvFood);
                tvPrice = itemView.findViewById(R.id.tv_price);
                tvQuantity = itemView.findViewById(R.id.tv_quantity);
                btnDecrease = itemView.findViewById(R.id.btn_decrease);
                btnIncrease = itemView.findViewById(R.id.btn_increase);
            }
        }
    }
}
