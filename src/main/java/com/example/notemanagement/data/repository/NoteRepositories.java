package com.example.notemanagement.data.repository;

import com.example.notemanagement.data.model.Entries;
import com.example.notemanagement.data.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepositories extends JpaRepository<Notes, Integer> {
    boolean existsNotesByNameIgnoreCase(String noteName);
}
