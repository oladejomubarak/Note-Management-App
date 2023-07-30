package com.example.notemanagement.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@RequiredArgsConstructor
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;
    //@Email(message = "invalid email")
    private String emailAddress;
    private String password;

    private boolean isEnabled = false;

//    @OneToOne
//    private Notes note;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
//    private List<ConfirmationToken> confirmationTokens = new ArrayList<>();


    public AppUser(String firstname, String lastname, String emailAddress, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
