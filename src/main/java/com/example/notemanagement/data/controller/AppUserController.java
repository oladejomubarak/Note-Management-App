package com.example.notemanagement.data.controller;

import com.example.notemanagement.data.dtos.request.*;
import com.example.notemanagement.exception.ApiResponse;
import com.example.notemanagement.services.AppUserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAppUser(@RequestBody CreateAppUserRequest createAppUserRequest,
                                             HttpServletRequest httpServletRequest) throws MessagingException {

        try {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(appUserService.registerUser(createAppUserRequest))
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .message("Registration is successful, check your email for verification code")
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/user/{email}")
    public ResponseEntity<?> findUser(@PathVariable String email,
                                          HttpServletRequest httpServletRequest) {
        try {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(appUserService.findUserByEmailIgnoreCase(email))
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .message("User found")
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("confirm-token")
    public ResponseEntity<?> confirmToken(@RequestBody ConfirmationTokenRequest confirmationTokenRequest,
                                          HttpServletRequest httpServletRequest) {

        try {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(appUserService.confirmToken(confirmationTokenRequest))
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .message("you are successfully verified")
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/resend-token")
    public ResponseEntity<?> resendToken(@RequestBody ResendTokenRequest resendTokenRequest, HttpServletRequest httpServletRequest)
            throws MessagingException {
        try {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(appUserService.resendToken(resendTokenRequest))
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .message("token sent successfully, check your email")
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping ("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        try {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(appUserService.login(loginRequest))
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .message("You are successfully logged in")
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-appUser/{email}")
    public ResponseEntity<?> deleteAppUser(@PathVariable String email, HttpServletRequest httpServletRequest) {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(appUserService.deleteAppUser(email))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest,
                                            HttpServletRequest httpServletRequest) throws MessagingException {

        try {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(appUserService.forgotPassword(forgotPasswordRequest))
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .message("Token has been sent to yur email address you used to register")
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

        }
    }

    @PatchMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest,
                                           HttpServletRequest httpServletRequest) {
        try {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.OK.value())
                    .data(appUserService.resetPassword(resetPasswordRequest))
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .message("You are successfully logged in")
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .timeStamp(ZonedDateTime.now())
                    .path(httpServletRequest.getRequestURI())
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete-user")
    public ResponseEntity<?>deleteAppUser(@RequestBody String email){
        try {
            return ResponseEntity.ok(appUserService.deleteUserByEmail(email));
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete-all-tokens")
    public ResponseEntity<?>deleteAllTokens(){
        return ResponseEntity.ok(appUserService.deleteAllTokens());
    }
}
