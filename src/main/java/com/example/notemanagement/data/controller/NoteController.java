package com.example.notemanagement.data.controller;

import com.example.notemanagement.data.dtos.request.AddEntriesRequest;
import com.example.notemanagement.data.dtos.request.CreateNoteRequest;
import com.example.notemanagement.data.dtos.request.NoteUpdateRequest;
import com.example.notemanagement.services.NoteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {
    @Autowired
    private NoteServices noteServices;

    @PostMapping("/createnote")
    public ResponseEntity<?> createNote(@RequestBody CreateNoteRequest createNoteRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(noteServices.createNote(createNoteRequest));
    }
    @PatchMapping ("/updatenote")
    public ResponseEntity<?> updateNote(@RequestBody NoteUpdateRequest noteUpdateRequest){
        return ResponseEntity.ok(noteServices.updateNote(noteUpdateRequest));
    }
    @DeleteMapping("/deletenote/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable int id){
        return ResponseEntity.ok(noteServices.deleteNote(id));
    }
    @PostMapping("/addentry")
    public ResponseEntity<?> addEntries(@RequestBody AddEntriesRequest addEntriesRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(noteServices.addEntries(addEntriesRequest));

    }
}
