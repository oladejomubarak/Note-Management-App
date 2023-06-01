package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.*;
import com.example.notemanagement.data.model.ConfirmationToken;
import jakarta.mail.MessagingException;

public interface AppUserService {

    String registerUser(CreateAppUserRequest createAppUserRequest) throws MessagingException;

    String confirmToken(ConfirmationTokenRequest confirmationTokenRequest);
    String resendToken (ResendTokenRequest resendTokenRequest) throws MessagingException;

    String login(LoginRequest loginRequest);
    String deleteAppUser(String email);

    String changePassword(ChangePasswordRequest changePasswordRequest);
}
