package com.example.notemanagement.data.repository;

import com.example.notemanagement.data.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmailAddressIgnoreCase(String email);

    boolean existsByEmailAddressIgnoreCase(String email);

//    String deleteAppUserByEmailAddress(String email);
    AppUser findAppUserByEmailAddressIgnoreCase(String email);
}
