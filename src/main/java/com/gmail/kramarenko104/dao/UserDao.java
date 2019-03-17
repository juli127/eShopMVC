package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;

import java.util.List;

public interface UserDao {

    // CRUD functionality
    boolean createUser(User user);
    User getUser(int id);
    User getUserByLogin(String login);
    boolean deleteUser(int id);

    List<User> getAllUsers();

}
