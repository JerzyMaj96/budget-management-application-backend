package com.jerzymaj.budgetmanagement.budget_management_app.models;


import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsSummaryRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.services.MonthlyCostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class MonthlyCostsServiceTest {

    @Mock
    private MonthlyCostsRepository monthlyCostsRepository;

    private MonthlyCostsSummaryRepository monthlyCostsResultsRepository;

    private MonthlyCostsService monthlyCostsService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        monthlyCostsService = new MonthlyCostsService(monthlyCostsRepository,monthlyCostsResultsRepository);
    }

    @Test
    public void testAddUpAllMonthlyCostsForUser_IfCostsFound(){

        Long userId = 1L;
        MonthlyCosts mockedCosts = new MonthlyCosts(1500,500,400,300);

        when(monthlyCostsRepository.findByUserId(userId)).thenReturn(Optional.of(mockedCosts));

        double actualCostsSum = monthlyCostsService.addUpAllMonthlyCostsForUser(userId);

        double expectedCostsSum = 1500 + 500 + 400 + 300;

        assertEquals(expectedCostsSum,actualCostsSum);
    }

    @Test
    public void testAddUpAllMonthlyCostsForUser_IfNotCostsFound(){

        Long userId = 1L;

        when(monthlyCostsRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(MonthlyCostsNotFoundException.class, () -> monthlyCostsService.addUpAllMonthlyCostsForUser(userId));

    }

}
