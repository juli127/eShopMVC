package com.gmail.kramarenko104.factoryDao;

import com.gmail.kramarenko104.dao.*;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MySqlDaoFactory extends DaoFactory {

    private static Logger logger = Logger.getLogger(MySqlDaoFactory.class);
    Connection conn;
    UserDaoMySqlImpl userDaoMySqlImpl;
    ProductDaoMySqlImpl productDaoMySqlImpl;
    CartDaoMySqlImpl cartDaoMySqlImpl;

    @Override
    public UserDao getUserDao() {
        userDaoMySqlImpl = new UserDaoMySqlImpl(conn);
        return userDaoMySqlImpl;
    }

    @Override
    public ProductDao getProductDao() {
        productDaoMySqlImpl = new ProductDaoMySqlImpl(conn);
        return productDaoMySqlImpl;
    }

    @Override
    public CartDao getCartDao() {
        cartDaoMySqlImpl = new CartDaoMySqlImpl(conn);
        return cartDaoMySqlImpl;
    }

    public MySqlDaoFactory() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
        logger.debug("Connection to DB .....");
        try {
            ResourceBundle config = ResourceBundle.getBundle("config");
            logger.debug("Connection string:" + "jdbc:mysql://" + config.getString("host") + "/" + config.getString("db")
                    + "?" + "user=" + config.getString("usr") + "&password=" + config.getString("psw"));
            conn = DriverManager.getConnection("jdbc:mysql://" + config.getString("host") + "/" + config.getString("db")
                    + "?" + "user=" + config.getString("usr") + "&password=" + config.getString("psw"));

            logger.debug("Connection obtained");
        } catch (SQLException ex) {
            logger.debug("Connection to DB failed...");
            logger.debug("SQLException: " + ex.getMessage());
            logger.debug("SQLState: " + ex.getSQLState());
            logger.debug("VendorError: " + ex.getErrorCode());
        }
    }

    @Override
    public void deleteUserDao(UserDao userDao) {
        if(userDao != null){
            userDao = null;
        }
    }

    @Override
    public void deleteProductDao(ProductDao productDao) {
        if(productDao != null){
            productDao = null;
        }
    }

    @Override
    public void deleteCartDao(CartDao cartDao) {
        if(cartDao != null){
            cartDao = null;
        }
    }


    @Override
    public void closeConnection() {
        try {
            if (conn != null)
                conn.close();
            logger.debug("Connection to DB was closed");
            Thread.dumpStack();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
