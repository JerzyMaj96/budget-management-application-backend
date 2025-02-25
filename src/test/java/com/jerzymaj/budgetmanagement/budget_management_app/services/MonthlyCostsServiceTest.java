package com.jerzymaj.budgetmanagement.budget_management_app.services;

import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsSummaryRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MonthlyCostsServiceTest {

    @Mock
    private MonthlyCostsRepository monthlyCostsRepository;

    @Mock
    private MonthlyCostsSummaryRepository monthlyCostsSummaryRepository;

    @InjectMocks
    private MonthlyCostsService monthlyCostsService;

    @Test
    public void testAddUpAllMonthlyCostsForUser(){

        MonthlyCosts mockMonthlyCosts = new MonthlyCosts();

        mockMonthlyCosts.setRent(100);
        mockMonthlyCosts.setFoodCosts(100);
        mockMonthlyCosts.setCurrentElectricityBill(100);
        mockMonthlyCosts.setCurrentGasBill(100);
        mockMonthlyCosts.setTotalCarServiceCosts(null);
        mockMonthlyCosts.setCarInsuranceCosts(100.0);
        mockMonthlyCosts.setCarOperatingCosts(100.0);

        MonthlyCostsService spyMonthlyCostsService = Mockito.spy(monthlyCostsService);

        doReturn(mockMonthlyCosts).when(spyMonthlyCostsService).getMonthlyCostsForUserByMonth(1L,1);

       double actualResult = spyMonthlyCostsService.addUpAllMonthlyCostsForUser(1L,1);

       assertEquals(600,actualResult);
    }
}
