package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.services.MonthlyCostsService;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.UserRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MonthlyCostsService monthlyCostsService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository,monthlyCostsService);
    }

    @Test
    public void testCalculateCostsPercentageOfUserSalary_If(){

        Long userId;

        when(u).thenReturn;



    }
}
