package com.gmail.kramarenko104.services;

import com.gmail.kramarenko104.model.User;
import com.gmail.kramarenko104.repositories.UserRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final static String SALT = "34Ru9k";
    private UserRepoImpl userRepo;

    @Autowired
    public UserServiceImpl(UserRepoImpl userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user){
        User criptUser = user;
        criptUser.setPassword(hashString(user.getPassword()));
        return userRepo.createUser(criptUser);
    }

    @Override
    public User getUser(long id){
        return userRepo.get(id);
    }

    @Override
    public User getUserByLogin(String login){
        return userRepo.getUserByLogin(login);
    }

    @Override
    public User update(User user) {
        return userRepo.update(user);
    }

    @Override
    public void deleteUser(long id){
        userRepo.delete(id);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepo.getAll();
    }

    public static String hashString(String hash) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(StandardCharsets.UTF_8.encode(hash + SALT));
        return String.format("%032x", new BigInteger(md5.digest()));
    }
}
