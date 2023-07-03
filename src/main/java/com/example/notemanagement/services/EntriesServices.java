package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.data.dtos.request.EntriesUpdateRequest;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.model.Entries;

import java.util.List;

public interface EntriesServices {
    Entries createEntries(CreateEntriesRequest createEntriesRequest);
    GetResponse updateEntries(EntriesUpdateRequest entriesUpdateRequest);
//    Entries findEntryByTitle (String title);
    com.example.notemanagement.data.model.Entries viewEntryById(int id);
    GetResponse deleteEntryById(int id);
    GetResponse deleteAllEntries();

    List<com.example.notemanagement.data.model.Entries> getAllEntries();

    List<com.example.notemanagement.data.model.Entries> findEntryByKeyword(String keyword);
    List<com.example.notemanagement.data.model.Entries> findEntryByTitleKeyword(String titleKeyword);
    List <com.example.notemanagement.data.model.Entries> findEntryByDateCreated(String createdDate);
    List <com.example.notemanagement.data.model.Entries> findEntryByTitle(String entryTitle);
}
