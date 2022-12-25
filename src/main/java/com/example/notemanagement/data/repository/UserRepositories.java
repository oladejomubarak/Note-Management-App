package com.example.notemanagement.data.repository;

import com.example.notemanagement.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositories extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
}
