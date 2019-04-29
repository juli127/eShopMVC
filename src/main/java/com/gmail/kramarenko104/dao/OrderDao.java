package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    Order createOrderForUser(int orderId, int userId, Map<Product, Integer> products);

    List<Order> getAllOrdersForUser(int userId);

    void deleteAllOrdersForUser(int userId);

    int getNewOrderId();
}