package com.example.notemanagement.data.controller;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.data.dtos.request.EntriesUpdateRequest;
import com.example.notemanagement.services.EntriesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EntryController {
    @Autowired
private EntriesServices entriesServices;

    @PostMapping("/entry")
    public ResponseEntity<?> createEntries(@RequestBody CreateEntriesRequest createEntriesRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(entriesServices.createEntries(createEntriesRequest));
    }
    @PatchMapping("/updateentry")
    public ResponseEntity<?> updateEntries(@RequestBody EntriesUpdateRequest entriesUpdateRequest){
        return ResponseEntity.ok(entriesServices.updateEntries(entriesUpdateRequest));
    }
    @GetMapping("/viewentry/{id}")
    public ResponseEntity<?> viewEntryById(@PathVariable int id){
        return ResponseEntity.ok(entriesServices.viewEntryById(id));
    }
    @DeleteMapping("/deleteentry/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable int id){
        return ResponseEntity.ok(entriesServices.deleteEntryById(id));
    }
    @DeleteMapping("deleteallentries")
    public ResponseEntity<?> deleteAllEntries(){
        return ResponseEntity.ok(entriesServices.deleteAllEntries());
    }
}
