package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final static String CREATE_ORDER = "INSERT INTO orders (orderNumber, userId, productId, quantity, status) VALUES(?,?,?,?,?);";
    private final static String DELETE_ALL_ORDERS_BY_USERID = "DELETE FROM orders WHERE userId = ?;";
    private final static String GET_LAST_ORDER_NUMBER = "SELECT DISTINCT max(orderNumber) as lastOrderNumber FROM orders;";
    private final static String PROCESSED_ORDER = "ordered";
    private static Logger logger = Logger.getLogger(OrderDaoImpl.class);
    private final SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order createOrder(int orderNumber, int userId, Map<Product, Integer> products) {
        // create the new order
        int totalSum = 0;
        int itemsCount = 0;
        session = sessionFactory.openSession();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            itemsCount += entry.getValue();
            totalSum += entry.getValue() * entry.getKey().getPrice();
            try (PreparedStatement pst = conn.prepareStatement(CREATE_ORDER)) {
                session.beginTransaction();
                pst.setInt(1, orderNumber);
                pst.setInt(2, userId);
                pst.setInt(3, entry.getKey().getId()); // productId
                pst.setInt(4, entry.getValue()); // quantity
                pst.setString(5, PROCESSED_ORDER);
                pst.executeUpdate();
                session.getTransaction().commit();
            } catch (SQLException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
        }
        // and return this new order with calculated itemsCount and totalSum back to show on view
        Order newOrder = new Order();
        newOrder.setOrderNumber(orderNumber);
        newOrder.setStatus(PROCESSED_ORDER);
        newOrder.setUserId(userId);
        newOrder.setProducts(products);
        newOrder.setTotalSum(totalSum);
        newOrder.setItemsCount(itemsCount);
        logger.debug("OrderDAO.createOrder:...new Order was created with orderNumber = " + orderNumber);
        session.close();
        return newOrder;
    }

    @Override
    public int getNewOrderId(){
        // generate order number (orderNumber is not auto-increment in 'orders' table)
        // because of one order can have many products ==> many rows can have the same orderNumber in 'orders' table
        int lastOrderNumber = 0;
        ResultSet rs = null;
        session = sessionFactory.openSession();
        try (Statement pst = conn.createStatement()) {
            rs = pst.executeQuery(GET_LAST_ORDER_NUMBER);
            while (rs.next()) {
                lastOrderNumber = rs.getInt("lastOrderNumber");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.close();
        return ++lastOrderNumber;
    }

    @Override
    public boolean deleteAllOrders(int userId) {
        session = sessionFactory.openSession();
        try (PreparedStatement pst = conn.prepareStatement(DELETE_ALL_ORDERS_BY_USERID)) {
            pst.setInt(1, userId);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.close();
        return false;
    }
}
