package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String SALT = "34Ru9k";
    private final SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int createUser(User user) {
        session = sessionFactory.openSession();
        Serializable id = session.save("User", user);
        session.close();
        return (int) id;
    }

    @Override
    public int updateUser(User user) {
        session = sessionFactory.openSession();
        session.update("User", user);
        int sessionId = (int) session.getIdentifier(session);
        session.close();
        return sessionId;
    }

    @Override
    public User getUser(int id) {
        session = sessionFactory.openSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<User> usersList = session.createQuery("from User").list();
        session.close();
        return usersList;
    }

    @Override
    public User getUserByLogin(String login) {
        session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        User user = (User) session.createQuery("select u from User u where u.login = :login").getResultList().get(0);
        session.delete("User", user);
        session.close();
        return user;
    }

    @Override
    public int deleteUser(int id) {
        session = sessionFactory.openSession();
        User user = session.load(User.class, id);
        session.delete("User", user);
        int sessionId = (int) session.getIdentifier(user);
        session.close();
        return sessionId;
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
