package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.User;
import com.fsd.taskmanager.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController = new UserController();

    @Test
    public void getUsers() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<User>());
        assertEquals(2, userController.getUsers().getBody().size());
    }

    @Test
    public void createUser() throws Exception {

    }

    @Test
    public void updateUser() throws Exception {

    }

    @Test
    public void deleteUser() throws Exception {

    }

}