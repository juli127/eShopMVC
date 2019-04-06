package com.gmail.kramarenko104.repository;

import com.gmail.kramarenko104.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Repository
@EnableTransactionManagement
public class UserCrudRepositoryImpl implements UserCrudRepository {

    private static final String SALT = "34Ru9k";
    private static final String GET_USER_BY_LOGIN = "select u from User u where u.login = :login";
    private static final String GET_ALL_USERS = "select u from User u ";
    private TransactionTemplate transactionTemplate;

    @PersistenceContext
    EntityManager entityManager;

//    @Autowired
//    public UserJpaRepository(SessionFactory sessionFactory)  {
//        this.sessionFactory = sessionFactory;
//    }
//    public UserDaoImpl(TransactionTemplate transactionTemplate)  {
//        this.transactionTemplate = transactionTemplate;
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int createUser(User user) {
        entityManager.persist(user);
        return getUserByLogin(user.getLogin()).getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int updateUser(User user) {
        entityManager.merge(user);
        return 1;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> getAllUsers() {
        return entityManager.createQuery(GET_ALL_USERS, User.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User getUserByLogin(String login) {
//        SqlParameterSource namedParameters = new MapSqlParameterSource("login", login);
        return (User) entityManager.createQuery(GET_USER_BY_LOGIN).getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int deleteUser(int id) {
        entityManager.remove(getUser(id));
        return 1;
    }

    public boolean sessionIsOpen() {
        return entityManager.isOpen();
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
