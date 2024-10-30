package com.example.caulong.entities;


import android.content.ContentValues;

public class User {
    private int customerId;
    private String customerName;
    private boolean gender;
    private String phoneNumber;
    private String email;
    private int userId;

    public User(int customerId, String customerName, boolean gender, String phoneNumber, String email, int userId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userId = userId;
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ContentValues getContentValuesForInsert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("customer_name", customerName);
        contentValues.put("gender", gender ? 1 : 0);
        contentValues.put("phone_number", phoneNumber);
        contentValues.put("email", email);
        contentValues.put("user_id", userId);
        return contentValues;
    }

    @Override
    public String toString() {
        return "User{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", userId=" + userId +
                '}';
    }
}
