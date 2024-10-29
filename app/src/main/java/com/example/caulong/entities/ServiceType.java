package com.example.caulong.entities;

import java.io.Serializable;

public class ServiceType implements Serializable {
    int type_id;
    String type_name;

    public ServiceType(int type_id, String type_name) {
        super();
        this.type_id = type_id;
        this.type_name = type_name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public String toString() {
        return type_name;
    }
}
