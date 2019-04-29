package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
@EnableTransactionManagement
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;
    private final static String ENTITY_NAME = "User";
    private final static String SALT = "34Ru9k";
    private final static String GET_ALL_USERS = "select u from " + ENTITY_NAME + " u";
    private final static String GET_USER_BY_LOGIN = "select u from " + ENTITY_NAME + " u where u.login = :login";
    private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private Session session;

    public UserDaoImpl()  {
    }

//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory)  {
//        this.sessionFactory = sessionFactory;
////        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
//
//    }

    @Transactional
    @Override
    public int createUser(User user) {
        session = sessionFactory.openSession();
        Serializable id = session.save(ENTITY_NAME, user);
        session.flush();
        session.close();
        return (int) id;
    }

    @Transactional
    @Override
    public int updateUser(User user) {
        session = sessionFactory.openSession();
        session.update(ENTITY_NAME, user);
        session.flush();
        int identifier = (int) session.getIdentifier(user);
        session.close();
        return identifier;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User getUser(int id) {
        session = sessionFactory.openSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        session = sessionFactory.openSession();
        List<User> usersList = session.createQuery(GET_ALL_USERS).list();
        session.close();
        return usersList;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    @SuppressWarnings("unchecked")
    public User getUserByLogin(String login) {
        session = sessionFactory.openSession();
        User user = (User) session.createQuery(GET_USER_BY_LOGIN).getResultList().get(0);
        session.close();
        return user;
    }

    @Transactional
    @Override
    public int deleteUser(int id) {
        session = sessionFactory.openSession();
        int identifier = -1;
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(ENTITY_NAME, user);
            session.flush();
            identifier = (int) session.getIdentifier(user);
        }
        session.close();
        return identifier;
    }

    @Override
    public boolean sessionIsOpen() {
        return sessionFactory.isOpen();
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
}
