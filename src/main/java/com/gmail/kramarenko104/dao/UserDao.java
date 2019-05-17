package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;

import java.util.List;

public interface UserDao {

    long createUser(User user);

    User get(long userId);

    User getUserByLogin(String login);

    User update(User user);

    List<User> getAll();

    void delete(long userId);

}
