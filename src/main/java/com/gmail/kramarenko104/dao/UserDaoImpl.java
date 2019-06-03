package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class UserDaoImpl extends BaseDao<User> implements UserDao {

    @Autowired
    private EntityManagerFactory emf;

    public UserDaoImpl() {
    }

    @Override
    public User createUser(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        EntityManager em = emf.createEntityManager();
        User user = null;
        try {
            TypedQuery<User> query = em.createNamedQuery("GET_USER_BY_LOGIN", User.class)
                    .setParameter("login", login);
            user = query.getSingleResult();
        }catch (NoResultException nre){
        }
        em.close();
        return user;
    }
}
