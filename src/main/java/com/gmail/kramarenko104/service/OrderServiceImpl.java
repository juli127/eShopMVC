package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.CartDaoImpl;
import com.gmail.kramarenko104.dao.OrderDaoImpl;
import com.gmail.kramarenko104.dao.UserDaoImpl;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDaoImpl orderDao;

    @Autowired
    UserDaoImpl userDao;

    @Autowired
    CartDaoImpl cartDao;

    private final static String PROCESSED_ORDER = "ordered";

    public void setOrderDao(OrderDaoImpl orderDao, UserDaoImpl userDao, CartDaoImpl cartDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.cartDao = cartDao;
    }

    public void deleteAllOrders(long userId){
        orderDao.deleteAllOrdersForUser(userId);
    }

    public Order createOrder(long userId, Map<Product, Integer> products){
        long newOrderNumber = orderDao.getNewOrderNumber();
        // createProduct the new order on the base of Cart
        Order newOrder = new Order();
        newOrder.setUser(userDao.get(userId));
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
        Order dbNewOrder = orderDao.createOrder(newOrder);

        return dbNewOrder;
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order getLastOrderByUserId(long userId){
        Order order = orderDao.getLastOrderByUserId(userId);
        if (order != null){
            order = recalculateOrder(order);
        }
        return order;
    }

    public boolean isDbConnected(){
        return orderDao.isDbConnected();
    }

    private Order recalculateOrder(Order order) {
        Map<Product, Integer> productsInOrder = order.getProducts();
        int itemsCount = 0;
        int totalSum = 0;
        if (productsInOrder.size() > 0) {
            int quantity = 0;
            for (Map.Entry<Product, Integer> entry : productsInOrder.entrySet()) {
                quantity = entry.getValue();
                itemsCount += quantity;
                totalSum += quantity * entry.getKey().getPrice();
            }
        }
        if (itemsCount > 0) {
            order.setItemsCount(itemsCount);
            order.setTotalSum(totalSum);
        }
        return order;
    }
}
