package com.example.notemanagement.data.dtos.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
