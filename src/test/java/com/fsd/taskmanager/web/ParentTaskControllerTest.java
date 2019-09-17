package com.fsd.taskmanager.web;

import com.fsd.taskmanager.data.entity.ParentTask;
import com.fsd.taskmanager.data.entity.Project;
import com.fsd.taskmanager.repository.ParentTaskRepository;
import com.fsd.taskmanager.repository.ProjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParentTaskControllerTest {

    @InjectMocks
    private ParentTaskController controller = new ParentTaskController();

    @Mock
    private ParentTaskRepository parentTaskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectController projectController;

    private ParentTask parentTask;

    private List<ParentTask> parentTasks = new ArrayList<>();

    private Project project = new Project();

    public ParentTaskControllerTest() {
        parentTask = ParentTask.builder().parentId(1).projectId(1).project(project).build();
        parentTasks.add(parentTask);
    }

    @Before
    public void init() {
        when(parentTaskRepository.findAll()).thenReturn(parentTasks);
        when(parentTaskRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(parentTask));
        when(parentTaskRepository.findById(2)).thenReturn(java.util.Optional.empty());
        when(projectRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(project));
    }

    @Test
    public void getParentTasks() throws Exception {
        assertEquals(1, controller.getParentTasks().getBody().size());
    }

    @Test
    public void createParentTask() throws Exception {
        assertEquals(HttpStatus.CREATED, controller.createParentTask(parentTask).getStatusCode());
    }

    @Test
    public void createTaskWithInvalidProject() throws Exception {
        parentTask.setProjectId(2);
        assertEquals(HttpStatus.BAD_REQUEST, controller.createParentTask(parentTask).getStatusCode());
    }

    @Test
    public void updateParentTask() throws Exception {
        assertEquals(HttpStatus.OK, controller.updateParentTask(parentTask).getStatusCode());
    }

    @Test
    public void updateInvalidParentTask() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND, controller.updateParentTask(ParentTask.builder().parentId(2).build()).getStatusCode());
    }

    @Test
    public void updateParentTaskWithInvalidProject() throws Exception {
        doThrow(new Exception()).when(projectController).updateProjectStatus(project);
        assertEquals(HttpStatus.BAD_REQUEST, controller.updateParentTask(parentTask).getStatusCode());
    }

    @Test
    public void deleteParentTask() throws Exception {
        assertEquals(HttpStatus.OK, controller.deleteParentTask(parentTask.getParentId()).getStatusCode());
    }

    @Test
    public void deleteInvalidParentTask() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND, controller.deleteParentTask(2).getStatusCode());
    }

}