package com.example.caulong.entities;

import java.io.Serializable;

public class times implements Serializable {
    private String time_id;
    private String time_name;
    private double value;

    public times(String time_id, String time_name, double value) {
        this.time_id = time_id;
        this.time_name = time_name;
        this.value = value;
    }

    public String getTime_id() {
        return time_id;
    }

    public void setTime_id(String time_id) {
        this.time_id = time_id;
    }

    public String getTime_name() {
        return time_name;
    }

    public void setTime_name(String time_name) {
        this.time_name = time_name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return time_name; // Hiển thị tên sân trong Spinner
    }
}
