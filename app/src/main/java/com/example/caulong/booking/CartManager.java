package com.example.caulong.booking;

import com.example.caulong.entities.Service;

import java.util.ArrayList;

public class CartManager {
    private static CartManager instance;
    private ArrayList<Service> cartList;

    private CartManager() {
        cartList = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public ArrayList<Service> getCartList() {
        return cartList;
    }

    public void addService(Service service) {
        boolean found = false;
        for (Service cartService : cartList) {
            if (cartService.getService_id() == service.getService_id()) {
                cartService.setQuantity(cartService.getQuantity() + service.getQuantity());
                found = true;
                break;
            }
        }
        if (!found) {
            cartList.add(service.copy()); // Thêm bản sao của service vào cartList
        }
    }

    public void clearCart() {
        cartList.clear();
    }
}

