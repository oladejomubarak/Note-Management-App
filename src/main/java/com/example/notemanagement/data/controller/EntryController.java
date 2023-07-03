package com.example.notemanagement.data.controller;

import com.example.notemanagement.data.dtos.request.CreateEntriesRequest;
import com.example.notemanagement.data.dtos.request.EntriesUpdateRequest;
import com.example.notemanagement.exception.ApiResponse;
import com.example.notemanagement.services.EntriesServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1")
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
    @GetMapping("/entries")
    public ResponseEntity<?> viewAllEntries(){
        return ResponseEntity.ok(entriesServices.getAllEntries());
    }
    @DeleteMapping("/deleteentry/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable int id){
        return ResponseEntity.ok(entriesServices.deleteEntryById(id));
    }
    @DeleteMapping("/deleteallentries")
    public ResponseEntity<?> deleteAllEntries(){
        return ResponseEntity.ok(entriesServices.deleteAllEntries());

    }
    @GetMapping("/search-entry/{keyword}")
    public ResponseEntity<?> searchEntryByKeyword (@PathVariable String keyword, HttpServletRequest httpServletRequest){
    ApiResponse apiResponse=ApiResponse.builder()
            .status(HttpStatus.OK.value())
            .data(entriesServices.findEntryByKeyword(keyword))
            .timeStamp(ZonedDateTime.now())
            .path(httpServletRequest.getRequestURI())
            .isSuccessful(true)
            .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
}
    @GetMapping("/search-entry/{titleKeyword}")
    public ResponseEntity<?> searchEntryByTitleKeyword (@PathVariable String titleKeyword, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(entriesServices.findEntryByTitleKeyword(titleKeyword))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/search-entry/{dateCreated}")
    public ResponseEntity<?> searchEntryByDateCreated (@PathVariable String dateCreated, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(entriesServices.findEntryByDateCreated(dateCreated))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/search-entry/{title}")
    public ResponseEntity<?> searchEntryByTitle (@PathVariable String title, HttpServletRequest httpServletRequest){
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(entriesServices.findEntryByTitle(title))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
