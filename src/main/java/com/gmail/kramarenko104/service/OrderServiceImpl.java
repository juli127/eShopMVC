package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.OrderDaoImpl;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDaoImpl orderDao;

    public boolean deleteAllOrders(int userId){
        return orderDao.deleteAllOrders(userId);
    }

    public Order createOrder(int userId, Map<Product, Integer> products){
        int newOrderNumber = orderDao.getNewOrderId();
        return orderDao.createOrder(newOrderNumber, userId, products);
    }
}
