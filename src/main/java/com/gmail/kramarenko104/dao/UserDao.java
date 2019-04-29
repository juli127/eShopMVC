package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;
import java.util.List;

public interface UserDao {

    int createUser(User user);

    User getUser(int id);

    User getUserByLogin(String login);

    int updateUser(User user);

    int deleteUser(int id);

    List<User> getAllUsers();

    boolean sessionIsOpen();
}
