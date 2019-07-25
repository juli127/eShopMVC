package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class UserRepoImpl extends BaseRepoImpl<User> implements UserRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    public User createUser(User user) {
        em.persist(user);
        return getUserByLogin(user.getLogin());
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        try {
            TypedQuery<User> query = em.createNamedQuery("GET_USER_BY_LOGIN", User.class)
                    .setParameter("login", login);
            user = query.getSingleResult();
        } catch (NoResultException nre) {
        }
        return user;
    }
}
