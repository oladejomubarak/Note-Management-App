package com.example.notemanagement.data.repository;

import com.example.notemanagement.data.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepositories extends JpaRepository<Notes, Integer> {
}
