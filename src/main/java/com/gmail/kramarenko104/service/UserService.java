package com.gmail.kramarenko104.service;

import com.gmail.kramarenko104.model.User;
import java.util.List;

public interface UserService {

    long createUser(User user);

    User getUser(long id);

    User getUserByLogin(String login);

    long updateUser(User user);

    void deleteUser(long id);

    List<User> getAllUsers();

}
