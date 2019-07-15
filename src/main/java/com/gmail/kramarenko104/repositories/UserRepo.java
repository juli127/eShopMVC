package com.gmail.kramarenko104.repositories;

import com.gmail.kramarenko104.model.User;

public interface UserRepo {

    User createUser(User user);

    User getUserByLogin(String login);

}
