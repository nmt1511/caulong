package com.example.caulong.entities;

import java.io.Serializable;

public class Service implements Serializable {
    int service_id;
    String service_name;
    int quantity;
    int type_id;
    String type_name;
    double service_price;
    double total_price;

    public Service() {
    }

    public Service(int quantity, int service_id, String service_name, double service_price, int type_id) {
        super();
        this.quantity = quantity;
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_price = service_price;
        this.type_id = type_id;
    }

    public Service(int quantity, int service_id, String service_name, double service_price, int type_id, String type_name) {
        this.quantity = quantity;
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_price = service_price;
        this.type_id = type_id;
        this.type_name = type_name;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    // Phương thức sao chép
    public Service copy() {
        Service copy = new Service();
        copy.setService_id(this.service_id);
        copy.setService_name(this.service_name);
        copy.setService_price(this.service_price);
        copy.setQuantity(this.quantity);
        return copy;
    }

    public Service(int service_id, String service_name, double service_price) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_price = service_price;
    }
    public Service(String service_name, int quantity,double service_price, double total_price) {
        this.service_name = service_name;
        this.service_price = service_price;
        this.quantity = quantity;
        this.total_price = total_price;
    }
    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
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
        return "Service{" +
                "service_name='" + service_name + '\'' +
                ", service_price=" + service_price +
                '}';
    }
}
