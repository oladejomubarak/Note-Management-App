package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.AddEntriesRequest;
import com.example.notemanagement.data.dtos.request.CreateNoteRequest;
import com.example.notemanagement.data.dtos.request.NoteUpdateRequest;
import com.example.notemanagement.data.dtos.response.CreateNoteResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;

public interface NoteServices {
    CreateNoteResponse createNote(CreateNoteRequest createNoteRequest);
    GetResponse updateNote(NoteUpdateRequest noteUpdateRequest);
    GetResponse deleteNote(int id);

    GetResponse addEntries(AddEntriesRequest addEntriesRequest);
}
