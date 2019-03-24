package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.User;

import java.util.List;

public interface UserService {

    // CRUD functionality
    boolean createUser(User user);
    User getUser(int id);
    User getUserByLogin(String login);
    boolean deleteUser(int id);

    List<User> getAllUsers();

}
