package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class UserRepoImpl extends BaseRepoImpl<User> implements UserRepo {

    private EntityManagerFactory emf;

    @Autowired
    public UserRepoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = Exception.class)
    public User createUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.persist(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return getUserByLogin(user.getLogin());
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        EntityManager em = emf.createEntityManager();
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
