package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.hibernate.EntityManagerFactoryUtil;
import com.gmail.kramarenko104.model.Order;
import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@Repository
@DynamicUpdate
public class OrderDaoImpl implements OrderDao {

    private final static String DELETE_ALL_ORDERS_BY_USERID = "delete from Order o where o.user = :user";
    private final static String GET_ALL_ORDERS_BY_USERID = "select o from Order o where o.user = :user";
    private final static String GET_LAST_ORDER_NUMBER = "select distinct max(o.orderNumber) as lastOrderNumber from Order o";
    private final static Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    //    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    UserDaoImpl userDao;

//    public OrderDaoImpl() {
//    }

    public OrderDaoImpl() {
//        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        emf = EntityManagerFactoryUtil.getEntityManagerFactory();
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
        logger.debug("OrderDAO.createOrderForUser:...new Order was created: " + newOrder);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrdersForUser(long userId) {
        EntityManager em = emf.createEntityManager();
        List<Order> resultList = em.createQuery(GET_ALL_ORDERS_BY_USERID)
                .setParameter("user", userDao.getUser(userId))
                .getResultList();
        logger.debug("OrderDAO.getAllOrdersForUser: List of all orders is: " + resultList.toString());
        em.close();
        return resultList;
    }

    @Override
    public long getNewOrderNumber() {
        // generate order number (orderNumber is not auto-increment in 'orders' table)
        // because of one order can have many products ==> many rows can have the same orderNumber in 'orders' table
        EntityManager em = emf.createEntityManager();
        int lastOrderNumber = (int) em.createQuery(GET_LAST_ORDER_NUMBER).getSingleResult();
        em.close();
        return ++lastOrderNumber;
    }

    @Override
    public void deleteAllOrdersForUser(long userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            em.createQuery(DELETE_ALL_ORDERS_BY_USERID)
                    .setParameter("user", userDao.getUser(userId))
                    .executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
