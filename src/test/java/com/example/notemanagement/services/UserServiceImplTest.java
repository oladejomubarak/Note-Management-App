package com.example.notemanagement.services;

import com.example.notemanagement.data.dtos.request.CreateUserRequest;
import com.example.notemanagement.data.dtos.request.LoginRequest;
import com.example.notemanagement.data.dtos.request.UserUpdateRequest;
import com.example.notemanagement.data.dtos.response.CreateUserResponse;
import com.example.notemanagement.data.dtos.response.GetResponse;
import com.example.notemanagement.data.dtos.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    private CreateUserRequest createUserRequest;
    private CreateUserRequest createUserRequest1;
    private CreateUserRequest createUserRequest2;
    private CreateUserRequest createUserRequest3;

    @BeforeEach
    void setUp() {
        createUserRequest = new CreateUserRequest();
        createUserRequest.setFirstName("Mub");
        createUserRequest.setLastName("Ola");
        createUserRequest.setEmail("oladejomubarakade@gmail.com");
        createUserRequest.setPassword("Adeshina@100");
        createUserRequest.setPhoneNumber("08162768079999");

        createUserRequest1 = new CreateUserRequest();
        createUserRequest1.setFirstName("Lob");
        createUserRequest1.setLastName("Bolu");
        createUserRequest1.setEmail("oladejomubarak@gmail.com");
        createUserRequest1.setPassword("Adeshina@101");
        createUserRequest1.setPhoneNumber("08142768079999");

        createUserRequest2 = new CreateUserRequest();
        createUserRequest2.setFirstName("Dola");
        createUserRequest2.setLastName("Nun");
        createUserRequest2.setEmail("olabomubarakade@gmail.com");
        createUserRequest2.setPassword("Odeshina@100");
        createUserRequest2.setPhoneNumber("08062768079999");

//        createUserRequest3 = new CreateUserRequest();
//        createUserRequest3.setFirstName("Nb");
//        createUserRequest3.setLastName("Bla");
//        createUserRequest3.setEmail("oladejomubarak@yahoo.com");
//        createUserRequest3.setPassword("Adeshina@123");
//        createUserRequest3.setPhoneNumber("08162768079913");


    }
    @Test
    void testUserCanRegister(){
//        CreateUserResponse createUserResponse = userService.createUser(createUserRequest);
        CreateUserResponse createUserResponse1 = userService.createUser(createUserRequest1);
//        CreateUserResponse createUserResponse2 = userService.createUser(createUserRequest2);
//        assertNotNull(createUserResponse);
        assertNotNull(createUserResponse1);
//        assertNotNull(createUserResponse2);

//        assertEquals("User created successfully", createUserResponse.getMessage());
        assertEquals("User created successfully", createUserResponse1.getMessage());
//        assertEquals("User created successfully", createUserResponse2.getMessage());
    }

    @Test void testThatUserCanLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(createUserRequest1.getEmail());
        loginRequest.setPassword(createUserRequest1.getPassword());
        LoginResponse loginResponse = userService.userLogin(loginRequest);
        assertEquals("Login is successful", loginResponse.getMessage());
    }

    @Test
    void testThatUserDetailsCanBeUpdated() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(1);
        userUpdateRequest.setEmail("Ilovewritingjava@gmail.com");

        GetResponse getResponse = userService.updateUser(userUpdateRequest);
        assertEquals("User detail updated successfully", getResponse.getMessage());
    }
    @Test
    void testThatUserCanBeDeleted(){
        GetResponse deleteUser = userService.deleteUser(2);
        assertEquals("User deleted successfully", deleteUser.getMessage());
    }
}