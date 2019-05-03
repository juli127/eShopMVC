package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.OrderDaoImpl;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDaoImpl orderDao;

    public void deleteAllOrders(long userId){
        orderDao.deleteAllOrdersForUser(userId);
    }

    public Order createOrder(long userId, Map<Product, Integer> products){
        long newOrderNumber = orderDao.getNewOrderId();
        return orderDao.createOrderForUser(newOrderNumber, userId, products);
    }

    public Session openSession() {
        return orderDao.getSessionFactory().openSession();
    }

    public void closeSession(){
        orderDao.getSessionFactory().getCurrentSession().close();
    }
}
