package com.example.notemanagement.data.dtos.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
    private String confirmNewPassword;
    private String token;
}
