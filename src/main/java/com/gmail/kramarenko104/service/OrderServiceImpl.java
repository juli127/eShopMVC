package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.OrderDaoImpl;
import com.gmail.kramarenko104.dao.UserDaoImpl;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDaoImpl orderDao;

    @Autowired
    UserDaoImpl userDao;

    private final static String PROCESSED_ORDER = "ordered";

    public void deleteAllOrders(long userId){
        orderDao.deleteAllOrdersForUser(userId);
    }

    public Order createOrder(long userId, Map<Product, Integer> products){
        long newOrderNumber = orderDao.getNewOrderNumber();
        // createProduct the new order on the base of Cart
        Order newOrder = new Order();
        newOrder.setUser(userDao.getUser(userId));
        newOrder.setOrderNumber(newOrderNumber);
        newOrder.setProducts(products);
        newOrder.setStatus(PROCESSED_ORDER);
        int totalSum = 0;
        int itemsCount = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            itemsCount += entry.getValue();
            totalSum += entry.getValue() * entry.getKey().getPrice();
        }
        newOrder.setTotalSum(totalSum);
        newOrder.setItemsCount(itemsCount);
        return orderDao.createOrder(newOrder);
    }
}
