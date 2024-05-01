package com.example.usersbackend.service;

import com.example.usersbackend.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LocalStorage {

    private long countId;

    private List<User> userStorage;

    @PostConstruct
    private void init(){
        userStorage = new ArrayList<>();
    }

    public List<User> getAllUsers(){
        return userStorage;
    }

    public User getById(long id){
        return userStorage.stream()
                .filter(user -> user.getId() == id)
                .findFirst().orElse(null);
    }

    public User create(User user){
        user.setId(++countId);
        userStorage.add(user);
        return user;
    }

    public void delete(long id){
        userStorage.removeIf(user -> user.getId() == id);
    }
}
