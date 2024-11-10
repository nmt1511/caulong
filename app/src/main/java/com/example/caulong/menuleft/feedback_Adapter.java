package com.example.caulong.menuleft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caulong.R;
import com.example.caulong.data.DataDatSan;
import com.example.caulong.entities.Feedback;

import java.util.List;

public class feedback_Adapter extends RecyclerView.Adapter<feedback_Adapter.FeedbackViewHolder> {
    private List<Feedback> feedbackList;
    private DataDatSan db;

    public feedback_Adapter(Context context, List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
        this.db = new DataDatSan(context);
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        String customer_name = db.getCustomerName(feedback.getCustomerId());
        holder.customerName.setText(customer_name);
        holder.feedbackText.setText(feedback.getFeedbackText());
        holder.feedbackDate.setText(feedback.getFeedbackDate());
        holder.ratingBar.setRating(feedback.getStar());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, feedbackText, feedbackDate;
        RatingBar ratingBar;

        FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            feedbackText = itemView.findViewById(R.id.feedbackText);
            feedbackDate = itemView.findViewById(R.id.feedbackDate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
