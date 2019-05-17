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
import javax.persistence.TypedQuery;

@Repository
@DynamicUpdate
//@EnableTransactionManagement
public class UserDaoImpl extends BaseDao<User> implements UserDao {

    // @Autowired
    private SessionFactory sessionFactory;
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
    @SuppressWarnings("unchecked")
    public User getUserByLogin(String login) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createNamedQuery("GET_USER_BY_LOGIN", User.class).setParameter("login", login);
        User user = query.getSingleResult();
        em.close();
        return user;
    }

}
