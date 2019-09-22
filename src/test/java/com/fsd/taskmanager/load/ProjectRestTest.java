package com.fsd.taskmanager.load;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.taskmanager.data.entity.Project;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class ProjectRestTest {
    public ProjectRestTest() {
    }


    public ProjectRestTest(String s) {
        super();
    }
    private ObjectMapper mapper = new ObjectMapper();

    private String baseUrl = "http://localhost:8082/projects";

    @Test
    public void getProjects() {
        get(baseUrl).then().statusCode(200);
    }

    @Test
    public void createProject() {
        Project project = Project.builder().project("Perf test projects").build();
        given().contentType(ContentType.JSON).body(project)
                .post(baseUrl)
                .then()
                .statusCode(201);
    }

    @Test
    public void updateProject() throws IOException {
        List<Project> projects = mapper.readValue(get(baseUrl).asString(), new TypeReference<List<Project>>() {
        });
        Project lastProject = projects.get(projects.size() - 1);
        given().contentType(ContentType.JSON).body(lastProject)
                .put(baseUrl)
                .then()
                .statusCode(200);
    }
}
