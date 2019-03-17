package com.gmail.kramarenko104.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private int userId;
    private int itemsCount;
    private int totalSum;
    private Map<Product, Integer> products;

    public Cart(int userId) {
        this.userId = userId;
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "userId:" + userId +
                ", itemsCount:" + itemsCount +
                ", totalSum:" + totalSum +
                '}';
    }
}
