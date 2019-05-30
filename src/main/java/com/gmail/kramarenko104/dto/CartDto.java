package com.gmail.kramarenko104.dto;

import com.gmail.kramarenko104.model.Product;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Setter
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

    @Override
    public String toString() {
        return "Cart{" +
                "userId:" + userId +
                ", itemsCount:" + itemsCount +
                ", totalSum:" + totalSum +
                '}';
    }
}
