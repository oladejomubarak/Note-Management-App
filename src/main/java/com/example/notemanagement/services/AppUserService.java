package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.*;
import com.example.notemanagement.data.model.AppUser;
import com.example.notemanagement.data.model.ConfirmationToken;
import jakarta.mail.MessagingException;

import java.util.Optional;

public interface AppUserService {

    String registerUser(CreateAppUserRequest createAppUserRequest) throws MessagingException;

    String confirmToken(ConfirmationTokenRequest confirmationTokenRequest);
    String resendToken (ResendTokenRequest resendTokenRequest) throws MessagingException;

    String login(LoginRequest loginRequest);
    String deleteAppUser(String email);

    String changePassword(ChangePasswordRequest changePasswordRequest);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException;
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
    AppUser findUserByEmailIgnoreCase(String email);
    String deleteUserByEmail(String email);
    String deleteAllTokens();
}
