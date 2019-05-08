package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.UserDaoImpl;
import com.gmail.kramarenko104.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private UserDaoImpl userDao;

    @Override
//    @Transactional
    public long createUser(User user){
        return userDao.save(user);
    }

    @Override
//    @Transactional
    public User getUser(long id){
        return userDao.get(id);
    }

    @Override
//    @Transactional
    public User getUserByLogin(String login){
        return userDao.getUserByLogin(login);
    }

    @Override
//    @Transactional
    public long updateUser(User user) {
        return userDao.update(user);
    }

    @Override
//    @Transactional
    public long deleteUser(long id){
        return userDao.delete(id);
    }

    @Override
//    @Transactional
    public List<User> getAllUsers(){
        return userDao.getAll();
    }

    public Session openSession() {
        return userDao.getSessionFactory().openSession();
    }

    public void closeSession(){
        userDao.getSessionFactory().getCurrentSession().close();
    }
}
