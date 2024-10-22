package com.example.caulong.entities;

import java.io.Serializable;

public class court implements Serializable {
    private String court_id;
    private String court_name;
    private String court_type;
    private double price;
    private String status;

    public court(String court_id, String court_name, String court_type, double price, String status) {
        this.court_id = court_id;
        this.court_name = court_name;
        this.court_type = court_type;
        this.price = price;
        this.status = status;
    }

    public String getCourt_id() {
        return court_id;
    }

    public void setCourt_id(String court_id) {
        this.court_id = court_id;
    }

    public String getCourt_name() {
        return court_name;
    }

    public void setCourt_name(String court_name) {
        this.court_name = court_name;
    }

    public String getCourt_type() {
        return court_type;
    }

    public void setCourt_type(String court_type) {
        this.court_type = court_type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
