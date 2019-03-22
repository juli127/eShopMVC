package com.gmail.kramarenko104.factoryDao;

import com.zaxxer.hikari.HikariConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlHikariDataSourceFactory extends DaoFactory {
    private static Logger logger = Logger.getLogger(MySqlHikariDataSourceFactory.class);

    @Autowired
    private HikariConfig hikariConfig;
    @Autowired
    private DataSource hikariDataSource;

    public MySqlHikariDataSourceFactory(){}

    @Override
    public void openConnection() {
        try {
            logger.debug("dataSource: " + hikariDataSource);
            logger.debug("hikariConfig: " + hikariConfig);
            Connection conn = hikariDataSource.getConnection();
            super.setConnection(conn);
            logger.debug("Connection obtained");
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
