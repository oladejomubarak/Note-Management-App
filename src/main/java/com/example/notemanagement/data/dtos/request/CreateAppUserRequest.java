package com.example.notemanagement.data.dtos.request;

import com.example.notemanagement.data.model.Notes;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAppUserRequest {

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String firstname;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String lastname;
    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    @Email(message = "The email is invalid")
    private String emailAddress;

    @NotNull(message = "This field is required")
    @NotEmpty(message = "This field is required")
    private String password;

    private String confirmPassword;

}
