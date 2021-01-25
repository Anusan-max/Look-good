package com.example.lookgood.repositories;

import com.example.lookgood.models.CartItem;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.List;

public class OrderRepo implements Serializable {

    private int orderId;
    private String customerId;
    private List<CartItem> orderItems;

    FirebaseAuth fAuth;

    public  OrderRepo() {
        fAuth = FirebaseAuth.getInstance();
        orderId = (int)(Math.random() * (20000000 - 100 + 1) + 100);
        customerId = fAuth.getUid();
    }


    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderId() {
        return "Or_" + orderId ;
    }

    public String getCustomerId() {
        return customerId;
    }
}
