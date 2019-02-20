package com.gmail.kramarenko104.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class DBWorker {

    private final static String GET_USER_BY_LOGIN_PASS = "SELECT * FROM users WHERE login = ? AND  password = ?";
    private final static String GET_ALL_USERS = "SELECT * FROM users;";
    private final static String GET_RECORD_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private final static String GET_USER_COUNT_BY_LOGIN = "SELECT COUNT(*) FROM users WHERE login = ?";
    private final static String INSERT_USER = "INSERT INTO users (login, password, name, age, gender, address, comment, agree) VALUES (?,?,?,?,?,?,?,?);";
    private final static String tableTitle =
            "<br><center><table border='1'><tr><td>login</td><td>name</td><td>age</td><td>gender</td><td>address</td><td>comment</td><td>agree</td></tr>";
    private Connection conn;
    private final String SALT = "34Ru9k";
    private static Logger logger = Logger.getLogger(DBWorker.class);

    public DBWorker() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("sout DBWorker: Driver loaded");
        logger.debug("logger DBWorker: try to connect.....");
        try {
            ResourceBundle config = ResourceBundle.getBundle("config");
            //String connStr = getConnectionString();
            String connStr = new StringBuilder("jdbc:mysql://").append(config.getString("host"))
                    .append("/").append(config.getString("db")).append("?")
                    .append("user=").append(config.getString("usr")).append("&")
                    .append("password=").append(config.getString("psw")).toString();
            conn = DriverManager.getConnection(connStr);
            logger.debug("DBWorker: Connection obtained ");
        } catch (Exception ex) {
            logger.debug("failed to get connection...");
            logger.debug("SQLException: " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        System.out.println("DBWorker: return conn: " + conn);
        return conn;
    }

    static String hashString(String hash) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(StandardCharsets.UTF_8.encode(hash));
        return String.format("%032x", new BigInteger(md5.digest()));
    }

    public String getAllUsers() {
        StringBuilder sb = new StringBuilder();
        sb.append(tableTitle);
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_USERS)) {
            while (rs.next()) {
                sb.append("<tr><td>" + rs.getString(2) +
                        "</td><td>" + rs.getString(4) +
                        "</td><td>" + rs.getString(5) +
                        "</td><td>" + rs.getString(6) +
                        "</td><td>" + rs.getString(7) +
                        "</td><td>" + rs.getString(8) +
                        "</td><td>" + rs.getString(9) +
                        "</td></tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sb.append("</table>");
        return sb.toString();
    }

    public int addUser(String login, String pass,
                       String name, String age, String gender,
                       String address, String comment, String agree) throws SQLException {
        int insertedCount = 0;
        conn.setAutoCommit(false);
        try (PreparedStatement statement = conn.prepareStatement(INSERT_USER)) {
            statement.setString(1, login);
            statement.setString(2, hashString(pass + SALT));
            statement.setString(3, name);
            statement.setInt(4, Integer.parseInt(age));
            statement.setString(5, gender);
            statement.setString(6, address);
            statement.setString(7, comment);
            statement.setString(8, agree);
            insertedCount = statement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertedCount;
    }

    public boolean userExists(String login) {
        boolean present = false;
        ResultSet rs = null;
        try (PreparedStatement statement = conn.prepareStatement(GET_USER_COUNT_BY_LOGIN)) {
            statement.setString(1, login);
            rs = statement.executeQuery();
            while (rs.next()) {
                present = (Integer.parseInt(rs.getString(1)) > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }
        // logger.debug("DBWorker: userExists ? " + present);
        return present;
    }

    public boolean passIsCorrect(String login, String pass) {
        boolean correct = false;
        ResultSet rs = null;
        try (PreparedStatement statement = conn.prepareStatement(GET_RECORD_BY_LOGIN)) {
            statement.setString(1, login);
            rs = statement.executeQuery();
            while (rs.next()) {
                correct = (rs.getString(3).equals(hashString(pass + SALT)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }
        return correct;
    }

    public String getName(String login, String pass) {
        String name = "";
        ResultSet rs = null;
        try (PreparedStatement statement = conn.prepareStatement(GET_USER_BY_LOGIN_PASS)) {
            statement.setString(1, login);
            statement.setString(2, hashString(pass + SALT));
            rs = statement.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }
        return name;
    }

    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // logger.debug("DBWorker: connection was closed");
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }
}
