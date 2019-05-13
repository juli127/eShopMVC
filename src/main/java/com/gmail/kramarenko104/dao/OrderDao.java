package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Order;

import java.util.List;

public interface OrderDao {

    Order createOrder(Order order);

    List<Order> getAllOrdersForUser(long userId);

    void deleteAllOrdersForUser(long userId);

    long getNewOrderNumber();
}