package com.example.caulong.entities;

import java.io.Serializable;

public class Service implements Serializable {
    int service_id;
    String service_name;
    int quantity;
    int type_id;
    double service_price;

    public Service(int quantity, int service_id, String service_name, double service_price, int type_id) {
        super();
        this.quantity = quantity;
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_price = service_price;
        this.type_id = type_id;
    }

    public Service(int service_id, String service_name, double service_price) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_price = service_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public double getService_price() {
        return service_price;
    }

    public void setService_price(double service_price) {
        this.service_price = service_price;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return service_name;
    }
}
