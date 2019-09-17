package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.User;
import com.fsd.taskmanager.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController = new UserController();

    private User user;

    private List<User> users = new ArrayList<>();

    public UserControllerTest() {
        user = User.builder().userId(1).employeeId(111).firstName("Manju").lastName("Sahu").build();
        List<User> users = new ArrayList<>();
        User user1 = User.builder().userId(2).employeeId(222).firstName("Sathya").lastName("Mani").build();
        users.add(user);
        users.add(user1);
    }

    @Before
    public void init() {
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findById(3)).thenReturn(Optional.empty());

    }

    @Test
    public void getUsers() throws Exception {
        assertEquals(2, userController.getUsers().getBody().size());
    }

    @Test
    public void createUser() throws Exception {
        assertEquals(HttpStatus.CREATED, userController.createUser(User.builder().build()).getStatusCode());
    }

    @Test
    public void updateUser() throws Exception {
        assertEquals(HttpStatus.OK, userController.updateUser(user).getStatusCode());
    }

    @Test
    public void updateInvalidUser() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND, userController.updateUser(User.builder().build()).getStatusCode());
    }

    @Test
    public void deleteUser() throws Exception {
        assertEquals(HttpStatus.OK, userController.deleteUser(1).getStatusCode());
    }

    @Test
    public void deleteInvalidUser() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND, userController.deleteUser(3).getStatusCode());
    }

}