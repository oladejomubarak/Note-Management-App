package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.data.dtos.request.EntriesUpdateRequest;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.model.Entries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EntriesServicesImplTest {
    @Autowired
    private EntriesServicesImpl entriesServices;
    private CreateEntriesRequest createEntriesRequest;
    private CreateEntriesRequest createEntriesRequest1;
    private CreateEntriesRequest createEntriesRequest2;



    @BeforeEach
    void setUp() {
        createEntriesRequest = new CreateEntriesRequest();
        createEntriesRequest.setTitle("A title");
        createEntriesRequest.setBody("A body");
        //createEntriesRequest.setDateCreated(LocalDateTime.now());


        createEntriesRequest1 = new CreateEntriesRequest();
        createEntriesRequest1.setTitle("Another title");
        createEntriesRequest1.setBody("Another body");
        //createEntriesRequest1.setDateCreated(LocalDateTime.now());

        createEntriesRequest2 = new CreateEntriesRequest();
        createEntriesRequest2.setTitle("this title");
        createEntriesRequest2.setBody("this body");
        //createEntriesRequest2.setDateCreated(LocalDateTime.now());
    }
    @Test void testThatEntryCanBeCreated(){
        Entries entries = entriesServices.createEntries(createEntriesRequest2);

        assertNotNull(entries);
        //assertEquals("Entry created successfully", entries.getMessage());
//        assertEquals("Another title", createEntriesRequest1.getTitle());
//        assertEquals("Another body", createEntriesRequest1.getBody());
    }
    @Test void testThatEntryCanEdited(){
        EntriesUpdateRequest entriesUpdateRequest = new EntriesUpdateRequest();
        EntriesUpdateRequest entriesUpdateRequest1 = new EntriesUpdateRequest();
        entriesUpdateRequest1.setId(2);
        entriesUpdateRequest.setId(1);
        entriesUpdateRequest.setBody("updated body 1");
        entriesUpdateRequest1.setTitle("new title 2");
        GetResponse getResponse1 = entriesServices.updateEntries(entriesUpdateRequest1);

        GetResponse getResponse = entriesServices.updateEntries(entriesUpdateRequest);

        assertEquals("Entry updated successfully",getResponse.getMessage());
        assertEquals("Entry updated successfully", getResponse1.getMessage());

    }
    @Test void testThatEntryCanBeViewed(){
//        String entryToView = entriesServices.viewEntryById(1);
        com.example.notemanagement.data.model.Entries foundEntry = entriesServices.viewEntryById(1);
        assertEquals("A title", foundEntry.getTitle());
    }
    @Test void testThatEntryCanBeDeletedById(){
        GetResponse deleteResponseById = entriesServices.deleteEntryById(1);
        assertEquals("Entry has been deleted successfully", deleteResponseById.getMessage());
    }
    @Test void testThatAllEntriesCanBeCleared(){
        GetResponse deleteAllEntries = entriesServices.deleteAllEntries();
        assertEquals("All entries have been cleared", deleteAllEntries.getMessage());
    }
}