package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.data.dtos.request.EntriesUpdateRequest;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.model.Entries;
import com.example.notemanagement.data.repository.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntriesServicesImpl implements EntriesServices{
    @Autowired
    private EntriesRepository entriesRepository;
    private final com.example.notemanagement.data.model.Entries entries = new com.example.notemanagement.data.model.Entries();

    @Override
    public Entries createEntries(CreateEntriesRequest createEntriesRequest) {
        Entries entries = new Entries();
        entries.setDateCreated(LocalDateTime.now().toString());
        entries.setTitle(createEntriesRequest.getTitle());
        entries.setBody(createEntriesRequest.getBody());
        entriesRepository.save(entries);

//        entries.setMessage("Entry created successfully");
//        entries.setId(savedEntry.getId());
//        entries.setStatusCode(201);

        return entries;
    }

    @Override
    public GetResponse updateEntries(EntriesUpdateRequest entriesUpdateRequest) {
        com.example.notemanagement.data.model.Entries foundEntries = entriesRepository.findById(entriesUpdateRequest.getId())
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        foundEntries.setDateCreated(LocalDateTime.now().toString());
        foundEntries.setTitle(entriesUpdateRequest.getTitle() != null && !entriesUpdateRequest.getTitle().equals("")
                ? entriesUpdateRequest.getTitle() : foundEntries.getTitle());
        foundEntries.setBody(entriesUpdateRequest.getBody() != null && !entriesUpdateRequest.getBody().equals("")
                ? entriesUpdateRequest.getBody() : foundEntries.getBody());
        entriesRepository.save(foundEntries);
        return new GetResponse("Entry updated successfully");
    }
//    @Override
//    public Entries findEntryByTitle(String title) {
//
//        return entriesRepository.findEntriesByTitle(title).orElseThrow(()->
//                new RuntimeException("Entry with the title: "+ title +"does not exist"));
//    }
    @Override
    public com.example.notemanagement.data.model.Entries viewEntryById(int id) {
        return entriesRepository.findById(id).orElseThrow(()->
                new RuntimeException("Entry with the id"+ id +"does not exist"));
    }

    @Override
    public GetResponse deleteEntryById(int id) {
        entriesRepository.deleteById(id);
        return new GetResponse("Entry has been deleted successfully");
    }

    @Override
    public GetResponse deleteAllEntries() {
        entriesRepository.deleteAll();
        return new GetResponse("All entries have been cleared");
    }

    @Override
    public List<com.example.notemanagement.data.model.Entries> getAllEntries() {
        return entriesRepository.findAll();
    }

    @Override
    public List<com.example.notemanagement.data.model.Entries> findEntryByKeyword(String keyword) {
        boolean isValidKeyword = keyword.length() > 3;
        List <com.example.notemanagement.data.model.Entries> foundEntries = new ArrayList<>();
        for ( com.example.notemanagement.data.model.Entries entry: getAllEntries()) {
            if(isValidKeyword && (entry.getTitle().contains(keyword) || entry.getBody().contains(keyword)))
                foundEntries.add(entry);
        }
        return foundEntries;
    }

    @Override
    public List<com.example.notemanagement.data.model.Entries> findEntryByTitleKeyword(String titleKeyword) {
        boolean isValidKeyword = titleKeyword.length() > 3;
        List <com.example.notemanagement.data.model.Entries> foundEntries = new ArrayList<>();
        for ( com.example.notemanagement.data.model.Entries entry: getAllEntries()) {
            if(isValidKeyword && (entry.getTitle().contains(titleKeyword)))
                foundEntries.add(entry);
        }
        return foundEntries;
    }

    @Override
    public List<com.example.notemanagement.data.model.Entries> findEntryByDateCreated(String createdDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        var formattedDate = LocalDateTime.parse(createdDate, dateTimeFormatter);
        List <com.example.notemanagement.data.model.Entries> foundEntries = new ArrayList<>();
        for ( com.example.notemanagement.data.model.Entries entry: getAllEntries()) {
            if(entry.getDateCreated().equals(formattedDate))
                foundEntries.add(entry);
        }
        return foundEntries;
    }

    @Override
    public List<com.example.notemanagement.data.model.Entries> findEntryByTitle(String entryTitle) {
        List <com.example.notemanagement.data.model.Entries>foundEntries = new ArrayList<>();
        for (com.example.notemanagement.data.model.Entries entry: getAllEntries()
             ) { if (entry.getTitle().equalsIgnoreCase(entryTitle)) foundEntries.add(entry);

        }
        return foundEntries;
    }

}
