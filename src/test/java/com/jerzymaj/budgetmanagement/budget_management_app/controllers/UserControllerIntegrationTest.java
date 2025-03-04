package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.UserRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void setUp() {
        userRepository.deleteAll();

        List<User> testUsers = List.of(
                new User(null, "John Smith",5000),
                new User(null, "Karen Johnson", 7000)
        );
        userRepository.saveAll(testUsers);
    }

    @Test
    public void shouldRetrieveAllUsers() throws Exception {
        mockMvc.perform(get("/budget-management/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Smith"))
                .andExpect(jsonPath("$[0].netSalary").value(5000))
                .andExpect(jsonPath("$[1].name").value("Karen Johnson"))
                .andExpect(jsonPath("$[1].netSalary").value(7000));
    }

    @Test
    public void shouldRetrieveUserById() throws Exception {
        User user = new User(null, "Jerry Jackson", 8000);
        User savedUser = userRepository.save(user);

        mockMvc.perform(get("/budget-management/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.name").value("Jerry Jackson"))
                .andExpect(jsonPath("$.netSalary").value(8000));
    }

    @Test
    public void shouldReturnNotFoundForInvalidUserId() throws Exception {
        mockMvc.perform(get("/budget-management/users/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        String newUserJson = """
            {
                "name": "Janet Jackson",
                "netSalary": 6000
            }
        """;

        mockMvc.perform(post("/budget-management/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Janet Jackson"))
                .andExpect(jsonPath("$.netSalary").value(6000));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User user = new User(null, "Chris Roberts", 9000);
        User savedUser = userRepository.save(user);

        mockMvc.perform(delete("/budget-management/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }
}