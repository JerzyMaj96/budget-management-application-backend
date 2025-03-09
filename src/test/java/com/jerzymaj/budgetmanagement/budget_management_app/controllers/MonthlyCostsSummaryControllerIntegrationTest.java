package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsSummaryRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.UserRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Month;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MonthlyCostsSummaryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MonthlyCostsRepository monthlyCostsRepository;

    @Autowired
    private MonthlyCostsSummaryRepository monthlyCostsSummaryRepository;

    private Long userId;

    private Month month;

    @BeforeEach
    public void setUp(){
        monthlyCostsSummaryRepository.deleteAll();
        monthlyCostsRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User(null,"John Smith",5000);
        user = userRepository.save(user);
        userId = user.getId();

        MonthlyCosts costs = new MonthlyCosts(1300, 500, 300,
                400, 100, 1000, 400);
        costs.setUser(user);
        monthlyCostsRepository.save(costs);

        MonthlyCostsSummary summary = new MonthlyCostsSummary(1, 3500.0, BigDecimal.valueOf(26),
                BigDecimal.valueOf(10), BigDecimal.valueOf(6), BigDecimal.valueOf(8), BigDecimal.valueOf(2),
                BigDecimal.valueOf(20), BigDecimal.valueOf(8), BigDecimal.valueOf(70), BigDecimal.valueOf(1500));
        summary.setMonthlyCosts(costs);
        summary.setVersion(0);
        monthlyCostsSummaryRepository.save(summary);

        month = summary.getCreateDate().getMonth();
    }

    @Test
    public void shouldRetrieveMonthlyCostsSummaryByUserIdAndMonth() throws Exception {
        mockMvc.perform(get("/budget-management/users/{userId}/monthly_costs/monthly_costs_summary", userId)
                        .param("month", String.valueOf(month.getValue()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(13))
                .andExpect(jsonPath("$.monthlyCostsSum").value(3500.0))
                .andExpect(jsonPath("$.rentPercentageOfUserSalary").value(26))
                .andExpect(jsonPath("$.foodCostsPercentageOfUserSalary").value(10))
                .andExpect(jsonPath("$.currentElectricityBillPercentageOfUserSalary").value(6))
                .andExpect(jsonPath("$.currentGasBillPercentageOfUserSalary").value(8))
                .andExpect(jsonPath("$.totalCarServicePercentageOfUserSalary").value(2))
                .andExpect(jsonPath("$.carInsuranceCostsPercentageOfUserSalary").value(20))
                .andExpect(jsonPath("$.carOperatingCostsPercentageOfUserSalary").value(8))
                .andExpect(jsonPath("$.costsPercentageOfUserSalary").value(70))
                .andExpect(jsonPath("$.netSalaryAfterCosts").value(1500));
    }
}
