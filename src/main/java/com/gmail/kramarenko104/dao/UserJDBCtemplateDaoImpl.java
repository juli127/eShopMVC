package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableTransactionManagement
public class UserJDBCtemplateDaoImpl implements UserDao {

    private static final String SALT = "34Ru9k";
    private final static String TABLE_NAME = "users";
    private final static String CREATE_USER = "INSERT INTO " + TABLE_NAME + "(login, password, name, address, comment) VALUES(?,?,?,?,?)";
    private final static String GET_USERID_BY_LOGIN_PASS = "SELECT id FROM " + TABLE_NAME + " WHERE login = ?, password = ?";
    private final static String UPDATE_USER = "UPDATE " + TABLE_NAME + "SET password = ?, name = ?, address = ? comment = ? WHERE id = ?";
    private final static String GET_USERBY_ID ="SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    private final static String GET_ALL_USERS ="SELECT * FROM " + TABLE_NAME;
    private final static String GET_USER_BY_LOGIN = "SELECT * FROM " + TABLE_NAME + " WHERE login = ?";
    private final static String DELETE_USER_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
    //private final SessionFactory sessionFactory;
    DataSource dataSource;

    private Session session;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserJDBCtemplateDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
//        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int createUser(User user) {
        int newId = -1;
        // user was created: number of updates rows > 0
        if (jdbcTemplate.update(CREATE_USER, user.getLogin(), user.getPassword(), user.getName(), user.getAddress(), user.getComment()) > 0){
            newId = jdbcTemplate.queryForObject(GET_USERID_BY_LOGIN_PASS,
                    (rs, rowNum) -> rs.getInt("id"),
                    user.getLogin(), user.getPassword());
        }
        return newId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int updateUser(User user) {
        return jdbcTemplate.update(UPDATE_USER, user.getPassword(), user.getName(), user.getAddress(), user.getComment(), user.getId());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User getUser(int id) {
        return jdbcTemplate.queryForObject(GET_USERBY_ID, new UserMapper(), id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> getAllUsers() {
        return jdbcTemplate.query(GET_ALL_USERS, new UserMapper());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User getUserByLogin(String login) {
        return jdbcTemplate.queryForObject(GET_USER_BY_LOGIN, new UserMapper(), login);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int deleteUser(int id) {
        return jdbcTemplate.update(DELETE_USER_BY_ID, id);
    }

    public boolean sessionIsOpen() {
        boolean isOpen = false;
        try {
            isOpen =  dataSource.getConnection() != null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOpen;
    }

    public static String hashString(String hash) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(StandardCharsets.UTF_8.encode(hash + SALT));
        return String.format("%032x", new BigInteger(md5.digest()));
    }

    class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setAddress(rs.getString("address"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setComment(rs.getString("comment"));
            return user;
        }
    }
}
