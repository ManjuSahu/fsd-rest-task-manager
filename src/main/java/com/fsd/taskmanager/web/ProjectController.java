package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.Project;
import com.fsd.taskmanager.data.User;
import com.fsd.taskmanager.repository.ProjectRepository;
import com.fsd.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach((item) -> {
            projects.add(item);
            System.out.println("item: " + item);
        });
        System.out.println(projects);
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody Project project) {
        Optional<User> managerOptional = userRepository.findById(project.getManagerId());
        if (managerOptional.isPresent()) {
            project.setManager(managerOptional.get());
            projectRepository.save(project);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateProject(@RequestBody Project project) {
        projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProject(@RequestParam Integer projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            projectRepository.delete(project.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
