package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.ConfirmationTokenRequest;
import com.example.notemanagement.data.dtos.request.CreateAppUserRequest;
import com.example.notemanagement.data.model.ConfirmationToken;
import jakarta.mail.MessagingException;

public interface AppUserService {

    String registerUser(CreateAppUserRequest createAppUserRequest) throws MessagingException;

    String confirmToken(ConfirmationTokenRequest confirmationTokenRequest);
}
