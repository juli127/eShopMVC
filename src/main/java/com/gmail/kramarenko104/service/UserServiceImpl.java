package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.UserDao;
import com.gmail.kramarenko104.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public int createUser(User user){
        return userDao.createUser(user);
    }

    @Override
    @Transactional
    public User getUser(int id){
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public User getUserByLogin(String login){
        return userDao.getUserByLogin(login);
    }

    @Override
    @Transactional
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    @Transactional
    public int deleteUser(int id){
        return userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    @Override
    public boolean sessionIsOpen(){
        return userDao.sessionIsOpen();
    }
}
