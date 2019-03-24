package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;

import java.util.Map;

public interface OrderService {

    boolean deleteAllOrders(int userId);
    Order createOrder(int userId, Map<Product, Integer> products);
}