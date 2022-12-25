package com.example.notemanagement.data.repository;

import com.example.notemanagement.data.model.Entries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntriesRepository extends JpaRepository<Entries, Integer> {
    Optional<Entries> findEntriesByTitle(String title);
}
