package com.fsd.taskmanager.load;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.taskmanager.data.entity.User;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class UserRestTest {
    public UserRestTest() {
    }

    public UserRestTest(String s) {
        super();
    }


    private ObjectMapper mapper = new ObjectMapper();

    private String baseUrl = "http://localhost:8082/users";

    @Test
    public void getUsers() {
        get(baseUrl).then().statusCode(200);
    }

    @Test
    public void createUser() {
        User user = User.builder().firstName("perf").lastName("test").employeeId(1).build();
        given().contentType(ContentType.JSON).body(user)
                .post(baseUrl)
                .then()
                .statusCode(201);
    }

    @Test
    public void updateUser() throws IOException {
        List<User> users = mapper.readValue(get(baseUrl).asString(), new TypeReference<List<User>>() {
        });
        User lastUser = users.get(users.size() - 1);
        given().contentType(ContentType.JSON).body(lastUser)
                .put(baseUrl)
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteUser() throws IOException {
        List<User> users = mapper.readValue(get(baseUrl).asString(), new TypeReference<List<User>>() {
        });
        User lastUser = users.get(users.size() - 1);
        given().contentType(ContentType.JSON)
                .delete(baseUrl + "/" + lastUser.getUserId())
                .then()
                .statusCode(200);
    }
}

