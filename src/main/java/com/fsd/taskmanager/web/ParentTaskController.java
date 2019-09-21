package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.ParentTask;
import com.fsd.taskmanager.data.entity.Project;
import com.fsd.taskmanager.repository.ParentTaskRepository;
import com.fsd.taskmanager.repository.ProjectRepository;
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
@RequestMapping("/parentTasks")
public class ParentTaskController {

    @Autowired
    private ParentTaskRepository parentTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectController projectController;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParentTaskController.class);

    @GetMapping
    public ResponseEntity<List<ParentTask>> getParentTasks() {
        LOGGER.info("getParentTasks invoked");
        List<ParentTask> parentTasks = new ArrayList<>();
        parentTaskRepository.findAll().forEach((item) -> {
            parentTasks.add(item);
        });
        LOGGER.info("getParentTasks response {}", parentTasks);
        return ResponseEntity.ok(parentTasks);
    }

    @PostMapping
    public ResponseEntity<Void> createParentTask(@RequestBody ParentTask parentTask) {
        LOGGER.info("createParentTask invoked with {}", parentTask);
        Optional<Project> projectOptional = projectRepository.findById(parentTask.getProjectId());
        if (projectOptional.isPresent()) {
            parentTask.setProject(projectOptional.get());
            parentTask.setStatus("Active");
            parentTaskRepository.save(parentTask);
            LOGGER.info("createParentTask successfully created");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        LOGGER.error("createParentTask led to bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateParentTask(@RequestBody ParentTask parentTask) {
        LOGGER.info("updateParentTask invoked with {}", parentTask);
        Optional<ParentTask> parentTask1 = parentTaskRepository.findById(parentTask.getParentId());
        if (parentTask1.isPresent()) {
            parentTaskRepository.save(parentTask);
            try {
                projectController.updateProjectStatus(parentTask.getProject());
            } catch (Exception e) {
                LOGGER.error("updateParentTask led to bad request with error {}", e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            LOGGER.info("updateParentTask successfully updated");
            return ResponseEntity.status(HttpStatus.OK).build();
        } else
            LOGGER.error("updateParentTask led to resource not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
