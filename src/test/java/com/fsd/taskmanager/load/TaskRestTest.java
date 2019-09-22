package com.fsd.taskmanager.load;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.taskmanager.data.entity.Project;
import com.fsd.taskmanager.data.entity.Task;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class TaskRestTest {

    public TaskRestTest() {
    }


    public TaskRestTest(String s) {
        super();
    }
    private ObjectMapper mapper = new ObjectMapper();

    private String baseUrl = "http://localhost:8082/tasks";

    @Test
    public void getTasks() {
        get(baseUrl).then().statusCode(200);
    }

    @Test
    public void createTask() throws IOException {
        List<Project> projects = mapper.readValue((get("http://localhost:8082/projects").asString()), new TypeReference<List<Project>>() {
        });
        Project lastProject = projects.get(projects.size() - 1);
        Task project = Task.builder().task("Perf test task").projectId(lastProject.getProjectId()).build();
        given().contentType(ContentType.JSON).body(project)
                .post(baseUrl)
                .then()
                .statusCode(201);
    }

    @Test
    public void updateTask() throws IOException {
        List<Task> projects = mapper.readValue(get(baseUrl).asString(), new TypeReference<List<Task>>() {
        });
        Task lastTask = projects.get(projects.size() - 1);
        given().contentType(ContentType.JSON).body(lastTask)
                .put(baseUrl)
                .then()
                .statusCode(200);
    }
}
