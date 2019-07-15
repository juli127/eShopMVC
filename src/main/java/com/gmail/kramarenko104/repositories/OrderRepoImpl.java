package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class OrderRepoImpl extends BaseRepoImpl<Order> implements OrderRepo {

    private final static Logger logger = LoggerFactory.getLogger(OrderRepoImpl.class);
    private EntityManagerFactory emf;

    @Autowired
    public OrderRepoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Order createOrder(Order order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Order newOrder = null;
        try {
            tx.begin();
            order.setUser(em.merge(order.getUser()));
            em.persist(order);
            tx.commit();
            long orderId = order.getOrderId();
            newOrder = em.find(Order.class, orderId);

        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        logger.debug("[eshop] OrderDAO.createOrderForUser:...new Order was created: " + newOrder);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrdersByUserId(long userId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Order> query = em.createNamedQuery("GET_ALL_ORDERS_BY_USERID", Order.class).setParameter("userId", userId);
        List<Order> resultList = query.getResultList();
        logger.debug("[eshop] OrderDAO.getAllOrdersForUser: List of all orders is: " + resultList.toString());
        em.close();
        return resultList;
    }

    @Override
    public Order getLastOrderByUserId(long userId) {
        EntityManager em = emf.createEntityManager();
        Order order = null;
        logger.debug("[eshop] OrderDAO.getLastOrderByUserId: get order for userId: " + userId);
        try {
            TypedQuery<Order> query = em.createNamedQuery("GET_LAST_ORDER_BY_USERID", Order.class).setParameter("userId", userId);
            order = query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
        }
        logger.debug("[eshop] OrderDAO.getLastOrderByUserId: the last orders is: " + order);
        em.close();
        return order;
    }

    public long getNewOrderNumber() {
        // generate order number (orderNumber is not auto-increment in 'orders' table)
        // because of one order can have many products ==> many rows can have the same orderNumber in 'orders' table
        EntityManager em = emf.createEntityManager();
        Object result = em.createNamedQuery("GET_LAST_ORDER_NUMBER").getSingleResult();
        long lastOrderNumber = (result == null ? 0 : (long) result);
        em.close();
        return ++lastOrderNumber;
    }

    @Override
    public void deleteAllOrdersForUser(long userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            TypedQuery<Order> query = em.createNamedQuery("GET_ALL_ORDERS_BY_USERID", Order.class)
                    .setParameter("userId", userId);
            List<Order> ordersToRemove = query.getResultList();
            for (Order order : ordersToRemove) {
                em.remove(order);
            }
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
