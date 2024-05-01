package com.example.usersbackend.service.impl;

import com.example.usersbackend.api.exception.badrequest.BadRequestException;
import com.example.usersbackend.api.exception.notfound.NotFoundException;
import com.example.usersbackend.model.User;
import com.example.usersbackend.service.LocalStorage;
import com.example.usersbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${limitAge}")
    private int limitAge;

    @Autowired
    private LocalStorage localStorage;

    @Override
    public List<User> findUsersByDateRange(LocalDate from, LocalDate to) {
        return localStorage.getAllUsers().stream()
                .filter(user -> user.getBirthday().isAfter(from) && user.getBirthday().isBefore(to))
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) {
        validateUser(user);
        return localStorage.create(user);
    }

    @Override
    public void updateUser(User updatedUser) {
        User user = localStorage.getById(updatedUser.getId());
        validateFound(user);
        if (updatedUser.getBirthday() != null){
            validateAge(updatedUser.getBirthday());
            user.setBirthday(updatedUser.getBirthday());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()){
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getName() != null && !updatedUser.getName().isBlank()){
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getLastName() != null && !updatedUser.getLastName().isBlank()){
            user.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getAddress() != null){
            user.setAddress(updatedUser.getAddress());
        }
        if (updatedUser.getPhoneNumber() != null){
            user.setPhoneNumber(updatedUser.getPhoneNumber());
        }

    }

    @Override
    public void deleteUser(long userId) {
        User user = localStorage.getById(userId);
        validateFound(user);
        localStorage.delete(userId);
    }

    private void validateFound(User user) {
        if (user == null) {
            throw new NotFoundException("User not found.");
        }
    }

    private void validateUser(User user){

        if (user.getEmail() == null || user.getEmail().isBlank()){
            throw new BadRequestException("Email is required.");
        }
        if (user.getName() == null || user.getName().isBlank()){
            throw new BadRequestException("Name is required.");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()){
            throw new BadRequestException("Lastname is required.");
        }
        if (user.getBirthday() == null){
            throw new BadRequestException("Birthday is required.");
        }

        validateAge(user.getBirthday());
    }

    private void validateAge(LocalDate birthday){
        if (ChronoUnit.YEARS.between(birthday, LocalDate.now()) < limitAge){
            throw new BadRequestException("The age should be more than 18 years old.");
        }
    }

}
