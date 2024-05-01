package com.example.usersbackend.service;

import com.example.usersbackend.model.User;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> findUsersByDateRange(LocalDate from, LocalDate to);

    User createUser(User user);

    void updateUser(User user);

    void deleteUser(long userId);
}
