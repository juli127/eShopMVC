package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;

import java.util.List;

public interface UserDao {

    long createUser(User user);

    User getUser(long userId);

    User getUserByLogin(String login);

    long updateUser(User user);

    List<User> getAllUsers();

    void deleteUser(long userId);

}
