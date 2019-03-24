package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final static String CREATE_ORDER = "INSERT INTO orders (orderNumber, userId, productId, quantity, status) VALUES(?,?,?,?,?);";
    private final static String DELETE_ALL_ORDERS_BY_USERID = "DELETE FROM orders WHERE userId = ?;";
    private final static String GET_LAST_ORDER_NUMBER = "SELECT DISTINCT max(orderNumber) as lastOrderNumber FROM orders;";
    private final static String PROCESSED_ORDER = "ordered";
    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
    private Connection conn;

    public OrderServiceImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Order createOrder(int userId, Map<Product, Integer> products) {

        // generate order number (orderNumber) is not auto-increment in 'orders' table
        // (one order can have many products ==> many rows can have the same order number)
        int lastOrderNumber = 0;
        ResultSet rs = null;
        try (Statement pst = conn.createStatement()) {
            rs = pst.executeQuery(GET_LAST_ORDER_NUMBER);
            while (rs.next()) {
                lastOrderNumber = rs.getInt("lastOrderNumber");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ++lastOrderNumber;

        // create new order
        int totalSum = 0;
        int itemsCount = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            itemsCount += entry.getValue();
            totalSum += entry.getValue() * entry.getKey().getPrice();
            try (PreparedStatement pst = conn.prepareStatement(CREATE_ORDER)) {
                conn.setAutoCommit(false);
                pst.setInt(1, lastOrderNumber);
                pst.setInt(2, userId);
                pst.setInt(3, entry.getKey().getId()); // productId
                pst.setInt(4, entry.getValue()); // quantity
                pst.setString(5, PROCESSED_ORDER);
                pst.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // prepare created order to return
        Order newOrder = new Order();
        newOrder.setOrderNumber(lastOrderNumber);
        newOrder.setStatus(PROCESSED_ORDER);
        newOrder.setUserId(userId);
        newOrder.setProducts(products);
        newOrder.setTotalSum(totalSum);
        newOrder.setItemsCount(itemsCount);
        logger.debug("OrderDAO.createOrder:...new Order was created with orderNumber = " + lastOrderNumber);
        return newOrder;
    }

    @Override
    public boolean deleteAllOrders(int userId) {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_ALL_ORDERS_BY_USERID)) {
            pst.setInt(1, userId);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
