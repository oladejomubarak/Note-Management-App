package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.data.dtos.request.EntriesUpdateRequest;
import com.example.notemanagement.data.dtos.response.CreateEntriesResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.model.Entries;
import com.example.notemanagement.data.repository.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EntriesServicesImpl implements EntriesServices{
    @Autowired
    private EntriesRepository entriesRepository;
    private final Entries entries = new Entries();

    @Override
    public CreateEntriesResponse createEntries(CreateEntriesRequest createEntriesRequest) {
        entries.setLocalDateTime(LocalDateTime.now());
        entries.setTitle(createEntriesRequest.getTitle());
        entries.setBody(createEntriesRequest.getBody());
        Entries savedEntry = entriesRepository.save(entries);
        CreateEntriesResponse createEntriesResponse = new CreateEntriesResponse();
        createEntriesResponse.setMessage("Entry created successfully");
        createEntriesResponse.setId(savedEntry.getId());
        createEntriesResponse.setStatusCode(201);

        return createEntriesResponse;

    }

    @Override
    public GetResponse updateEntries(EntriesUpdateRequest entriesUpdateRequest) {
        Entries foundEntries = entriesRepository.findById(entriesUpdateRequest.getId())
                .orElseThrow(() -> new RuntimeException("Entry not found"));
        foundEntries.setLocalDateTime(LocalDateTime.now());
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
    public Entries viewEntryById(int id) {
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
}
