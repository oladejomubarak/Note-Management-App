package com.example.notemanagement.data.controller;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.services.EntriesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntryController {
    @Autowired
private EntriesServices entriesServices;

    @PostMapping("/entry")
    public ResponseEntity<?> createEntries(@RequestBody CreateEntriesRequest createEntriesRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(entriesServices.createEntries(createEntriesRequest));
    }

}
