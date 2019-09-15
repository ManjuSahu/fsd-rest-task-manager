package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.ParentTask;
import com.fsd.taskmanager.data.Project;
import com.fsd.taskmanager.data.Task;
import com.fsd.taskmanager.data.User;
import com.fsd.taskmanager.repository.ParentTaskRepository;
import com.fsd.taskmanager.repository.ProjectRepository;
import com.fsd.taskmanager.repository.TaskRepository;
import com.fsd.taskmanager.repository.UserRepository;
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

    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach((item) -> {
            tasks.add(item);
            System.out.println("item: " + item);
        });
        System.out.println(tasks);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody Task task) {
        Optional<Project> projectOptional = projectRepository.findById(task.getProjectId());
        if (projectOptional.isPresent()) {
            task.setProject(projectOptional.get());
            task.setStatus("Active");
            if(!StringUtils.isEmpty(task.getParentTaskId())) {
                Optional<ParentTask> parentTaskOptional = parentTaskRepository.findById(task.getParentTaskId());
                if (parentTaskOptional.isPresent()) {
                    task.setParentTask(parentTaskOptional.get());
                }
            }
            if(!StringUtils.isEmpty(task.getTaskOwnerId())) {
                Optional<User> userOptional = userRepository.findById(task.getTaskOwnerId());
                if (userOptional.isPresent()) {
                    task.setTaskOwner(userOptional.get());
                }
            }
            taskRepository.save(task);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTask(@RequestBody Task task) {
        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(@RequestParam Integer taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
