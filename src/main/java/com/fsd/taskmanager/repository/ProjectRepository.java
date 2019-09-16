package com.fsd.taskmanager.repository;

import com.fsd.taskmanager.data.entity.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
