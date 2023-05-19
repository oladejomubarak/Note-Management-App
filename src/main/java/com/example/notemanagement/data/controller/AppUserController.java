package com.example.notemanagement.data.controller;

import com.example.notemanagement.data.dtos.request.CreateAppUserRequest;
import com.example.notemanagement.exception.ApiResponse;
import com.example.notemanagement.services.AppUserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAppUser(@RequestBody CreateAppUserRequest createAppUserRequest,
                                             HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(appUserService.registerUser(createAppUserRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
