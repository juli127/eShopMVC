package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.hibernate.HibernateSessionFactoryUtil;
import com.gmail.kramarenko104.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
@DynamicUpdate
//@EnableTransactionManagement
public class UserDaoImpl implements UserDao {

    //    @Autowired
    private SessionFactory sessionFactory;
    private final static String ENTITY_NAME = "User";
    private final static String SALT = "34Ru9k";
    private final static String GET_ALL_USERS = "select u from " + ENTITY_NAME + " u";
    private final static String GET_USER_BY_LOGIN = "select u from " + ENTITY_NAME + " u where u.login = :login";
    private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private Session session;

    public UserDaoImpl() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory)  {
//        this.sessionFactory = sessionFactory;
////        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
//
//    }

    //    @Transactional
    @Override
    public long save(User user) {
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User criptUser = user;
        criptUser.setPassword(hashString(user.getPassword()));
        try {
            Serializable id = session.save(criptUser);
            return (long) id;
        } finally {
            tx.commit();
            session.close();
        }
    }

    //    @Transactional
    @Override
    public long update(User user) {
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(user);
            long identifier = (long) session.getIdentifier(user);
            return identifier;
        } finally {
            tx.commit();
            session.close();
        }
    }

    //    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User get(long id) {
        session = sessionFactory.openSession();
        User user = (User) session.get(User.class, id);
        session.close();
        return user;
    }

    //    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        session = sessionFactory.openSession();
        List<User> usersList = session.createQuery(GET_ALL_USERS).list();
        session.close();
        return usersList;
    }

    //    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    @SuppressWarnings("unchecked")
    public User getUserByLogin(String login) {
        session = sessionFactory.openSession();
        User user = (User) session.createQuery(GET_USER_BY_LOGIN)
                .setParameter("login", login)
                .getSingleResult();
        session.close();
        return user;
    }

    @Transactional
    @Override
    public long delete(long id) {
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        long identifier = -1;
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
            session.flush();
            identifier = (long) session.getIdentifier(user);
        }
        tx.commit();
        session.close();
        return identifier;
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

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
