package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.dao.UserDaoImpl;
import com.gmail.kramarenko104.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    // validation of User object MUST be done here, NOT in UserDao.java !!!!
    @Autowired
    private UserDaoImpl userDao;

    public boolean createUser(User user){
        return userDao.createUser(user);
    }

    public User getUser(int id){
        return userDao.getUser(id);
    }

    public User getUserByLogin(String login){
        return userDao.getUserByLogin(login);
    }

    public boolean deleteUser(int id){
        return userDao.deleteUser(id);
    }

    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }
}
