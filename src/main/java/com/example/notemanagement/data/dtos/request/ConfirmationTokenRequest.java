package com.example.notemanagement.data.dtos.request;

import lombok.Data;

@Data
public class ConfirmationTokenRequest {
    private String token;
    private String email;
}
