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
        return userDao.createUser(user);
    }

    @Override
//    @Transactional
    public User getUser(long id){
        return userDao.getUser(id);
    }

    @Override
//    @Transactional
    public User getUserByLogin(String login){
        return userDao.getUserByLogin(login);
    }

    @Override
//    @Transactional
    public long updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
//    @Transactional
    public long deleteUser(long id){
        return userDao.deleteUser(id);
    }

    @Override
//    @Transactional
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    public Session openSession() {
        return userDao.getSessionFactory().openSession();
    }

    public void closeSession(){
        userDao.getSessionFactory().getCurrentSession().close();
    }
}
