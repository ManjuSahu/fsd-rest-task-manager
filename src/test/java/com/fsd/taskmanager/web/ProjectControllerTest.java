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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ParentTaskRepository parentTaskRepository;

    @InjectMocks
    private ProjectController projectController = new ProjectController();

    private Project project;

    private List<Project> projects = new ArrayList<>();

    private User user;

    public ProjectControllerTest() {
        Task task = Task.builder().task("Task 1").build();
        ParentTask parentTask = ParentTask.builder().parentTask("Parent Task 1").build();
        user = User.builder().userId(1).employeeId(111).firstName("Manju").lastName("Sahu").build();
        project = new Project();
        project.setStatus("Active");
        project.setManager(user);
        project.setProject("User Management");
        project.setPriority(1);
        project.setDbTasks(Collections.singletonList(task));
        project.setDbParentTasks(Collections.singletonList(parentTask));
        project.setProjectId(1);
        Project project1 = Project.builder().projectId(2).project("Leave Management").manager(user).priority(1)
                .status("Suspended")
                .projectId(2)
                .build();
        projects.add(project);
        projects.add(project1);
    }

    @Before
    public void init() {
        when(projectRepository.findAll()).thenReturn(projects);
        when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        when(projectRepository.findById(1)).thenReturn(Optional.ofNullable(project));
    }

    @Test
    public void getProjects() throws Exception {
        assertEquals(1, projectController.getProjects().getBody().size());
    }

    @Test
    public void createProject() throws Exception {
        Project project = Project.builder().project("New Project").build();
        project.setManagerId(1);
        assertEquals(HttpStatus.CREATED, projectController.createProject(project).getStatusCode());
    }

    @Test
    public void createInvalidProject() throws Exception {
        Project project = Project.builder().project("New Project").build();
        project.setManagerId(2);
        assertEquals(HttpStatus.BAD_REQUEST, projectController.createProject(project).getStatusCode());
    }

    @Test
    public void updateProject() throws Exception {
        assertEquals(HttpStatus.OK, projectController.updateProject(project).getStatusCode());

    }

    @Test
    public void updateInvalidProject() throws Exception {
        Project project = Project.builder().projectId(3).build();
        assertEquals(HttpStatus.NOT_FOUND, projectController.updateProject(project).getStatusCode());
    }

    @Test
    public void suspendProject() throws Exception {
        assertEquals(HttpStatus.OK, projectController.suspendProject(1).getStatusCode());
    }

    @Test
    public void suspendInvalidProject() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND, projectController.suspendProject(3).getStatusCode());
    }

    @Test
    public void updateProjectStatus() throws Exception {
        projectController.updateProjectStatus(project);
    }

    @Test(expected = Exception.class)
    public void updateInvalidProjectStatus() throws Exception {
        projectController.updateProjectStatus(Project.builder().build());
    }

}