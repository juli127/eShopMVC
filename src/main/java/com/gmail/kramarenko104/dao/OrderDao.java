package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    Order createOrderForUser(long orderId, long userId, Map<Product, Integer> products);

    List<Order> getAllOrdersForUser(long userId);

    void deleteAllOrdersForUser(long userId);

    long getNewOrderId();
}