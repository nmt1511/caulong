package com.example.caulong.entities;

import java.io.Serializable;

public class Customer implements Serializable {
    private String customer_id;
    private String customer_name;
    private String gender;
    private String phone_number;
    private String email;
    private long user_id;
    private String avatar;

    public Customer(String customer_id, String customer_name, String gender, String phone_number, String email, long user_id, String avatar) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.email = email;
        this.gender = gender;
        this.phone_number = phone_number;
        this.user_id = user_id;
        this.avatar = avatar;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_name='" + customer_name + '\'' +
                '}';
    }
}
