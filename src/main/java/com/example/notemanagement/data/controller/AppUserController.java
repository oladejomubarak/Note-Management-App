package com.example.notemanagement.data.controller;

import com.example.notemanagement.data.dtos.request.ConfirmationTokenRequest;
import com.example.notemanagement.data.dtos.request.CreateAppUserRequest;
import com.example.notemanagement.data.dtos.request.ResendTokenRequest;
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
@CrossOrigin(origins = "*")
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

    @PatchMapping("confirm-token")
    public ResponseEntity<?> confirmToken(@RequestBody ConfirmationTokenRequest confirmationTokenRequest,
                                          HttpServletRequest httpServletRequest){

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(appUserService.confirmToken(confirmationTokenRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/resend-token")
    public ResponseEntity<?> resendToken(@RequestBody ResendTokenRequest resendTokenRequest, HttpServletRequest httpServletRequest)
            throws MessagingException{
        ApiResponse apiResponse=ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(appUserService.resendToken(resendTokenRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
