package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.data.dtos.request.EntriesUpdateRequest;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.model.Entries;
import com.example.notemanagement.data.repository.EntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class EntriesServicesImpl implements EntriesServices{
    @Autowired
    private EntriesRepository entriesRepository;
    LocalDate dateNow = LocalDate.now();
    LocalTime timeNow = LocalTime.now();

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    String formattedDate = dateNow.format(dateFormatter);

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    String formattedTime = timeNow.format(timeFormatter);
    @Override
    public Entries createEntries(CreateEntriesRequest createEntriesRequest) {
        String[] words = createEntriesRequest.getTitle().split(" ");

        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            String firstLetter = word.substring(0, 1).toUpperCase();
            String restOfWord = word.substring(1);
            String capitalizedWord = firstLetter + restOfWord;
            sb.append(capitalizedWord).append(" ");
        }
        String modifiedTitle = sb.toString().trim();

        Entries entries = new Entries();
        entries.setDateCreated(formattedDate);
        entries.setTimeCreated(formattedTime);
        entries.setTitle(modifiedTitle);
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
    public Entries viewEntryById(int id) {
        return entriesRepository.findById(id).orElseThrow(()->
                new RuntimeException("Entry with the id "+ id +" does not exist"));
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
    public List<Entries> getAllEntries() {
        return entriesRepository.findAll();
    }

    @Override
    public List<Entries> findEntryByKeyword(String keyword) {
        boolean isValidKeyword = keyword.length() > 3;
        List <com.example.notemanagement.data.model.Entries> foundEntries = new ArrayList<>();
        for ( com.example.notemanagement.data.model.Entries entry: getAllEntries()) {
            if(isValidKeyword && (entry.getTitle().contains(keyword) || entry.getBody().contains(keyword)))
                foundEntries.add(entry);
        }
        return foundEntries;
    }

    @Override
    public List<Entries> findEntryByTitleKeyword(String titleKeyword) {
        boolean isValidKeyword = titleKeyword.length() > 3;
        List <com.example.notemanagement.data.model.Entries> foundEntries = new ArrayList<>();
        for ( com.example.notemanagement.data.model.Entries entry: getAllEntries()) {
            if(isValidKeyword && (entry.getTitle().contains(titleKeyword)))
                foundEntries.add(entry);
        }
        return foundEntries;
    }

    @Override
    public List<Entries> findEntryByDateCreated(String createdDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        var formattedDate = LocalDateTime.parse(createdDate, dateTimeFormatter);
        String dateInString = formattedDate.toString();
        List <Entries> foundEntries = new ArrayList<>();
        for ( Entries entry: getAllEntries()) {
            if(entry.getDateCreated().equals(dateInString))
                foundEntries.add(entry);
        }
        return foundEntries;
    }

    @Override
    public List<Entries> findEntryByTitle(String entryTitle) {
        List <com.example.notemanagement.data.model.Entries>foundEntries = new ArrayList<>();
        for (com.example.notemanagement.data.model.Entries entry: getAllEntries()
             ) { if (entry.getTitle().equalsIgnoreCase(entryTitle)) foundEntries.add(entry);

        }
        return foundEntries;
    }

}
