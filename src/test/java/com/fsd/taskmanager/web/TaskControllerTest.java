package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.ParentTask;
import com.fsd.taskmanager.data.entity.Project;
import com.fsd.taskmanager.data.entity.Task;
import com.fsd.taskmanager.data.entity.User;
import com.fsd.taskmanager.repository.ParentTaskRepository;
import com.fsd.taskmanager.repository.ProjectRepository;
import com.fsd.taskmanager.repository.TaskRepository;
import com.fsd.taskmanager.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController = new TaskController();

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ParentTaskRepository parentTaskRepository;

    @Mock
    private ProjectController projectController;

    private Task task;

    private Task task1;

    private List<Task> tasks = new ArrayList<>();

    private Project project;

    private Project invalidProject = new Project();

    private User user;

    private ParentTask parentTask;

    public TaskControllerTest() {
        project = new Project();
        project.setStatus("Active");
        project.setProject("User Management");
        project.setPriority(1);
        project.setDbTasks(Collections.singletonList(task));
        project.setProjectId(1);
        task1 = Task.builder().task("Task 2").priority(2).status("Completed").build();
        tasks.add(task);
        tasks.add(task1);
        user = User.builder().userId(1).employeeId(111).firstName("Manju").lastName("Sahu").build();
        parentTask = ParentTask.builder().parentTask("Parent Task 1").parentId(1).build();
    }

    @Before
    public void init() throws Exception {
        task = Task.builder().taskId(1).task("Task 1").priority(1).status("Active").projectId(1).build();
        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskRepository.findById(1)).thenReturn(Optional.ofNullable(task));
        when(taskRepository.findById(3)).thenReturn(Optional.empty());
        when(projectRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(project));
        when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findById(2)).thenReturn(java.util.Optional.empty());
        when(parentTaskRepository.findById(1)).thenReturn(Optional.of(parentTask));
        when(parentTaskRepository.findById(2)).thenReturn(Optional.empty());
        doThrow(new Exception()).when(projectController).updateProjectStatus(invalidProject);
    }

    @Test
    public void getTasks() throws Exception {
        assertEquals(2, taskController.getTasks().getBody().size());
    }

    @Test
    public void createTask() throws Exception {
        assertEquals(HttpStatus.CREATED, taskController.createTask(task).getStatusCode());
    }

    @Test
    public void createTaskWithParentTaskAndOwner() throws Exception {
        task.setTaskOwnerId(1);
        task.setParentTaskId(1);
        assertEquals(HttpStatus.CREATED, taskController.createTask(task).getStatusCode());
    }

    @Test
    public void createTaskWithInvalidProject() throws Exception {
        task.setProjectId(3);
        task.setParentTaskId(1);
        assertEquals(HttpStatus.BAD_REQUEST, taskController.createTask(task).getStatusCode());
    }

    @Test
    public void createTaskWithInvalidParentTask() throws Exception {
        task.setParentTaskId(2);
        assertEquals(HttpStatus.BAD_REQUEST, taskController.createTask(task).getStatusCode());
    }

    @Test
    public void createTaskWithInvalidTaskOwner() throws Exception {
        task.setTaskOwnerId(2);
        assertEquals(HttpStatus.BAD_REQUEST, taskController.createTask(task).getStatusCode());
    }

    @Test
    public void updateTask() throws Exception {
        task.setProject(project);
        assertEquals(HttpStatus.OK, taskController.updateTask(task).getStatusCode());
    }

    @Test
    public void updateInvalidTask() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND, taskController.updateTask(Task.builder().taskId(3).build()).getStatusCode());
    }

    @Test
    public void updateTaskWithInvalidProject() throws Exception {
        task.setProject(invalidProject);
        assertEquals(HttpStatus.BAD_REQUEST, taskController.updateTask(task).getStatusCode());
    }

}