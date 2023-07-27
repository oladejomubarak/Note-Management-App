package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.AddEntriesRequest;
import com.example.notemanagement.data.dtos.request.CreateNoteRequest;
import com.example.notemanagement.data.dtos.request.NoteUpdateRequest;
import com.example.notemanagement.data.dtos.response.CreateNoteResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.model.Entries;
import com.example.notemanagement.data.model.Notes;
import com.example.notemanagement.data.repository.NoteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoteServicesImpl implements NoteServices{
    @Autowired
    private NoteRepositories noteRepositories;
    private final Notes notes = new Notes();
    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        if(noteRepositories.existsNotesByNameIgnoreCase(createNoteRequest.getNoteName())) throw new IllegalStateException(
                "A note with such name exists already, choose another note name");
        notes.setName(createNoteRequest.getNoteName());
        Notes savedNote = noteRepositories.save(notes);
        CreateNoteResponse createNoteResponse = new CreateNoteResponse();
        createNoteResponse.setMessage("Note created successfully");
        createNoteRequest.setId(savedNote.getId());
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

    @Override
    public GetResponse addEntries(AddEntriesRequest addEntriesRequest) {
        Notes foundNote = noteRepositories.findById(addEntriesRequest.getId()).orElseThrow(()->
                new RuntimeException("Note not found"));
        Entries entries = new Entries();
        entries.setTitle(addEntriesRequest.getTitle());
        entries.setBody(addEntriesRequest.getBody());
        entries.setCreatedDate(LocalDateTime.now().toString());
        foundNote.getEntries().add(entries);
        noteRepositories.save(foundNote);
        return new GetResponse("Entry added successfully");
    }
}
