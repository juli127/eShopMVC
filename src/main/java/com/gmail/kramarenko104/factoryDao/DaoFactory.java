package com.gmail.kramarenko104.factoryDao;

import com.gmail.kramarenko104.dao.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoFactory {

    private UserDaoImpl userDao;
    private ProductDaoImpl productDao;
    private CartDaoImpl cartDao;
    private OrderDaoImpl orderDao;
    private SessionFactory sessionFactory;

    @Autowired
    public void DaoFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //public abstract void openConnection();

//    public static DaoFactory getSpecificDao() {
//        ResourceBundle config = ResourceBundle.getBundle("application");
//        DaoFactory daoFactory = null;
//
//        try {
//            daoFactory = (DaoFactory) Class.forName(config.getString("factoryClass")).newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return daoFactory;
//    }

//    @Autowired
//    public void setConnection(Connection connection) {
//        this.connection = connection;
//    }

    @Autowired
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setProductDao(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setCartDao(CartDaoImpl cartDao) {
        this.cartDao = cartDao;
    }

    @Autowired
    public void setOrderDao(OrderDaoImpl orderDao) {
        this.orderDao = orderDao;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

//    public Connection getConnection() {
//        return connection;
//    }

    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public UserDao getUserDao() {
        userDao = new UserDaoImpl(sessionFactory);
        return userDao;
    }

    public ProductDao getProductDao() {
        productDao = new ProductDaoImpl(sessionFactory);
        return productDao;
    }

    public CartDao getCartDao() {
        cartDao = new CartDaoImpl(sessionFactory);
        return cartDao;
    }

    public OrderDao getOrderDao() {
        orderDao = new OrderDaoImpl(sessionFactory);
        return orderDao;
    }

    public void deleteUserService(UserDao userDao) {
        if (userDao != null) {
            userDao = null;
        }
    }

    public void deleteProductService(ProductDao productDao) {
        if (productDao != null) {
            productDao = null;
        }
    }

    public void deleteCartService(CartDao cartDao) {
        if (cartDao != null) {
            cartDao = null;
        }
    }

    public void deleteOrderService(OrderDao orderService) {
        if (orderService != null) {
            orderService = null;
        }
    }

}
