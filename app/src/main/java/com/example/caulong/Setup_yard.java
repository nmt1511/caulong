package com.example.caulong;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Setup_yard extends AppCompatActivity {
    private TextView tvSelectedDate;
    private Button btnXacNhan;
    private ListView listViewYard;
    private List<String> danhSachSan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setup_yard);

        // Initialize UI components
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        listViewYard = findViewById(R.id.listViewYards);
        ImageView baselineCalendar = findViewById(R.id.pickTime);

        // Create a list of dummy yards
        danhSachSan = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            danhSachSan.add("Sân " + i);
        }

        // Hide ListView initially
        listViewYard.setVisibility(View.GONE);

        // Set up date picker dialog on calendar icon click
        baselineCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Confirmation button event
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateYardList();
            }
        });

        // Handle ListView item clicks
        listViewYard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sanDuocChon = danhSachSan.get(position);
                Toast.makeText(Setup_yard.this, "Bạn đã chọn " + sanDuocChon, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Show the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Setup_yard.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Update TextView with the selected date
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        tvSelectedDate.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void updateYardList() {
        // Check if the date is selected
        String selectedDate = tvSelectedDate.getText().toString();
        if (selectedDate.equals("Select Date")) {
            Toast.makeText(this, "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show();
            return;
        }

        // Display the yard list
        listViewYard.setVisibility(View.VISIBLE);

        // Create an ArrayAdapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, danhSachSan);
        listViewYard.setAdapter(adapter);
    }
}
