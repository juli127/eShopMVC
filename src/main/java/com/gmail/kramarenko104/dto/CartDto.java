package com.gmail.kramarenko104.dto;

import com.gmail.kramarenko104.model.Product;
import java.util.HashMap;
import java.util.Map;

public class CartDto {

    private long userId;
    private int itemsCount;
    private int totalSum;
    private Map<Product, Integer> products;

    public CartDto(long userId) {
        this.userId = userId;
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
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
