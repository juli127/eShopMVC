package com.gmail.kramarenko104.services;

import com.gmail.kramarenko104.model.User;
import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUser(long id);

    User getUserByLogin(String login);

    User update(User user);

    void deleteUser(long id);

    List<User> getAllUsers();

}
