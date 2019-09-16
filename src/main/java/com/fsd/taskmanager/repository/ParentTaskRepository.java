package com.fsd.taskmanager.repository;

import com.fsd.taskmanager.data.entity.ParentTask;
import org.springframework.data.repository.CrudRepository;

public interface ParentTaskRepository extends CrudRepository<ParentTask, Integer> {
}
