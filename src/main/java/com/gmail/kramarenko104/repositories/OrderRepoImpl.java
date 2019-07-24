package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = Exception.class)
    public Order createOrder(Order order) {
        Order newOrder = null;
        EntityManager em = emf.createEntityManager();
        try {
            order.setUser(em.merge(order.getUser()));
            em.persist(order);
            long orderId = order.getOrderId();
            newOrder = em.find(Order.class, orderId);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        logger.debug("[eshop] OrderRepoImpl.createOrderForUser:...new Order was created: " + newOrder);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrdersByUserId(long userId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Order> query = em.createNamedQuery("GET_ALL_ORDERS_BY_USERID", Order.class).setParameter("userId", userId);
        List<Order> resultList = query.getResultList();
        logger.debug("[eshop] OrderRepoImpl.getAllOrdersForUser: List of all orders is: " + resultList.toString());
        em.close();
        return resultList;
    }

    @Override
    public Order getLastOrderByUserId(long userId) {
        Order order = null;
        EntityManager em = emf.createEntityManager();
        logger.debug("[eshop] OrderRepoImpl.getLastOrderByUserId: get order for userId: " + userId);
        try {
            TypedQuery<Order> query = em.createNamedQuery("GET_LAST_ORDER_BY_USERID", Order.class).setParameter("userId", userId);
            order = query.setMaxResults(1).getSingleResult();
        } catch (NoResultException ex) {
        }
        logger.debug("[eshop] OrderRepoImpl.getLastOrderByUserId: the last orders is: " + order);
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
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = Exception.class)
    public void deleteAllOrdersForUser(long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Order> query = em.createNamedQuery("GET_ALL_ORDERS_BY_USERID", Order.class)
                    .setParameter("userId", userId);
            List<Order> ordersToRemove = query.getResultList();
            for (Order order : ordersToRemove) {
                em.remove(order);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
