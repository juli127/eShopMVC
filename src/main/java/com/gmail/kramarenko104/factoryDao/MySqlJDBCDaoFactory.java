package com.gmail.kramarenko104.factoryDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MySqlJDBCDaoFactory extends DaoFactory {

    private static Logger logger = LoggerFactory.getLogger(MySqlJDBCDaoFactory.class);
    private String connStr;

    public MySqlJDBCDaoFactory() {
        ResourceBundle config = null;
        try {
            config = ResourceBundle.getBundle("application");
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(config.getString("driverClassName")).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connStr = new StringBuilder().append(config.getString("url"))
                .append("?").append("user=").append(config.getString("user"))
                .append("&password=").append(config.getString("password")).toString();
    }

    //@Override
    public void openConnection() {
        try {
            Connection conn = DriverManager.getConnection(connStr);
           // super.setConnection(conn);
            logger.debug("Connection obtained");
        } catch (SQLException e) {
            logger.debug("Connection failed. SQLException: " + e.getMessage());
        }
    }

    //@Override
    public void close() {
        super.close();
        logger.debug("All resources closed");
    }
}
