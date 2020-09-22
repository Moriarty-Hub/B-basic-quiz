package com.thoughtworks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.entity.Education;
import com.thoughtworks.entity.User;
import com.thoughtworks.service.EducationService;
import com.thoughtworks.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IntegrationController.class)
@AutoConfigureJsonTesters
public class UnitControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private EducationService educationService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<User> jsonUser;
    @Autowired
    private JacksonTester<Education> jsonEducation;

    @Nested
    class GetUsers {

        private User user;

        @BeforeEach
        public void setUp() {
            user = User.builder()
                    .id(1L)
                    .name("root")
                    .age(28)
                    .avatar("www.google.com")
                    .description("I am root user").build();
        }

        @Test
        public void should_return_the_user_with_id_1() throws Exception {
            when(userService.getUserById(1L)).thenReturn(user);

            mockMvc.perform(get("/users/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("root")))
                    .andExpect(jsonPath("$.age", is(28)))
                    .andExpect(jsonPath("$.avatar", is("www.google.com")))
                    .andExpect(jsonPath("$.description", is("I am root user")));

            verify(userService, times(1)).getUserById(1L);
        }
    }

    @Nested
    class CreateUser {

        private User user;

        @BeforeEach
        public void setUp() {
            user = User.builder()
                    .name("root")
                    .age(28)
                    .avatar("www.google.com")
                    .description("I am root user").build();
        }

        @Test
        public void should_add_new_user() throws Exception {
            when(userService.addUser(user)).thenReturn(user);

            MockHttpServletRequestBuilder requestBuilder = post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonUser.write(user).getJson());

            MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                    .andReturn().getResponse();

            assertEquals(HttpStatus.CREATED.value(), response.getStatus());
            assertEquals(new ObjectMapper().writeValueAsString(user), response.getContentAsString());
            verify(userService).addUser(user);
        }


    }

    @Nested
    class GetEducations {

        private final List<Education> educationList = new LinkedList<>();

        @BeforeEach
        public void setUp() {
            Education education1 = Education.builder()
                    .id(1)
                    .userId(1L)
                    .year(2001L)
                    .title("primary school")
                    .description("A top student").build();
            Education education2 = Education.builder()
                    .id(2)
                    .userId(1L)
                    .year(2010L)
                    .title("Software Engineer")
                    .description("Working at ThoughtWorks").build();
            educationList.add(education1);
            educationList.add(education2);
        }

        @AfterEach
        public void tearDown() {
            educationList.clear();
        }

        @Test
        public void should_return_education_list_of_user_1() throws Exception {
            when(educationService.getEducationListById(1L)).thenReturn(educationList);

            mockMvc.perform(get("/users/1/educations"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].userId", is(1)))
                    .andExpect(jsonPath("$[0].year", is(2001)))
                    .andExpect(jsonPath("$[0].title", is("primary school")))
                    .andExpect(jsonPath("$[0].description", is("A top student")))
                    .andExpect(jsonPath("$[1].id", is(2)))
                    .andExpect(jsonPath("$[1].userId", is(1)))
                    .andExpect(jsonPath("$[1].year", is(2010)))
                    .andExpect(jsonPath("$[1].title", is("Software Engineer")))
                    .andExpect(jsonPath("$[1].description", is("Working at ThoughtWorks")));
        }
    }

    @Nested
    class CreateEducation {

        private Education education;
        private final List<Education> educationList = new LinkedList<>();

        @BeforeEach
        public void setUp() {
            education = Education.builder()
                    .id(1)
                    .userId(1L)
                    .year(2001L)
                    .title("primary school")
                    .description("A top student").build();
            educationList.add(education);
        }

        @AfterEach
        public void tearDown() {
            educationList.clear();
        }

        @Test
        public void should_add_new_education_record() throws Exception {
            when(educationService.addEducation(1L, education)).thenReturn(educationList);

            MockHttpServletRequestBuilder requestBuilder = post("/users/1/educations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonEducation.write(education).getJson());

            MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                    .andReturn()
                    .getResponse();

            assertEquals(HttpStatus.CREATED.value(), response.getStatus());
            assertEquals(new ObjectMapper().writeValueAsString(educationList), response.getContentAsString());
        }
    }
}
