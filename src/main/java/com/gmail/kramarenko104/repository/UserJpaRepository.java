package com.gmail.kramarenko104.repository;

import com.gmail.kramarenko104.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserJpaRepository extends JpaRepository<User,Integer> {

    int createUser(User user);

    User getUser(int id);

    User getUserByLogin(String login);

    int updateUser(User user);

    int deleteUser(int id);

    List<User> getAllUsers();
}
