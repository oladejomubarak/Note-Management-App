package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateUserRequest;
import com.example.notemanagement.data.dtos.request.LoginRequest;
import com.example.notemanagement.data.dtos.request.UserUpdateRequest;
import com.example.notemanagement.data.dtos.response.CreateUserResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.dtos.response.LoginResponse;
import com.example.notemanagement.data.model.User;
import com.example.notemanagement.data.repository.UserRepositories;
import com.example.notemanagement.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepositories userRepositories;

    private final User user = new User();
    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        if (!UserValidator.isValidEmail(createUserRequest.getEmail())) throw new RuntimeException(
                String.format("The email %s is invalid", createUserRequest.getEmail()));
        if (!UserValidator.isValidPassword(createUserRequest.getPassword())) throw new RuntimeException(
                String.format("The password %s is invalid", createUserRequest.getPassword()));
        if (!UserValidator.isValidPhoneNumber(createUserRequest.getPhoneNumber())) throw new RuntimeException(
                String.format("The phone number %s is invalid", createUserRequest.getPhoneNumber()));


        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        user.setPhoneNumber(createUserRequest.getPhoneNumber());


        userRepositories.save(user);

        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setMessage("User created successfully");
        createUserResponse.setStatusCode(201);

        return createUserResponse;

    }

    @Override
    public LoginResponse userLogin(LoginRequest loginRequest) {
        User findUser = userRepositories.findUserByEmail(loginRequest.getEmail());
        if(findUser.getEmail().isEmpty()) throw new RuntimeException("Email not found");
        //findUser.setEmail(loginRequest.getEmail());
        findUser.setPassword(loginRequest.getPassword());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Login is successful");
        return loginResponse;
    }

    @Override
    public GetResponse updateUser(UserUpdateRequest userUpdateRequest) {
        User foundUser = userRepositories.findById(userUpdateRequest.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        foundUser.setEmail(userUpdateRequest.getEmail() != null
                && !userUpdateRequest.getEmail().equals("") ? userUpdateRequest.getEmail() : foundUser.getEmail());
        foundUser.setPassword(userUpdateRequest.getPassword() != null
                && !userUpdateRequest.getPassword().equals("") ? userUpdateRequest.getPassword() : foundUser.getPassword());
        foundUser.setFirstName(userUpdateRequest.getFirstName() != null
                && !userUpdateRequest.getFirstName().equals("") ? userUpdateRequest.getFirstName() : foundUser.getFirstName());
        foundUser.setLastName(userUpdateRequest.getLastName() != null
                && !userUpdateRequest.getLastName().equals("") ? userUpdateRequest.getLastName() : foundUser.getLastName());
        foundUser.setPhoneNumber(userUpdateRequest.getPhoneNumber() != null
                && !userUpdateRequest.getPhoneNumber().equals("") ? userUpdateRequest.getPhoneNumber() : foundUser.getPhoneNumber());
        userRepositories.save(foundUser);
        return new GetResponse("User detail updated successfully");
    }
    @Override
    public GetResponse deleteUser(int id) {
        userRepositories.deleteById(id);
        return new GetResponse("User deleted successfully");
    }
}
