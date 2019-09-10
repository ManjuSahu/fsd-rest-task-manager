package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.User;
import com.fsd.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository taskRepository;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        taskRepository.findAll().forEach((item) -> {
            users.add(item);
            System.out.println("item: "+item);
        });
        System.out.println(users);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        taskRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
