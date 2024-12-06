package com.example.caulong.admin;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.caulong.HomeFragment;
import com.example.caulong.R;
import com.example.caulong.menubottom.AccountFragment;
import com.example.caulong.menubottom.HistoryFragment;
import com.example.caulong.menubottom.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BookingYard_Management extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            replaceFragment(new Booked_fragment());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.Booked)
            {
                replaceFragment(new Booked_fragment());
            } else if (item.getItemId() == R.id.Cancel) {
                replaceFragment(new Cancel_fragment());
            }else if (item.getItemId() == R.id.Canceled) {
                replaceFragment(new Canceled_fragment());
            }else if (item.getItemId() == R.id.complete) {
                replaceFragment(new Complete_fragment());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }
}
