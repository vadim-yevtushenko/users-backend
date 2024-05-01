package com.example.usersbackend.api.controller;

import com.example.usersbackend.BaseTest;
import com.example.usersbackend.TestDataService;
import com.example.usersbackend.api.exception.badrequest.BadRequestException;
import com.example.usersbackend.api.exception.badrequest.BadRequestExceptionHandler;
import com.example.usersbackend.model.User;
import com.example.usersbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerTest extends BaseTest {

    @Autowired
    private TestDataService testDataService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestController userRestController;

    private MockMvc mockMvc;

    private ObjectWriter objectWriter;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(userRestController)
                .setControllerAdvice(new BadRequestExceptionHandler())
                .build();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectWriter = objectMapper.writer();
    }

    @Test
    public void getUsersWithRightDateRange() throws Exception {
        List<User> users = testDataService.getTestUserList();
        Mockito.when(userService.findUsersByDateRange(LocalDate.parse("2000-01-01"), LocalDate.parse("2010-01-01")))
                .thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users?from=2000-01-01&to=2010-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[2].name", is("User3")));
    }

    @Test
    public void getUsersWithWrongDateRange() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users?from=2010-01-01&to=2000-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertEquals("Date from should be less than to.", result.getResolvedException().getMessage()));

    }

    @Test
    public void create() throws Exception {
        User user = testDataService.getTestUser();

        Mockito.when(userService.createUser(any(User.class)))
                .thenReturn(user);

        String content = objectWriter.writeValueAsString(user);

        MockHttpServletRequestBuilder mockPostRequest = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockPostRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("User1")));
    }

    @Test
    public void update() {
        User user = mock(User.class);
        userRestController.update(user);
        verify(userService).updateUser(user);
    }

    @Test
    public void delete() {
        long id = 1;
        userRestController.delete(id);
        verify(userService).deleteUser(id);
    }
}