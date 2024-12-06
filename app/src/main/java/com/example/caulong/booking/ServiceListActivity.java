package com.example.caulong.booking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.MainActivity;
import com.example.caulong.R;
import com.example.caulong.admin.DetailCustomerActivity;
import com.example.caulong.admin.EditCustomerActivity;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Service;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class ServiceListActivity extends AppCompatActivity implements ServiceAdapter.OnQuantityChangeListener{
    private int typeid;
    private long booking_id;
    private TextView tvTotalPrice;
    private Button btnAddToCart, btnshowCart;
    private RecyclerView recyclerView;
    private TextView txtTitleService;
    private ServiceAdapter serviceAdapter;
    private ArrayList<Service> serviceList = new ArrayList<>();
    //tạo 1 list tạm thời để lưu các sp vào giỏ
    private ArrayList<Service> cartList = CartManager.getInstance().getCartList();
    DataDatSan helper = new DataDatSan(this);
    SQLiteDatabase db;

    void init(){
        tvTotalPrice = findViewById(R.id.tv_totalPrice);
        btnAddToCart = findViewById(R.id.btn_AddtoCart);
        txtTitleService = findViewById(R.id.txtTitleService);
        recyclerView = findViewById(R.id.recyclerViewItem);
        btnshowCart = findViewById(R.id.btn_ShowCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        typeid = getIntent().getIntExtra("type_id",-1);
        booking_id = getIntent().getLongExtra("booking_id",-1);
        init();
        loadItems(typeid);
        updateTotalPrice();
        btnAddToCart.setOnClickListener(v ->{
            for (Service service : serviceList) {
                if (service.getQuantity() > 0) {
                    CartManager.getInstance().addService(service);
                }
            }
            Toast.makeText(ServiceListActivity.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            // Đặt lại số lượng trong serviceList về 0
            for (Service service : serviceList) {
                service.setQuantity(0);
            }
            // Cập nhật giao diện RecyclerView
            serviceAdapter.notifyDataSetChanged();
        });
        btnshowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCartDialog();
            }
        });
    }

    @Override
    public void onQuantityChanged() {
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalCartPrice();
        for(Service service : serviceList){
            if(service.getQuantity() > 0)
                totalPrice += service.getService_price() * service.getQuantity();
        }
        tvTotalPrice.setText("Giá: "+ String.format("%,.0f", totalPrice));
    }

    private void loadItems(int typeId) {
        db = helper.getReadableDatabase();
        String type_name="";
        Cursor c= db.rawQuery("SELECT * FROM Service_type WHERE type_id = ?", new String[]{String.valueOf(typeId)});
        if(c.moveToFirst()){
            type_name = c.getString(1);
        }
        txtTitleService.setText(type_name.toString());

        String query = "SELECT * FROM Service WHERE type_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(typeId)});

        if (cursor.moveToFirst()) {
            do {
                int itemId = cursor.getInt(0);
                String itemName = cursor.getString(1);
                double itemprice = cursor.getDouble(4);
                // Thêm sản phẩm vào danh sách
                serviceList.add(new Service(itemId, itemName, itemprice));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        serviceAdapter = new ServiceAdapter(serviceList,this,this);
        recyclerView.setAdapter(serviceAdapter);
    }

    private void showCartDialog(){
        //Tạo BottomSheetDialog cho giỏ hàng
        BottomSheetDialog cartDialog = new BottomSheetDialog(this);
        View cartView = LayoutInflater.from(this).inflate(R.layout.cart_layout, null);
        cartDialog.setContentView(cartView);

        RecyclerView recyclerViewCartItems = cartView.findViewById(R.id.recyclerViewCartItems);
        TextView txtTotalCartPrice = cartView.findViewById(R.id.txtTotalCartPrice);
        Button btnConfirmOrder = cartView.findViewById(R.id.btnConfirmOrder);

        // Thiết lập RecyclerView cho các mục trong giỏ hàng
        CartAdapter cartAdapter = new CartAdapter(cartList);
        recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCartItems.setAdapter(cartAdapter);

        // Tính tổng giá cho giỏ hàng
        double totalCartPrice = calculateTotalCartPrice();
        txtTotalCartPrice.setText(String.format("Tổng tiền: %,.0f VND", totalCartPrice));

        // xác nhận đặt hàng
        btnConfirmOrder.setOnClickListener(v -> {
            if(!cartList.isEmpty()){
                confirmOrder();
                cartDialog.dismiss();
            }
            else{
                Toast.makeText(ServiceListActivity.this, "Giỏ hàng trống, vui lòng chọn sản phẩm.", Toast.LENGTH_SHORT).show();
            }
        });

        cartDialog.show();
    }
    private double calculateTotalCartPrice() {
        double total = 0;
        for (Service service : cartList) {
            total += service.getService_price() * service.getQuantity();
        }
        return total;
    }
//    private int getbooking_id(){
//        db = helper.getReadableDatabase();
//        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//        int customerId = preferences.getInt("customerId", -1);
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        String currentDate = day + "/" + month + "/" + year;
//        //lấy booking_id
//        Cursor cursor = db.rawQuery(
//                "SELECT booking_id FROM Booking WHERE customer_id = ? AND present_date = ?",
//                new String[]{String.valueOf(customerId),currentDate}
//        );
//        if (cursor.moveToFirst()) {
//            int bookingId = cursor.getInt(0);
//            return bookingId;
//        }
//        cursor.close();
//        return -1;
//    }
    private void confirmOrder() {
        db = helper.getReadableDatabase();
        double totalItem=0;
        long bookingId = booking_id;
        for (Service service : cartList) {
            if (service.getQuantity() > 0) {
                int serviceId = service.getService_id();
                int quantity = service.getQuantity();
                double totalPrice = service.getService_price() * quantity;

                // Truy vấn số lượng hiện tại từ cơ sở dữ liệu
                String selectQuery = "SELECT quantity FROM Service WHERE service_id = ?";
                Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(serviceId)});
                if (cursor.moveToFirst()) {
                    int currentQuantity = cursor.getInt(0); // Lấy số lượng hiện tại

                    // Tính toán số lượng mới
                    int newQuantity = currentQuantity - quantity;
                    if (newQuantity < 0) {
                        newQuantity = 0; // Đảm bảo số lượng không âm
                    }

                    // Cập nhật số lượng mới vào bảng Service
                    String updateServiceQuery = "UPDATE Service SET quantity = ? WHERE service_id = ?";
                    db.execSQL(updateServiceQuery, new Object[]{newQuantity, serviceId});
                }
                cursor.close();

                String query = "INSERT INTO Booking_service (booking_id, service_id, quantity, total_price) VALUES (?, ?, ?, ?)";
                db.execSQL(query, new Object[]{bookingId, serviceId, quantity, totalPrice});
                totalItem += totalPrice;
            }
        }
        String updateQuery = "UPDATE Booking SET total_item = ? WHERE booking_id = ?";
        db.execSQL(updateQuery, new Object[]{totalItem, bookingId});
        Toast.makeText(this, "Đơn hàng đã được xác nhận!", Toast.LENGTH_SHORT).show();
        CartManager.getInstance().clearCart();// Đặt lại số lượng trong serviceList về 0
        for (Service service : serviceList) {
            service.setQuantity(0);
        }
        // Cập nhật giao diện RecyclerView
        serviceAdapter.notifyDataSetChanged();
        // Cập nhật tổng giá trị hiển thị về 0
        tvTotalPrice.setText("Giá: 0 VND");
        Intent intent = new Intent(ServiceListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
