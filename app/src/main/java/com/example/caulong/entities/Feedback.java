package com.example.caulong.entities;

public class Feedback {
    private int feedbackId;
    private int star;
    private String feedbackText;
    private String feedbackDate;
    private int customerId;

    public Feedback(int feedbackId, int star, String feedbackText, String feedbackDate, int customerId) {
        this.feedbackId = feedbackId;
        this.star = star;
        this.feedbackText = feedbackText;
        this.feedbackDate = feedbackDate;
        this.customerId = customerId;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
