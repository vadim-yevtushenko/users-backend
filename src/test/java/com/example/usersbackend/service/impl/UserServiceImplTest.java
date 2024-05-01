package com.example.usersbackend.service.impl;

import com.example.usersbackend.BaseTest;
import com.example.usersbackend.TestDataService;
import com.example.usersbackend.api.exception.badrequest.BadRequestException;
import com.example.usersbackend.api.exception.notfound.NotFoundException;
import com.example.usersbackend.model.User;
import com.example.usersbackend.service.LocalStorage;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

public class UserServiceImplTest extends BaseTest {

    @Autowired
    private TestDataService testDataService;

    @Mock
    private LocalStorage localStorage;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void findUsersByDateRange() {
        LocalDate from = LocalDate.parse("2002-01-01");
        LocalDate to = LocalDate.parse("2004-01-01");
        List<User> users = testDataService.getTestUserList();

        Mockito.when(localStorage.getAllUsers())
                .thenReturn(users);

        List<User> filteredUsers = userService.findUsersByDateRange(from, to);

        assertEquals(1, filteredUsers.size());

    }

    @Test
    public void getById() {

    }

    @Test
    public void createUser() {
        long id = 1;
        User user = testDataService.getTestUser();
        Mockito.when(localStorage.create(user))
                .thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals(id, createdUser.getId());
    }

    @Test
    public void createInvalidUser() {
        User user = testDataService.getTestUser();

        user.setEmail(null);
        assertThrows(BadRequestException.class,
                () -> userService.createUser(user));
        user.setEmail("");
        assertThrows(BadRequestException.class,
                () -> userService.createUser(user));

        user.setName(null);
        assertThrows(BadRequestException.class,
                () -> userService.createUser(user));
        user.setName("");
        assertThrows(BadRequestException.class,
                () -> userService.createUser(user));

        user.setLastName(null);
        assertThrows(BadRequestException.class,
                () -> userService.createUser(user));
        user.setLastName("");
        assertThrows(BadRequestException.class,
                () -> userService.createUser(user));

        LocalDate invalidBirthday = LocalDate.parse("2007-01-01");
        user.setBirthday(invalidBirthday);
        assertThrows(BadRequestException.class,
                () -> userService.createUser(user));
        user.setBirthday(null);
        assertThrows(BadRequestException.class,
                () -> userService.createUser(user));
    }

    @Test
    public void updateUser() {
        User user = testDataService.getTestUser();
        Mockito.when(localStorage.getById(anyLong()))
                .thenReturn(user);
        ReflectionTestUtils.setField(userService, "limitAge", 18);

        LocalDate invalidBirthday = LocalDate.parse("2007-01-01");
        String name = user.getName();
        String lastName = user.getLastName();
        String newEmail = "newemail@mail.com";
        User updatedUser = new User();
        updatedUser.setEmail(newEmail);
        updatedUser.setAddress("");
        updatedUser.setName(null);
        updatedUser.setLastName("");

        userService.updateUser(updatedUser);

        assertEquals(newEmail, user.getEmail());
        assertEquals("", user.getAddress());
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());

        updatedUser.setBirthday(invalidBirthday);
        assertThrows(BadRequestException.class,
                () -> userService.updateUser(updatedUser));

        Mockito.when(localStorage.getById(anyLong()))
                .thenReturn(null);
        assertThrows(NotFoundException.class,
                () -> userService.updateUser(updatedUser));
    }

    @Test
    public void deleteUser() {
        long id = 1;
        User user = testDataService.getTestUser();
        Mockito.when(localStorage.getById(anyLong()))
                .thenReturn(user);
        userService.deleteUser(id);
        verify(localStorage).delete(id);
    }
}