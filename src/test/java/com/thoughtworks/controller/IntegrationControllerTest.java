package com.thoughtworks.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import com.thoughtworks.repository.UserRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User testDataOfUser1 = User.builder()
                .name("root")
                .age(20)
                .avatar("https://www.google.com/")
                .description("This is a user for test")
                .build();

        User testDataOfUser2 = User.builder()
                .name("visitor")
                .age(30)
                .avatar("https://www.bing.com/")
                .description("This is another user for test")
                .build();

        userRepository.addUser(testDataOfUser1);
        userRepository.addUser(testDataOfUser2);
    }

    @AfterEach
    public void tearDown() {
        userRepository.clearAll();
    }

    @Test
    public void should_return_the_user_of_the_given_id() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("root")))
                .andExpect(jsonPath("$.age", is(20)))
                .andExpect(jsonPath("$.avatar", is("https://www.google.com/")))
                .andExpect(jsonPath("$.description", is("This is a user for test")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/2"))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("visitor")))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(jsonPath("$.avatar", is("https://www.bing.com/")))
                .andExpect(jsonPath("$.description", is("This is another user for test")))
                .andExpect(status().isOk());
    }

    @Test
    public void new_user_should_be_added_into_repository() throws Exception {
        User user = User.builder().name("newUser").age(35).avatar("https://www.amazon.com/").description("I am new here").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("newUser")))
                .andExpect(jsonPath("$.age", is(35)))
                .andExpect(jsonPath("$.avatar", is("https://www.amazon.com/")))
                .andExpect(jsonPath("$.description", is("I am new here")))
                .andExpect(status().isCreated());

    }


}
