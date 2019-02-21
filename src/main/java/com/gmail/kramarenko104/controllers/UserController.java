package com.gmail.kramarenko104.controllers;

import com.gmail.kramarenko104.models.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    private final static String GET_USER_BY_LOGIN_PASS = "SELECT * FROM users WHERE login = ? AND  password = ?";
    private final static String GET_ALL_USERS = "SELECT * FROM users;";
    private final static String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private final static String GET_USER_COUNT_BY_LOGIN = "SELECT COUNT(*) FROM users WHERE login = ?";
    private final static String INSERT_USER = "INSERT INTO users (login, password, name, address, comment) VALUES (?,?,?,?,?);";
    private final String SALT = "34Ru9k";
    private Connection conn;
    private StringBuilder log;

    public UserController(Connection conn) {
        this.conn = conn;
        log = new StringBuilder();
    }

    public int addUser(String login, String pass,
                       String name, String address, String comment) throws SQLException {
        int insertedCount = 0;
        conn.setAutoCommit(false);
        try (PreparedStatement statement = conn.prepareStatement(INSERT_USER)) {
            statement.setString(1, login);
            statement.setString(2, hashString(pass + SALT));
            statement.setString(3, name);
            statement.setString(4, address);
            statement.setString(5, comment);
            insertedCount = statement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertedCount;
    }

    public String getLog() {
        return log.toString();
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


    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_USERS)) {
            while (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setComment(rs.getString("comment"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
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
        log.append("<br>UserController: userExists ? " + present);
        return present;
    }

    public boolean passIsCorrect(String login, String pass) {
        boolean correct = false;
        ResultSet rs = null;
        try (PreparedStatement statement = conn.prepareStatement(GET_USER_BY_LOGIN)) {
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
        log.append("<br>UserController: passIsCorrect ? " + correct);
        return correct;
    }

    public User getUser(String login){
        ResultSet rs = null;
        User user = null;
        try (PreparedStatement statement = conn.prepareStatement(GET_USER_BY_LOGIN)) {
            statement.setString(1, login);
            rs = statement.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setComment(rs.getString("comment"));
                log.append("<br>UserController: getUser: " + ((user == null)?"":user.toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }
        return user;
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

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }

}
