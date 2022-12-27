package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.data.dtos.request.EntriesUpdateRequest;
import com.example.notemanagement.data.dtos.response.CreateEntriesResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.model.Entries;

public interface EntriesServices {
    CreateEntriesResponse createEntries(CreateEntriesRequest createEntriesRequest);
    GetResponse updateEntries(EntriesUpdateRequest entriesUpdateRequest);
//    Entries findEntryByTitle (String title);
    Entries viewEntryById(int id);
    GetResponse deleteEntryById(int id);
    GetResponse deleteAllEntries();
}
