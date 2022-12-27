package com.example.notemanagement.data.controller;

import com.example.notemanagement.data.dtos.request.CreateUserRequest;
import com.example.notemanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest){
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserRequest));

}
}
