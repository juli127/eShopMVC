package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Order;
import java.util.List;

public interface OrderRepo {

    Order createOrder(Order order);

    List<Order> getAllOrdersByUserId(long userId);

    void deleteAllOrdersForUser(long userId);

    Order getLastOrderByUserId(long userId);

}