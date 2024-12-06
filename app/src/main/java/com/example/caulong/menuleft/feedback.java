package com.example.caulong.menuleft;


import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Feedback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class feedback extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText feedbackText;
    private Button submitFeedbackButton;
    private RecyclerView feedbackRecyclerView;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar = findViewById(R.id.ratingBar);
        feedbackText = findViewById(R.id.feedbackText);
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton);
        feedbackRecyclerView = findViewById(R.id.feedbackRecyclerView);
        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Feedback> feedbackList = getAllFeedbacks();
        feedback_Adapter feedbackAdapter = new feedback_Adapter(this,feedbackList);
        feedbackRecyclerView.setAdapter(feedbackAdapter);

        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stars = (int) ratingBar.getRating();
                String text = feedbackText.getText().toString();

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String currentDate = day + "/" + month + "/" + year;

                SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                int customerId = preferences.getInt("customerId", -1);
                if (!text.isEmpty()) {
                    Feedback feedback = new Feedback(0, stars, text, currentDate,customerId);
                    addFeedback(feedback);
                    feedbackList.add(feedback);
                    feedbackAdapter.notifyDataSetChanged();
                    feedbackText.setText("");
                    ratingBar.setRating(0);
                }
            }
        });

    }

    public void addFeedback(Feedback feedback) {
        DataDatSan helper = new DataDatSan(this);
        db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("star", feedback.getStar());
        values.put("feedback_text", feedback.getFeedbackText());
        values.put("feedback_date", feedback.getFeedbackDate());
        values.put("customer_id", feedback.getCustomerId());
        db.insert("Feedback", null, values);
        db.close();
    }

    public ArrayList<Feedback> getAllFeedbacks() {
        ArrayList<Feedback> feedbackList = new ArrayList<>();
        String selectQuery = "SELECT * FROM Feedback ORDER BY feedback_id DESC";
        DataDatSan helper = new DataDatSan(this);
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Feedback feedback = new Feedback(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                );
                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return feedbackList;
    }
}


