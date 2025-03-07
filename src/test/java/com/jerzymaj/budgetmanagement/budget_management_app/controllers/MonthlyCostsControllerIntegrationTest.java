package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.UserRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Month;
import java.util.List;

import static org.hamcrest.Matchers.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MonthlyCostsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MonthlyCostsRepository monthlyCostsRepository;

    private Long userId;

    private Month creationMonth1;

    private Month creationMonth2;
    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
        monthlyCostsRepository.deleteAll();

        User user = new User(null,"John Smith",5000);
        user = userRepository.save(user);
        userId = user.getId();

        MonthlyCosts costs1 = new MonthlyCosts(1300, 500, 300,
                400, 100, 1000, 400);

        costs1.setUser(user);

        MonthlyCosts costs2 = new MonthlyCosts(1300, 400, 300,
                        400, 200, 1000, 300);

        costs2.setUser(user);

        monthlyCostsRepository.saveAll(List.of(costs1,costs2));

        creationMonth1 = costs1.getCreateDate().getMonth();
        creationMonth2 = costs2.getCreateDate().getMonth();
    }

    @Test
    public void shouldRetrieveMonthlyCostsByUserId() throws Exception{
        mockMvc.perform(get("/budget-management/users/{userId}/monthly_costs/byUserId", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].rent").value(1300))
                .andExpect(jsonPath("$[0].foodCosts").value(500))
                .andExpect(jsonPath("$[0].currentElectricityBill").value(300))
                .andExpect(jsonPath("$[0].currentGasBill").value(400))
                .andExpect(jsonPath("$[0].totalCarServiceCosts").value(100))
                .andExpect(jsonPath("$[0].carInsuranceCosts").value(1000))
                .andExpect(jsonPath("$[0].carOperatingCosts").value(400))
                .andExpect(jsonPath("$[1].rent").value(1300))
                .andExpect(jsonPath("$[1].foodCosts").value(400))
                .andExpect(jsonPath("$[1].currentElectricityBill").value(300))
                .andExpect(jsonPath("$[1].currentGasBill").value(400))
                .andExpect(jsonPath("$[1].totalCarServiceCosts").value(200))
                .andExpect(jsonPath("$[1].carInsuranceCosts").value(1000))
                .andExpect(jsonPath("$[1].carOperatingCosts").value(300));

    }

    @Test
    public void shouldRetrieveMonthlyCostsByUserIdAndMonthOfMonthlyCosts() throws Exception {
            mockMvc.perform(get("/budget-management/users/{userId}/monthly_costs/byMonth", userId)
                            .param("creationMonth", String.valueOf(creationMonth1.getValue())))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(9))
                    .andExpect(jsonPath("$.rent").value(1300))
                    .andExpect(jsonPath("$.foodCosts").value(500))
                    .andExpect(jsonPath("$.currentElectricityBill").value(300))
                    .andExpect(jsonPath("$.currentGasBill").value(400))
                    .andExpect(jsonPath("$.totalCarServiceCosts").value(100))
                    .andExpect(jsonPath("$.carInsuranceCosts").value(1000))
                    .andExpect(jsonPath("$.carOperatingCosts").value(400));
    }


    @Test
    public void shouldCreateMonthlyCostsForUser() throws Exception{
        String newMonthlyCostsJson = """
               {
                "rent": 1300,
                "foodCosts": 500,
                "currentElectricityBill" : 300,
                "currentGasBill" : 400,
                "totalCarServiceCosts" : 100.0,
                "carInsuranceCosts" : 1000.0,
                "carOperatingCosts" : 400.0
            }
            """;

        mockMvc.perform(post("/budget-management/users/{userId}/monthly_costs", userId)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(newMonthlyCostsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rent").value(1300))
                .andExpect(jsonPath("$.foodCosts").value(500))
                .andExpect(jsonPath("$.currentElectricityBill").value(300))
                .andExpect(jsonPath("$.currentGasBill").value(400))
                .andExpect(jsonPath("$.totalCarServiceCosts").value(100.0))
                .andExpect(jsonPath("$.carInsuranceCosts").value(1000.0))
                .andExpect(jsonPath("$.carOperatingCosts").value(400.0))
                .andExpect(jsonPath("$.createDate").value(matchesRegex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:00")));
    }
}
