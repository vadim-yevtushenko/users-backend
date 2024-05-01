package com.example.usersbackend.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class User {

    long id;

    private String email;

    private String name;

    private String lastName;

    private LocalDate birthday;

    private String address;

    private String phoneNumber;

}
