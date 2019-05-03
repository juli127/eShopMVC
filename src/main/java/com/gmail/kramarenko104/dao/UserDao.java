package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;
import java.util.List;

public interface UserDao {

    long createUser(User user);

    User getUser(long id);

    User getUserByLogin(String login);

    long updateUser(User user);

    long deleteUser(long id);

    List<User> getAllUsers();

}
