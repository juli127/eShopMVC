package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMySqlImpl implements UserDao {

    private final static String CREATE_USER = "INSERT INTO users(login, password, name, address, comment) VALUES(?,?,?,?,?);";
    private final static String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?);";
    private final static String GET_USER_BY_LOGIN_PASS = "SELECT * FROM users WHERE login = ? AND  password = ?";
    private final static String GET_ALL_USERS = "SELECT * FROM users;";
    private final static String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private final static String SALT = "34Ru9k";
    private Connection conn;
    private static Logger logger = Logger.getLogger(UserDaoMySqlImpl.class);

    public UserDaoMySqlImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean createUser(User user) {
        try (PreparedStatement pst = conn.prepareStatement(CREATE_USER)) {
            conn.setAutoCommit(false);
            pst.setString(1, user.getLogin());
            pst.setString(2, hashString(user.getPassword()));
            pst.setString(3, user.getName());
            pst.setString(4, user.getAddress());
            pst.setString(5, user.getComment());
            pst.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUser(int id) {
        User user = new User();
        try (PreparedStatement pst = conn.prepareStatement(GET_USER_BY_ID)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            fillUser(rs, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_USERS)) {
            while (rs.next()) {
                User user = new User();
                fillUser(rs, user);
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    @Override
    public User editUser(int id, User user) {
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = new User();
        ResultSet rs = null;
        boolean exist = false;
        logger.debug(">>>UserDao.userExists: check user with login = " + login);
        try (PreparedStatement statement = conn.prepareStatement(GET_USER_BY_LOGIN)) {
            statement.setString(1, login);
            rs = statement.executeQuery();
            fillUser(rs, user);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
        }
        logger.debug(">>>UserDao.userExists: user is correct = " + exist);
        return user;
    }

    private void fillUser(ResultSet rs, User user) throws SQLException {
        while (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setAddress(rs.getString("address"));
            user.setComment(rs.getString("comment"));
            logger.debug(">>>UserDao.userExists: GOT user with this login from DB = " + user);
        }
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }

    public static String hashString(String hash) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(StandardCharsets.UTF_8.encode(hash  + SALT));
        return String.format("%032x", new BigInteger(md5.digest()));
    }

}
