package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.models.User;

import java.util.List;

public interface UserDao {

    // CRUD functionality
    boolean createUser(User user);
    User getUser(int id);
    User editUser(int id, User user);
    boolean deleteUser(int id);

    List<User> getAllUsers();
    boolean userExists(String login);
    User getUserByLoginPass(String login, String pass);

}
