package com.gmail.kramarenko104.services;

import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;
import com.gmail.kramarenko104.repositories.OrderRepoImpl;
import com.gmail.kramarenko104.repositories.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final static String PROCESSED_ORDER = "ordered";
    private OrderRepoImpl orderRepo;
    private UserRepoImpl userRepo;

    @Autowired
    public OrderServiceImpl(UserRepoImpl userRepo,
                            OrderRepoImpl orderRepo) {
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
    }

    @Override
    public void deleteAllOrders(long userId) {
        orderRepo.deleteAllOrdersForUser(userId);
    }

    @Override
    public Order createOrder(long userId, Map<Product, Integer> products) {
        long newOrderNumber = orderRepo.getNewOrderNumber();
        // createProduct the new order on the base of Cart
        Order newOrder = new Order();
        newOrder.setUser(userRepo.get(userId));
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
        return orderRepo.createOrder(newOrder);
    }

    @Override
    public List<Order> getAll() {
        return orderRepo.getAll();
    }

    @Override
    public Order getLastOrderByUserId(long userId) {
        Order order = orderRepo.getLastOrderByUserId(userId);
        if (order != null) {
            order = recalculateOrder(order);
        }
        return order;
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
