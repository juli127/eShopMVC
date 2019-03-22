package com.gmail.kramarenko104.factoryDao;

import com.gmail.kramarenko104.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class DaoFactory {

    @Autowired
    private UserDaoMySqlImpl userDaoMySqlImpl;
    @Autowired
    private ProductDaoMySqlImpl productDaoMySqlImpl;
    @Autowired
    private CartDaoMySqlImpl cartDaoMySqlImpl;
    @Autowired
    private OrderDaoMySqlImpl orderDaoMySqlImpl;

	private Connection connection;

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

    public void setConnection(Connection connection){
        this.connection = connection;
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
        userDaoMySqlImpl = new UserDaoMySqlImpl(connection);
        return userDaoMySqlImpl;
    }

    public ProductDao getProductDao() {
        productDaoMySqlImpl = new ProductDaoMySqlImpl(connection);
        return productDaoMySqlImpl;
    }

    public CartDao getCartDao() {
        cartDaoMySqlImpl = new CartDaoMySqlImpl(connection);
        return cartDaoMySqlImpl;
    }

    public OrderDao getOrderDao() {
        orderDaoMySqlImpl = new OrderDaoMySqlImpl(connection);
        return orderDaoMySqlImpl;
    }


    public UserDaoMySqlImpl getUserDaoMySqlImpl() {
        return userDaoMySqlImpl;
    }

    public void setUserDaoMySqlImpl(UserDaoMySqlImpl userDaoMySqlImpl) {
        this.userDaoMySqlImpl = userDaoMySqlImpl;
    }

    public ProductDaoMySqlImpl getProductDaoMySqlImpl() {
        return productDaoMySqlImpl;
    }

    public void setProductDaoMySqlImpl(ProductDaoMySqlImpl productDaoMySqlImpl) {
        this.productDaoMySqlImpl = productDaoMySqlImpl;
    }

    public CartDaoMySqlImpl getCartDaoMySqlImpl() {
        return cartDaoMySqlImpl;
    }

    public void setCartDaoMySqlImpl(CartDaoMySqlImpl cartDaoMySqlImpl) {
        this.cartDaoMySqlImpl = cartDaoMySqlImpl;
    }

    public OrderDaoMySqlImpl getOrderDaoMySqlImpl() {
        return orderDaoMySqlImpl;
    }

    public void setOrderDaoMySqlImpl(OrderDaoMySqlImpl orderDaoMySqlImpl) {
        this.orderDaoMySqlImpl = orderDaoMySqlImpl;
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
