package com.example.usersbackend;

import com.example.usersbackend.model.User;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class TestDataService {

    public List<User> getTestUserList(){
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("user1@mail.com");
        user1.setName("User1");
        user1.setLastName("Lastuser1");
        user1.setBirthday(LocalDate.parse("2005-01-01"));
        user1.setAddress("Kyiv");
        user1.setPhoneNumber("+380973332211");

        User user2 = new User();
        user2.setId(2);
        user2.setEmail("user2@mail.com");
        user2.setName("User2");
        user2.setLastName("Lastuser2");
        user2.setBirthday(LocalDate.parse("2003-01-01"));
        user2.setAddress("Lviv");
        user2.setPhoneNumber("+380501112233");

        User user3 = new User();
        user3.setId(3);
        user3.setEmail("user3@mail.com");
        user3.setName("User3");
        user3.setLastName("Lastuser3");
        user3.setBirthday(LocalDate.parse("2001-01-01"));
        user3.setAddress("Dnipro");
        user3.setPhoneNumber("+380952223311");
        return List.of(user1, user2, user3);
    }

    public User getTestUser() {
        User user = new User();
        user.setId(1);
        user.setEmail("user1@mail.com");
        user.setName("User1");
        user.setLastName("Lastuser1");
        user.setBirthday(LocalDate.parse("2005-01-01"));
        user.setAddress("Kyiv");
        user.setPhoneNumber("+380973332211");
        return user;
    }

}
