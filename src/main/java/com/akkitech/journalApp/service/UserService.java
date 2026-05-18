package com.akkitech.journalApp.service;

import com.akkitech.journalApp.entity.User;
import com.akkitech.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public String addUser(User user) {
        userRepo.save(user);
        return "User added";
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return userRepo.findById(id);
    }

    public User findUserByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }
    
    public String deleteUser(ObjectId id) {
        userRepo.deleteById(id);
        return "User deleted";
    }
}
