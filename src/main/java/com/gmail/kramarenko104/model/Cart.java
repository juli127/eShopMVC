package com.gmail.kramarenko104.model;

import java.util.Map;

public class Cart {

    private int userId;

    private Map<Integer, Integer> products;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Cart: [");
        products.forEach((productId, quantity) -> result.append("(productId : " + productId + ", quantity : " + quantity + ")"));
        return result.append("]").toString();
    }
}
