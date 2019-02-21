package com.gmail.kramarenko104.controllers;

import java.sql.*;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
public class DBWorker {

    private Connection conn;
    private static Logger logger = Logger.getLogger(DBWorker.class);
    private StringBuilder log;

    public DBWorker() {
        logger.debug("DBWorker: --------------------------------------");
        log = new StringBuilder();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        log.append("<br>DBWorker: try to connect.....");
        try {
            ResourceBundle config = ResourceBundle.getBundle("config");
            //String connStr = getConnectionString();
            String connStr = new StringBuilder("jdbc:mysql://").append(config.getString("host"))
                    .append("/").append(config.getString("db")).append("?")
                    .append("user=").append(config.getString("usr")).append("&")
                    .append("password=").append(config.getString("psw")).toString();
            log.append("<br>DBWorker: try to get connection.....");
            conn = DriverManager.getConnection(connStr);
            log.append("<br>DBWorker: Connection obtained ");
        } catch (Exception ex) {
            log.append("<br>DBWorker: failed to get connection...");
            log.append("<br>DBWorker: SQLException: " + ex.getMessage());
        }
    }

    public String getLog() {
        return log.toString();
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.append("DBWorker: connection was closed");
    }


}
