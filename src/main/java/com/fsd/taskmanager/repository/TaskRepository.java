package com.fsd.taskmanager.repository;

import com.fsd.taskmanager.data.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
