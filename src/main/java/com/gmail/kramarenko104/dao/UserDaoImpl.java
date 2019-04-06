package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
@EnableTransactionManagement
public class UserDaoImpl implements UserDao {

    private static final String SALT = "34Ru9k";
    private final SessionFactory sessionFactory;
    private final String ENTITY_NAME = "User";
    private Session session;
    private TransactionTemplate transactionTemplate;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory)  {
        this.sessionFactory = sessionFactory;
    }

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
        int identifier = (int) session.getIdentifier(session);
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
    public List<User> getAllUsers() {
        session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<User> usersList = session.createQuery("from " + ENTITY_NAME).list();
        session.close();
        return usersList;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User getUserByLogin(String login) {
        session = sessionFactory.openSession();
        String sqlStr = "select u from " + ENTITY_NAME+ " u where u.login = :login";
        @SuppressWarnings("unchecked")
        User user = (User) session.createQuery(sqlStr).getResultList().get(0);
        session.close();
        return user;
    }

    @Transactional
    @Override
    public int deleteUser(int id) {
        session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.delete(ENTITY_NAME, user);
        session.flush();
        int identifier = (int) session.getIdentifier(user);
        session.close();
        return identifier;
    }

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
