package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateAppUserRequest;
import com.example.notemanagement.data.model.AppUser;
import com.example.notemanagement.data.repository.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService{

    private AppUserRepository appUserRepository;

    @Override
    public String registerUser(CreateAppUserRequest createAppUserRequest) {
        boolean emailExist = appUserRepository.existsAppUsersByEmailAddressIgnoreCase(createAppUserRequest.getEmailAddress()
        );
        if(emailExist) throw new IllegalStateException("email has been taken already, choose another email");
        AppUser foundUser = appUserRepository.findAppUserByEmailAddressIgnoreCase(createAppUserRequest.getEmailAddress()).get();
        return null;
    }
}
