package com.gmail.kramarenko104.models;

import java.util.*;

public class Cart {
    Map<Product, Integer> products;
    int size;

    public Cart() {
        size = 0;
        this.products = new HashMap<>();
    }

    public void addProduct(Product product){
        Set<Product> keyProducts = products.keySet();
        int qnt = 1;
        if (keyProducts.contains(product)){
            qnt = products.get(product) + 1;
        }
        products.put(product, qnt);
        size++;
    }

    public Map<Product, Integer> getProducts(){
        return products;
    }

    public int getSize(){
        return products.size();
    }
}
