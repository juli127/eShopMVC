package com.gmail.kramarenko104.factoryDao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

//import javax.annotation.PostConstruct;

//@Component("mySqlHikariDataSourceFactory")
public class MySqlHikariDataSourceFactory extends DaoFactory{
    private static Logger logger = Logger.getLogger(MySqlHikariDataSourceFactory.class);

//    @Autowired
    private HikariConfig hikariConfig;
//    @Autowired
    private HikariDataSource dataSource;


    Connection conn;

        //    public MySqlHikariDataSourceFactory()
//        @PostConstruct
        public void init() {
//        ResourceBundle rb = ResourceBundle.getBundle("hikari");
//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName(rb.getString("datasource.driverClassName"));
//        config.setJdbcUrl(rb.getString("datasource.jdbcUrl"));
//        config.setUsername(rb.getString("datasource.user"));
//        config.setPassword(rb.getString("datasource.password"));
//        config.setMinimumIdle(Integer.valueOf(rb.getString("datasource.minimumIdle")));
//        config.setMaximumPoolSize(Integer.valueOf(rb.getString("datasource.maximumPoolSize")));
//        dataSource = new HikariDataSource(hikariConfig);
            try {
                conn = dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    @Override
    public void openConnection() {
        try {
            logger.debug("MySqlHikariDataSourceFactory.openConnection:  dataSource: " + dataSource);
            logger.debug("MySqlHikariDataSourceFactory.openConnection:  hikariConfig: " + hikariConfig);
            conn = dataSource.getConnection();
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
