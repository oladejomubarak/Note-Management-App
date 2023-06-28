package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.*;
import com.example.notemanagement.data.dtos.response.CreateUserResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.dtos.response.LoginResponse;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest createUserRequest);
    LoginResponse userLogin(LoginRequest loginRequest);
    GetResponse updateUser(UserUpdateRequest userUpdateRequest);
    GetResponse deleteUser(int id);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    String resetPassword(ResetPasswordRequest resetPasswordRequest);

}
