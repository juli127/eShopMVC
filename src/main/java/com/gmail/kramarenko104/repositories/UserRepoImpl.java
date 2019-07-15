package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class UserRepoImpl extends BaseRepoImpl<User> implements UserRepo {

    private EntityManagerFactory emf;

    @Autowired
    public UserRepoImpl(EntityManagerFactory emf) {
        this.emf = emf;
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
        return getUserByLogin(user.getLogin());
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
