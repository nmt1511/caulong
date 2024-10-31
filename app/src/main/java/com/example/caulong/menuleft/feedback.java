package com.example.caulong.menuleft;


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

import java.util.ArrayList;

public class feedback extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText feedbackText;
    private Button submitFeedbackButton;
    private RecyclerView feedbackRecyclerView;
    private FeedbackAdapter feedbackAdapter;
    private ArrayList<FeedbackItem> feedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBar = findViewById(R.id.ratingBar);
        feedbackText = findViewById(R.id.feedbackText);
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton);
        feedbackRecyclerView = findViewById(R.id.feedbackRecyclerView);

        feedbackList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(feedbackList);
        feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedbackRecyclerView.setAdapter(feedbackAdapter);

        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        String comment = feedbackText.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (comment.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập góp ý của bạn", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thêm phản hồi mới vào danh sách
        FeedbackItem newFeedback = new FeedbackItem("Đánh giá", rating, comment);
        feedbackList.add(newFeedback);
        feedbackAdapter.notifyItemInserted(feedbackList.size() - 1);

        // Xóa nội dung đã nhập
        feedbackText.setText("");
        ratingBar.setRating(0);
        Toast.makeText(this, "Cảm ơn bạn đã gửi góp ý!", Toast.LENGTH_SHORT).show();
    }

    // Lớp FeedbackItem được gộp vào đây
    private static class FeedbackItem {
        private String category; // Danh mục phản hồi
        private float rating;    // Điểm đánh giá
        private String comment;  // Nội dung góp ý

        // Constructor
        public FeedbackItem(String category, float rating, String comment) {
            this.category = category;
            this.rating = rating;
            this.comment = comment;
        }

        // Getter cho danh mục
        public String getCategory() {
            return category;
        }

        // Getter cho đánh giá
        public float getRating() {
            return rating;
        }

        // Getter cho nội dung góp ý
        public String getComment() {
            return comment;
        }
    }
    // Lớp FeedbackAdapter được gộp vào đây
    private class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
        private ArrayList<FeedbackItem> feedbackList;

        public FeedbackAdapter(ArrayList<FeedbackItem> feedbackList) {
            this.feedbackList = feedbackList;
        }

        @NonNull
        @Override
        public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
            return new FeedbackViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
            FeedbackItem feedbackItem = feedbackList.get(position);
            holder.ratingTextView.setText("Chất lượng dịch vụ: " + feedbackItem.getRating());
            holder.commentTextView.setText("Góp ý: "+feedbackItem.getComment());
        }

        @Override
        public int getItemCount() {
            return feedbackList.size();
        }

        class FeedbackViewHolder extends RecyclerView.ViewHolder {
            TextView categoryTextView;
            TextView ratingTextView;
            TextView commentTextView;

            public FeedbackViewHolder(@NonNull View itemView) {
                super(itemView);
                ratingTextView = itemView.findViewById(R.id.tv_rating);
                commentTextView = itemView.findViewById(R.id.tv_comment);
            }
        }
    }
}


