package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateNoteRequest;
import com.example.notemanagement.data.dtos.request.NoteUpdateRequest;
import com.example.notemanagement.data.dtos.response.CreateNoteResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.model.Notes;
import com.example.notemanagement.data.repository.NoteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServicesImpl implements NoteServices{
    @Autowired
    private NoteRepositories noteRepositories;
    private final Notes notes = new Notes();
    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        notes.setName(createNoteRequest.getNoteName());
        noteRepositories.save(notes);
        CreateNoteResponse createNoteResponse = new CreateNoteResponse();
        createNoteResponse.setMessage("Note created successfully");
        createNoteResponse.setStatusCode(201);

        return createNoteResponse;
    }

    @Override
    public GetResponse updateNote(NoteUpdateRequest noteUpdateRequest) {
        Notes foundNote = noteRepositories.findById(noteUpdateRequest.getId())
                .orElseThrow(()-> new RuntimeException("Note not found"));
        foundNote.setName(noteUpdateRequest.getNoteName());
        noteRepositories.save(foundNote);
        return new GetResponse("note updated successfully");
    }

    @Override
    public GetResponse deleteNote(int id) {
        noteRepositories.deleteById(id);
        return new GetResponse("note deleted successfully");
    }
}
