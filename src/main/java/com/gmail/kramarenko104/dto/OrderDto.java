package com.gmail.kramarenko104.dto;

import com.gmail.kramarenko104.model.Product;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OrderDto {

    private long userId;

    private long orderNumber;

    private int itemsCount;

    private int totalSum;

    private Map<Product, Integer> products;

    public OrderDto(long userId) {
        this.userId = userId;
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
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
                ", products=[" + Arrays.asList(products) + "]}";
    }


}
