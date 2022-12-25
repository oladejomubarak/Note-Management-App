package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateUserRequest;
import com.example.notemanagement.data.dtos.request.LoginRequest;
import com.example.notemanagement.data.dtos.request.UserUpdateRequest;
import com.example.notemanagement.data.dtos.response.CreateUserResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.dtos.response.LoginResponse;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest createUserRequest);
    LoginResponse userLogin(LoginRequest loginRequest);
    GetResponse updateUser(UserUpdateRequest userUpdateRequest);
    GetResponse deleteUser(int id);

}
