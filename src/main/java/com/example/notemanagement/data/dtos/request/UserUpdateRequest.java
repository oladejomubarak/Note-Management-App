package com.example.notemanagement.data.dtos.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private int id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
