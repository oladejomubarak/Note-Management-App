package com.example.notemanagement.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Data
@RequiredArgsConstructor

public class AppUser {
    @Id
    private Long id;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String firstname;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String lastname;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String emailAddress;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String password;

    private boolean isEnabled;


    public AppUser(String firstname, String lastname, String emailAddress, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.password = password;


    }
}
