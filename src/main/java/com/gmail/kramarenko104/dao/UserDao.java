package com.gmail.kramarenko104.dao;

import com.gmail.kramarenko104.model.User;

public interface UserDao extends Dao <User> {

    User getUserByLogin(String login);

}
