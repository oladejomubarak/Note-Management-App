package com.example.notemanagement.data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
public class Entries {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(length = 50000)
    private String body;
    private String title;
    private String dateCreated;
    private String timeCreated;
}
