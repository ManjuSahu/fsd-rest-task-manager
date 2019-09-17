package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.User;
import com.fsd.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach((item) -> {
            users.add(item);
            System.out.println("item: "+item);
        });
        System.out.println(users);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        Optional<User> user1 = userRepository.findById(user.getUserId());
        if(user1.isPresent()) {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
