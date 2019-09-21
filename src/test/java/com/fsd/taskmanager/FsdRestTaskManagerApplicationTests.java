package com.fsd.taskmanager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsd.taskmanager.data.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FsdRestTaskManagerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreateRetrieveWithMockMVC() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users");
        MockHttpServletResponse apiResponse = this.mockMvc.perform(requestBuilder).andReturn().getResponse();
        mapper.readValue(apiResponse.getContentAsString(), new TypeReference<List<User>>() {
        });
    }
}
