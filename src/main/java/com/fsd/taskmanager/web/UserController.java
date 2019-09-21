package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.User;
import com.fsd.taskmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        LOGGER.info("getUsers invoked");
        userRepository.findAll().forEach((item) -> {
            users.add(item);
        });
        LOGGER.info("getUsers response {}", users);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        LOGGER.info("createUser invoked with {}", user);
        userRepository.save(user);
        LOGGER.info("createUser successfully created");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        LOGGER.info("updateUser invoked with {}", user);
        Optional<User> user1 = userRepository.findById(user.getUserId());
        if (user1.isPresent()) {
            userRepository.save(user);
            LOGGER.info("updateUser successfully updated");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        LOGGER.error("updateUser led to resource not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        LOGGER.info("deleteUser invoked with {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            LOGGER.info("deleteUser successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            LOGGER.error("deleteUser led to resource not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
