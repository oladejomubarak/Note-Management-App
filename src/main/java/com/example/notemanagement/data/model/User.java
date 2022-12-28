package com.example.notemanagement.data.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToOne
    private Notes notes;
}
