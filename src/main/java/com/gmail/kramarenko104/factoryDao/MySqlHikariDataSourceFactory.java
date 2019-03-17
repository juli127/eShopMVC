package com.gmail.kramarenko104.factoryDao;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;

public class MySqlHikariDataSourceFactory extends DaoFactory {
    private static Logger logger = Logger.getLogger(MySqlHikariDataSourceFactory.class);
    private HikariDataSource hikariDataSource;

    public MySqlHikariDataSourceFactory(){
        ResourceBundle rb = ResourceBundle.getBundle("hikari");
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(rb.getString("datasource.driverClassName"));
        config.setJdbcUrl(rb.getString("datasource.jdbcUrl"));
        config.setUsername(rb.getString("datasource.user"));
        config.setPassword(rb.getString("datasource.password"));
        config.setMinimumIdle(Integer.valueOf(rb.getString("datasource.minimumIdle")));
        config.setMaximumPoolSize(Integer.valueOf(rb.getString("datasource.maximumPoolSize")));
        hikariDataSource = new HikariDataSource(config);
    }

    @Override
    public void openConnection() {
        try {
            Connection conn = hikariDataSource.getConnection();
            super.setConnection(conn);
            logger.debug("Connection obtained");
            super.setConnection(conn);
        } catch (SQLException e) {
            logger.debug("Connection failed. SQLException: " + e.getMessage());
        }
    }

    @Override
    public void closeConnection() {
        super.closeConnection();
        logger.debug("Connection closed");
    }
}
