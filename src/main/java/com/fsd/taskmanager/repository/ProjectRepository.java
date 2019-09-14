package com.fsd.taskmanager.repository;

import com.fsd.taskmanager.data.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
