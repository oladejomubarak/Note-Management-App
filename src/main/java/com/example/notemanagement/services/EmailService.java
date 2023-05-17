package com.example.notemanagement.services;

import jakarta.mail.MessagingException;

public interface EmailService {
    void send(String to, String email) throws MessagingException;
}
