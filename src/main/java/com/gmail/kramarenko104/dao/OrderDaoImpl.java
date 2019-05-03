package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.factoryDao.HibernateSessionFactoryUtil;
import com.gmail.kramarenko104.model.Order;
import com.gmail.kramarenko104.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@Repository
@DynamicUpdate
public class OrderDaoImpl implements OrderDao {

    private final static String ENTITY_NAME = "Order";
    private final static String CREATE_ORDER = "insert into " + ENTITY_NAME + " (orderNumber, userId, productId, quantity, status) VALUES(?,?,?,?,?)";
    private final static String DELETE_ALL_ORDERS_BY_USERID = "delete from " + ENTITY_NAME + " o where o.userId = :userId";
    private final static String GET_ALL_ORDERS_BY_USERID = "select o from " + ENTITY_NAME + " o where o.userId = :userId";
    private final static String GET_LAST_ORDER_NUMBER = "select distinct max(o.orderNumber) as lastOrderNumber from " + ENTITY_NAME + " o";
    private final static String PROCESSED_ORDER = "ordered";
    private final static Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

//    @Autowired
    private SessionFactory sessionFactory;
    private Session session;


//    public OrderDaoImpl() {
//    }

    public OrderDaoImpl() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Override
    public Order createOrderForUser(long orderNumber, long userId, Map<Product, Integer> products) {
        // create the new order
        int totalSum = 0;
        int itemsCount = 0;
        session = sessionFactory.openSession();

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            itemsCount += entry.getValue();
            totalSum += entry.getValue() * entry.getKey().getPrice();

            Transaction tx = session.beginTransaction();
            session.createNativeQuery(CREATE_ORDER)
                    .setParameter("orderNumber", orderNumber)
                    .setParameter("userId", userId)
                    .setParameter("productId", entry.getKey().getProductId())
                    .setParameter("quantity", entry.getValue())
                    .setParameter("status", PROCESSED_ORDER)
                    .executeUpdate();
            tx.commit();
            session.close();
        }
        // and return this new order with calculated itemsCount and totalSum back to show on view
        Order newOrder = new Order();
        newOrder.setOrderNumber(orderNumber);
        newOrder.setStatus(PROCESSED_ORDER);
        newOrder.setUserId(userId);
        newOrder.setProducts(products);
        newOrder.setTotalSum(totalSum);
        newOrder.setItemsCount(itemsCount);
        logger.debug("OrderDAO.createOrderForUser:...new Order was created with orderNumber = " + orderNumber);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrdersForUser(long userId) {
        Session session = sessionFactory.openSession();
        List<Order> resultList =  session.createQuery(GET_ALL_ORDERS_BY_USERID)
                .setParameter("userId", userId)
                .getResultList();
        logger.debug("OrderDAO.getAllOrdersForUser: List of all orders is: " + resultList.toString());
        session.close();
        return resultList;
    }

    @Override
    public long getNewOrderId(){
        // generate order number (orderNumber is not auto-increment in 'orders' table)
        // because of one order can have many products ==> many rows can have the same orderNumber in 'orders' table
        int lastOrderNumber = 0;
        ResultSet rs = null;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        lastOrderNumber = (int) session.createQuery(GET_LAST_ORDER_NUMBER)
                .getSingleResult();
        tx.commit();
        session.close();
        return ++lastOrderNumber;
    }

    @Override
    public void deleteAllOrdersForUser(long userId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createQuery(DELETE_ALL_ORDERS_BY_USERID)
                .setParameter("userId", userId)
                .executeUpdate();
        tx.commit();
        session.close();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
