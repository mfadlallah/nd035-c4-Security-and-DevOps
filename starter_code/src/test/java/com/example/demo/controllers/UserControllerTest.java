package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    private String userName = "test";
    private long userId = 1L;

    private User user;


    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectMocks(userController,"userRepository", userRepository);
        TestUtils.injectMocks(userController,"cartRepository", cartRepository);
        TestUtils.injectMocks(userController,"bCryptPasswordEncoder", bCryptPasswordEncoder);

        user = new User();
        user.setId(1L);
        user.setUsername(userName);
    }

    @Test
    public void testCreateUser(){
        // given
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("fadl");
        createUserRequest.setPassword("1234567");
        createUserRequest.setConfirmPassword("1234567");

        // when
        ResponseEntity<User> userResponseEntity = userController.createUser(createUserRequest);
        User user = userResponseEntity.getBody();

        // then
        assertNotNull(userResponseEntity);
        assertNotNull(user);
        assertEquals(user.getUsername(), createUserRequest.getUsername());
        assertEquals(0, user.getId());
    }

    @Test
    public void testFindUserById(){
        // given
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        ResponseEntity<User> userResponseEntity = userController.findById(userId);
        User user = userResponseEntity.getBody();

        // then
        assertNotNull(userResponseEntity);
        assertNotNull(user);
        assertEquals(user.getId(), userId);
    }

    @Test
    public void testFindUserByName(){
        // given
        when(userRepository.findByUsername(userName)).thenReturn(user);

        // when
        ResponseEntity<User> userResponseEntity = userController.findByUserName(userName);
        User user = userResponseEntity.getBody();

        // then
        assertNotNull(userResponseEntity);
        assertNotNull(user);
        assertEquals(user.getUsername(), userName);
    }

}
