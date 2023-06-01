package com.example.notemanagement.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@RequiredArgsConstructor

public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String firstname;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String lastname;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    @Email(message = "The email is invalid")
    private String emailAddress;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String password;

    private boolean isEnabled = false;

    @OneToOne
    private Notes note;


    public AppUser(String firstname, String lastname, String emailAddress, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
