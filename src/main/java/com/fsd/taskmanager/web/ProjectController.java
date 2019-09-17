package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.Project;
import com.fsd.taskmanager.data.entity.User;
import com.fsd.taskmanager.repository.ParentTaskRepository;
import com.fsd.taskmanager.repository.ProjectRepository;
import com.fsd.taskmanager.repository.TaskRepository;
import com.fsd.taskmanager.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ParentTaskRepository parentTaskRepository;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach((item) -> {
            if (!("Suspended".equals(item.getStatus()))) {
                projects.add(item);
                item.getDbTasks().forEach(task -> {
                    com.fsd.taskmanager.data.dto.Task copyTask = new com.fsd.taskmanager.data.dto.Task();
                    BeanUtils.copyProperties(task, copyTask);
                    item.getTasks().add(copyTask);
                });
                item.getDbParentTasks().forEach(parentTask -> {
                    com.fsd.taskmanager.data.dto.ParentTask copyParentTask = new com.fsd.taskmanager.data.dto.ParentTask();
                    BeanUtils.copyProperties(parentTask, copyParentTask);
                    item.getParentTasks().add(copyParentTask);
                });
            }
        });
        System.out.println(projects);
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody Project project) {
        if (!StringUtils.isEmpty(project.getManagerId())) {
            Optional<User> managerOptional = userRepository.findById(project.getManagerId());
            if (managerOptional.isPresent()) {
                project.setManager(managerOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        project.setStatus("Active");
        projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateProject(@RequestBody Project project) {
        Optional<Project> projectOptional = projectRepository.findById(project.getProjectId());
        if (projectOptional.isPresent()) {
            projectRepository.save(project);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> suspendProject(@RequestParam Integer projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.setStatus("Suspended");
            projectRepository.save(project);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public void updateProjectStatus(Project project) throws Exception {
        Optional<Project> projectOptional = projectRepository.findById(project.getProjectId());
        if(projectOptional.isPresent()) {
            project = projectOptional.get();
            if(project.getTasks().stream().filter(projectTask -> !"Completed".equals(projectTask.getStatus())).count() +
                    project.getParentTasks().stream().filter(projectTask -> !"Completed".equals(projectTask.getStatus())).count() == 0) {
                project.setStatus("Completed");
                projectRepository.save(project);
            }
        } else
            throw new Exception("Inavlid project passed");
    }
}
