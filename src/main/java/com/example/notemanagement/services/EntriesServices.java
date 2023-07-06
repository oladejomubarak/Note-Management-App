package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.data.dtos.request.EntriesUpdateRequest;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.model.Entries;

import java.util.List;

public interface EntriesServices {
    Entries createEntries(CreateEntriesRequest createEntriesRequest);
    Entries updateEntries(int entryId, EntriesUpdateRequest entriesUpdateRequest);
//    Entries findEntryByTitle (String title);
    Entries viewEntryById(int id);
    GetResponse deleteEntryById(int id);
    GetResponse deleteAllEntries();

    List<Entries> getAllEntries();

    List<Entries> findEntryByKeyword(String keyword);
    List<Entries> findEntryByTitleKeyword(String titleKeyword);
    List <Entries> findEntryByDateCreated(String createdDate);
    List <Entries> findEntryByTitle(String entryTitle);
}
