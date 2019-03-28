package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.UserDaoImpl;
import com.gmail.kramarenko104.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoImpl userDao;

    @Override
    public int createUser(User user){
        return userDao.createUser(user);
    }

    @Override
    public User getUser(int id){
        return userDao.getUser(id);
    }

    @Override
    public User getUserByLogin(String login){
        return userDao.getUserByLogin(login);
    }

    @Override
    public int deleteUser(int id){
        return userDao.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    @Override
    public boolean sessionIsOpen(){
        return userDao.sessionIsOpen();
    }
}
