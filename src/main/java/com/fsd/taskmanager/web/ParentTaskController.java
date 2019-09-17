package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.ParentTask;
import com.fsd.taskmanager.data.entity.Project;
import com.fsd.taskmanager.repository.ParentTaskRepository;
import com.fsd.taskmanager.repository.ProjectRepository;
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

    @GetMapping
    public ResponseEntity<List<ParentTask>> getParentTasks() {
        List<ParentTask> parentTasks = new ArrayList<>();
        parentTaskRepository.findAll().forEach((item) -> {
            parentTasks.add(item);
            System.out.println("item: " + item);
        });
        System.out.println(parentTasks);
        return ResponseEntity.ok(parentTasks);
    }

    @PostMapping
    public ResponseEntity<Void> createParentTask(@RequestBody ParentTask parentTask) {
        Optional<Project> projectOptional = projectRepository.findById(parentTask.getProjectId());
        if (projectOptional.isPresent()) {
            parentTask.setProject(projectOptional.get());
            parentTask.setStatus("Active");
            parentTaskRepository.save(parentTask);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateParentTask(@RequestBody ParentTask parentTask) {
        Optional<ParentTask> parentTask1 = parentTaskRepository.findById(parentTask.getParentId());
        if (parentTask1.isPresent()) {
            parentTaskRepository.save(parentTask);
            try {
                projectController.updateProjectStatus(parentTask.getProject());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteParentTask(@RequestParam Integer parentTaskId) {
        Optional<ParentTask> parentTask = parentTaskRepository.findById(parentTaskId);
        if (parentTask.isPresent()) {
            parentTaskRepository.delete(parentTask.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
