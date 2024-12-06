package com.example.caulong.admin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.caulong.MainActivity;
import com.example.caulong.R;
import com.example.caulong.booking.Booking_yard;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.user.LogoutActivity;

public class AdminActivity extends Activity {

    private LinearLayout datSanLayout, logout, tournament,service,customer,rating;
    private TextView txtGreeting;
    SQLiteDatabase db;
    DataDatSan helper = new DataDatSan(this);

    void init(){
        datSanLayout = findViewById(R.id.QL_datsan);
        logout = findViewById(R.id.dang_xuat);
        tournament = findViewById(R.id.giai_dau);
        txtGreeting = findViewById(R.id.ad_tvGreeting);
        service = findViewById(R.id.dich_vu);
        customer = findViewById(R.id.Customer);
        rating = findViewById(R.id.rating);
    }

    void initListener(){
        datSanLayout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, BookingYard_Management.class);
            startActivity(intent);
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutActivity.showExitConfirmationDialog(AdminActivity.this);
            }
        });
        tournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, Tournament_List.class);
                startActivity(intent);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, ServiceList.class);
                startActivity(intent);
            }
        });
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, RatingActivity.class);
                startActivity(intent);
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, Customer_List.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_admin);
        db = helper.getReadableDatabase();
        init();
        initListener();
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = preferences.getInt("userId", -1);
        if (userId != -1) {
            String welcome="";
            Cursor c= db.rawQuery("SELECT * FROM Admin WHERE user_id = ?", new String[]{String.valueOf(userId)});
            if(c.moveToFirst()){
                welcome = c.getString(1);
            }
            txtGreeting.setText("Hello,\n"+welcome.toString());
        }
    }
}
