package com.fsd.taskmanager.repository;

import com.fsd.taskmanager.data.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {
}
