package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.hibernate.EntityManagerFactoryUtil;
import com.gmail.kramarenko104.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.DynamicUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@Repository
@DynamicUpdate
//@EnableTransactionManagement
public class UserDaoImpl implements UserDao {

    // @Autowired
    private SessionFactory sessionFactory;
    private final static String GET_ALL_USERS = "from User u";
    private final static String GET_USER_BY_LOGIN = "from User u where u.login = :login";
    private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
//    private Session session;
    private EntityManagerFactory emf;

    public UserDaoImpl() {
//        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        emf = EntityManagerFactoryUtil.getEntityManagerFactory();
    }

//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory)  {
//        this.sessionFactory = sessionFactory;
////        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
//
//    }

    @Override
    public long createUser(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long userId = -1;
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
            userId = user.getUserId();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return userId;
    }

    @Override
    public long updateUser(User updUser) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        long userId = -1;
        try {
            userId = updUser.getUserId();
            tx.begin();
            User dbUser = em.find(User.class, userId);
            dbUser.setLogin(updUser.getLogin());
            dbUser.setPassword(updUser.getPassword());
            dbUser.setComment(updUser.getComment());
            dbUser.setAddress(updUser.getAddress());
            dbUser.setName(updUser.getName());
            tx.commit();
        } catch (Exception ex){
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return userId;
    }

    @Override
    public User getUser(long userId) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, userId);
        em.close();
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        List<User> usersList = em.createQuery(GET_ALL_USERS).getResultList();
        em.close();
        return usersList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserByLogin(String login) {
        EntityManager em = emf.createEntityManager();
        User user = (User) em.createQuery(GET_USER_BY_LOGIN)
                .setParameter("login", login)
                .getSingleResult();
        em.close();
        return user;
    }

    @Override
    public void deleteUser(long userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class, userId);
            if (user != null) {
                em.remove(user);
            }
            tx.commit();
        } catch (Exception ex){
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
