package com.gmail.kramarenko104.factoryDao;

import com.gmail.kramarenko104.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Component
public abstract class DaoFactory {

    private UserDaoImpl userDao;
    private ProductDaoImpl productDao;
    private CartDaoImpl cartDao;
    private OrderDaoImpl orderDao;
	private Connection connection;

	public void DaoFactory(){}

    public abstract void openConnection();

	public static DaoFactory getSpecificDao(){
		ResourceBundle config = ResourceBundle.getBundle("application");
		DaoFactory daoFactory = null;

		try {
			daoFactory = (DaoFactory) Class.forName(config.getString("factoryClass")).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return daoFactory;
	}

    @Autowired
    public void setConnection(Connection connection){
        this.connection = connection;
    }

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

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection(){
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDao getUserDao() {
        userDao = new UserDaoImpl(connection);
        return userDao;
    }

    public ProductDao getProductDao() {
        productDao = new ProductDaoImpl(connection);
        return productDao;
    }

    public CartDao getCartDao() {
        cartDao = new CartDaoImpl(connection);
        return cartDao;
    }

    public OrderDao getOrderDao() {
        orderDao = new OrderDaoImpl(connection);
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
