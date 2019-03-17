package com.gmail.kramarenko104.factoryDao;

import com.gmail.kramarenko104.dao.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class DaoFactory {

    private UserDaoMySqlImpl userDaoMySqlImpl;
    private ProductDaoMySqlImpl productDaoMySqlImpl;
    private CartDaoMySqlImpl cartDaoMySqlImpl;
    private OrderDaoMySqlImpl orderDaoMySqlImpl;
	private Connection conn;

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

    public void setConnection(Connection conn){
        this.conn = conn;
    }

    public void closeConnection(){
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDao getUserDao() {
        userDaoMySqlImpl = new UserDaoMySqlImpl(conn);
        return userDaoMySqlImpl;
    }

    public ProductDao getProductDao() {
        productDaoMySqlImpl = new ProductDaoMySqlImpl(conn);
        return productDaoMySqlImpl;
    }

    public CartDao getCartDao() {
        cartDaoMySqlImpl = new CartDaoMySqlImpl(conn);
        return cartDaoMySqlImpl;
    }

    public OrderDao getOrderDao() {
        orderDaoMySqlImpl = new OrderDaoMySqlImpl(conn);
        return orderDaoMySqlImpl;
    }

    public void deleteUserDao(UserDao userDao) {
        if (userDao != null) {
            userDao = null;
        }
    }

    public void deleteProductDao(ProductDao productDao) {
        if (productDao != null) {
            productDao = null;
        }
    }

    public void deleteCartDao(CartDao cartDao) {
        if (cartDao != null) {
            cartDao = null;
        }
    }

    public void deleteOrderDao(OrderDao orderDao) {
        if (orderDao != null) {
            orderDao = null;
        }
    }

}
