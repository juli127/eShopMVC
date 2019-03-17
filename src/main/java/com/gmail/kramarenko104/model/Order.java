package com.gmail.kramarenko104.model;

import java.util.HashMap;
import java.util.Map;

public class Order {

    private int orderNumber;
    private int userId;
    private int itemsCount;
    private int totalSum;
    private String status;
    private Map<Product, Integer> products;

    public Order() {
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }
    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", userId=" + userId +
                ", itemsCount=" + itemsCount +
                ", totalSum=" + totalSum +
                ", products=" + products +
                '}';
    }
}
