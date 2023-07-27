package com.example.notemanagement.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Entity
@RequiredArgsConstructor
@Table(name = "confirmation_token")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String token;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    @NotNull
    private LocalDateTime expiredAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "app_user_token", referencedColumnName = "id")
    private AppUser user;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, AppUser user){
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }
}
