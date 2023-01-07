package com.example.notemanagement.data.repository;

import com.example.notemanagement.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositories extends JpaRepository<User, Integer> {
    Optional <User> findUserByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
}
