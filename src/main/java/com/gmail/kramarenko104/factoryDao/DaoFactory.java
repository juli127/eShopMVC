package com.gmail.kramarenko104.factoryDao;

import com.gmail.kramarenko104.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Component("daoFactory")
public abstract class DaoFactory {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
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

    public void setConnection(Connection connection){
        this.connection = connection;
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

    public UserService getUserService() {
        userService = new UserServiceImpl(connection);
        return userService;
    }

    public ProductService getProductService() {
        productService = new ProductServiceImpl(connection);
        return productService;
    }

    public CartService getCartService() {
        cartService = new CartServiceImpl(connection);
        return cartService;
    }

    public OrderService getOrderService() {
        orderService = new OrderServiceImpl(connection);
        return orderService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void setProductService(ProductServiceImpl productService) {
        this.productService = productService;
    }

    public void setCartService(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    public void deleteUserService(UserService userService) {
        if (userService != null) {
            userService = null;
        }
    }

    public void deleteProductService(ProductService productService) {
        if (productService != null) {
            productService = null;
        }
    }

    public void deleteCartService(CartService cartService) {
        if (cartService != null) {
            cartService = null;
        }
    }

    public void deleteOrderService(OrderService orderService) {
        if (orderService != null) {
            orderService = null;
        }
    }

}
