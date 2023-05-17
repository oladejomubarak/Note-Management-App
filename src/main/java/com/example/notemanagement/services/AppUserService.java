package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateAppUserRequest;

public interface AppUserService {

    String registerUser(CreateAppUserRequest createAppUserRequest);
}
