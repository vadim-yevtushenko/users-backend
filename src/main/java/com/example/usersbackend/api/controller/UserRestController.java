package com.example.usersbackend.api.controller;

import com.example.usersbackend.api.exception.badrequest.BadRequestException;
import com.example.usersbackend.model.User;
import com.example.usersbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers(@RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                               @RequestParam(value = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to){
        if (from.isAfter(to)){
            throw new BadRequestException("Date from should be less than to.");
        }
        return userService.findUsersByDateRange(from, to);
    }

    @PostMapping
    public User create(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping
    public void update(@RequestBody User updatedUser){
        userService.updateUser(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        userService.deleteUser(id);
    }

}
