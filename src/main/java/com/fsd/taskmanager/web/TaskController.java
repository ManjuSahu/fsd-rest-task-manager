package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.ParentTask;
import com.fsd.taskmanager.data.entity.Project;
import com.fsd.taskmanager.data.entity.Task;
import com.fsd.taskmanager.data.entity.User;
import com.fsd.taskmanager.repository.ParentTaskRepository;
import com.fsd.taskmanager.repository.ProjectRepository;
import com.fsd.taskmanager.repository.TaskRepository;
import com.fsd.taskmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ParentTaskRepository parentTaskRepository;

    @Autowired
    private ProjectController projectController;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        LOGGER.info("getTasks invoked");
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach((item) -> {
            tasks.add(item);
        });
        LOGGER.info("getTasks response {}", tasks);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody Task task) {
        LOGGER.info("createTask invoked with {}", task);
        Optional<Project> projectOptional = projectRepository.findById(task.getProjectId());
        if (projectOptional.isPresent()) {
            task.setProject(projectOptional.get());
            task.setStatus("Active");
            if (!StringUtils.isEmpty(task.getParentTaskId())) {
                Optional<ParentTask> parentTaskOptional = parentTaskRepository.findById(task.getParentTaskId());
                if (parentTaskOptional.isPresent()) {
                    task.setParentTask(parentTaskOptional.get());
                } else {
                    LOGGER.error("createTask led to bad request due to parent task not found");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            if (!StringUtils.isEmpty(task.getTaskOwnerId())) {
                Optional<User> userOptional = userRepository.findById(task.getTaskOwnerId());
                if (userOptional.isPresent()) {
                    task.setTaskOwner(userOptional.get());
                } else {
                    LOGGER.error("createTask led to bad request due to task owner not found");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            taskRepository.save(task);
            LOGGER.info("createTask successfully created");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        LOGGER.error("createTask led to bad request due to project not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTask(@RequestBody Task task) {
        LOGGER.info("updateTask invoked with {}", task);
        Optional<Task> task1 = taskRepository.findById(task.getTaskId());
        if (task1.isPresent()) {
            taskRepository.save(task);
            try {
                projectController.updateProjectStatus(task.getProject());
            } catch (Exception e) {
                LOGGER.error("updateTask led to bad request with error {}", e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            LOGGER.info("updateTask successfully updated");
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            LOGGER.error("updateTask led to resource not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
