package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;

import java.util.Map;

public interface OrderService {

    void deleteAllOrders(long userId);

    Order createOrder(long userId, Map<Product, Integer> products);
}
