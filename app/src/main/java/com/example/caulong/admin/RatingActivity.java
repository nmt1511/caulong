package com.example.caulong.admin;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Feedback;
import com.example.caulong.menuleft.feedback_Adapter;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private feedback_Adapter adapter;
    private DataDatSan dataDatSan;
    private List<Feedback> feedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        recyclerView = findViewById(R.id.recyclerViewFeedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataDatSan = new DataDatSan(this);

        // Lấy danh sách Feedback từ cơ sở dữ liệu
        feedbackList = getFeedbackList();
        adapter = new feedback_Adapter(this, feedbackList);

        recyclerView.setAdapter(adapter);
    }

    private List<Feedback> getFeedbackList() {
        List<Feedback> feedbackList = new ArrayList<>();
        Cursor cursor = dataDatSan.getReadableDatabase().rawQuery(
                "SELECT feedback_id, star, feedback_text, feedback_date, customer_id " +
                        "FROM Feedback", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int feedbackId = cursor.getInt(0);
                int star = cursor.getInt(1);
                String feedbackText = cursor.getString(2);
                String feedbackDate = cursor.getString(3);
                int customerId = cursor.getInt(4);

                Feedback feedback = new Feedback(feedbackId, star, feedbackText, feedbackDate, customerId);
                feedbackList.add(feedback);
            }
            cursor.close();
        }
        return feedbackList;
    }
}
