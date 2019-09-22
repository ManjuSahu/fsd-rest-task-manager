package com.fsd.taskmanager.load;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.taskmanager.data.entity.ParentTask;
import com.fsd.taskmanager.data.entity.Project;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class ParentTaskRestTest {
    public ParentTaskRestTest() {
    }


    public ParentTaskRestTest(String s) {
        super();
    }
    private ObjectMapper mapper = new ObjectMapper();

    private String baseUrl = "http://localhost:8082/parentTasks";

    @Test
    public void getParentTasks() {
        get(baseUrl).then().statusCode(200);
    }

    @Test
    public void createParentTask() throws IOException {
        List<Project> projects = mapper.readValue((get("http://localhost:8082/projects").asString()), new TypeReference<List<Project>>() {
        });
        Project lastProject = projects.get(projects.size() - 1);
        ParentTask project = ParentTask.builder().parentTask("Perf test task").projectId(lastProject.getProjectId()).build();
        given().contentType(ContentType.JSON).body(project)
                .post(baseUrl)
                .then()
                .statusCode(201);
    }

    @Test
    public void updateParentTask() throws IOException {
        List<ParentTask> projects = mapper.readValue(get(baseUrl).asString(), new TypeReference<List<ParentTask>>() {
        });
        ParentTask lastParentTask = projects.get(projects.size() - 1);
        given().contentType(ContentType.JSON).body(lastParentTask)
                .put(baseUrl)
                .then()
                .statusCode(200);
    }
}

