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
        boolean emailExist = appUserRepository.existsAppUsersByEmailAddressIgnoreCase(createAppUserRequest.getEmailAddress());
        if(emailExist) throw new IllegalStateException("email has been taken already, choose another email");
        AppUser foundUser = appUserRepository.findAppUserByEmailAddressIgnoreCase(createAppUserRequest.getEmailAddress())
                .get();
        AppUser appUser = new AppUser();
        appUser.setFirstname(createAppUserRequest.getFirstname());
        appUser.setLastname(createAppUserRequest.getLastname());
        appUser.setEmailAddress(createAppUserRequest.getEmailAddress());
        appUser.setPassword(createAppUserRequest.getPassword());
        appUser.setPassword(createAppUserRequest.getConfirmPassword());
        if (!createAppUserRequest.getPassword().equals(createAppUserRequest.getConfirmPassword()))
            throw new IllegalStateException("passwords do not match");
        appUserRepository.save(appUser);
        return null;
    }
}
