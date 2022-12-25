package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateNoteRequest;
import com.example.notemanagement.data.dtos.request.NoteUpdateRequest;
import com.example.notemanagement.data.dtos.response.CreateNoteResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class NoteServicesImplTest {

    @Autowired
    private NoteServices noteServices;
    private CreateNoteRequest createNoteRequest;
    private CreateNoteRequest createNoteRequest1;
    @BeforeEach
    void setUp() {
        createNoteRequest = new CreateNoteRequest();
        createNoteRequest1 = new CreateNoteRequest();
        createNoteRequest.setNoteName("My Academic diary");
        createNoteRequest1.setNoteName("Another Note");

    }
    @Test
    void testThatNoteNameCanBeSetAsNoteIsCreated(){
//        CreateNoteResponse createNoteResponse = noteServices.createNote(createNoteRequest);
//        assertNotNull(createNoteResponse);
        CreateNoteResponse createNoteResponse1 = noteServices.createNote(createNoteRequest1);
        assertNotNull(createNoteResponse1);
        assertEquals("Note created successfully",createNoteResponse1.getMessage());
        //assertEquals(201, createNoteResponse.getStatusCode());
    }
    @Test void testThatNoteNameCanBeEdited(){
        NoteUpdateRequest noteUpdateRequest = new  NoteUpdateRequest();
        noteUpdateRequest.setId(1);
        noteUpdateRequest.setNoteName("My Live Diary");
        GetResponse getUpdateResponse = noteServices.updateNote(noteUpdateRequest);
        assertEquals("note updated successfully", getUpdateResponse.getMessage());
    }
    @Test void testThatNoteCanBeDeleted(){
        GetResponse getDeleteResponse =noteServices.deleteNote(2);
        assertEquals("note deleted successfully", getDeleteResponse.getMessage());
    }
}