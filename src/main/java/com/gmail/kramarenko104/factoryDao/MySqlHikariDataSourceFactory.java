package com.gmail.kramarenko104.factoryDao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.SQLException;

@Component("mySqlHikariDataSourceFactory")
public class MySqlHikariDataSourceFactory extends DaoFactory{
    private static Logger logger = Logger.getLogger(MySqlHikariDataSourceFactory.class);

    @Autowired
    private HikariConfig hikariConfig;
    @Autowired
    private HikariDataSource dataSource;

    public MySqlHikariDataSourceFactory(){}

    @Override
    public void openConnection() {
        try {
            logger.debug("MySqlHikariDataSourceFactory.openConnection:  dataSource: " + dataSource);
            logger.debug("MySqlHikariDataSourceFactory.openConnection:  hikariConfig: " + hikariConfig);
            Connection conn = dataSource.getConnection();
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
