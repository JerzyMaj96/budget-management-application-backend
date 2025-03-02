package com.jerzymaj.budgetmanagement.budget_management_app.services;

import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.MonthlyCostsDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.MonthlyCostsSummaryDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsSummaryRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

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

    private MonthlyCosts mockMonthlyCosts;

    private MonthlyCostsSummary mockMonthlyCostsSummary;

    @BeforeEach
    public void setUp(){
        mockMonthlyCosts = new MonthlyCosts();
        mockMonthlyCosts.setId(1L);
        mockMonthlyCosts.setRent(100.0);
        mockMonthlyCosts.setFoodCosts(100.0);
        mockMonthlyCosts.setCurrentElectricityBill(100.0);
        mockMonthlyCosts.setCurrentGasBill(100.0);
        mockMonthlyCosts.setTotalCarServiceCosts(null);
        mockMonthlyCosts.setCarInsuranceCosts(100.0);
        mockMonthlyCosts.setCarOperatingCosts(100.0);
        mockMonthlyCosts.setCreateDate(LocalDateTime.now());

        mockMonthlyCostsSummary = new MonthlyCostsSummary();
        mockMonthlyCostsSummary.setMonthlyCostsSum(600.0);
        mo
    }
    @Test
    public void testAddUpAllMonthlyCostsForUser(){

        MonthlyCostsService spyMonthlyCostsService = Mockito.spy(monthlyCostsService);

        doReturn(mockMonthlyCosts).when(spyMonthlyCostsService).getMonthlyCostsForUserByMonth(1L,1);

       double actualResult = spyMonthlyCostsService.addUpAllMonthlyCostsForUser(1L,1);

       assertEquals(600,actualResult);
    }

    @Test
    public void testConvertMonthlyCostsToDTO(){

        MonthlyCostsDTO expectedMonthlyCostsDTO = new MonthlyCostsDTO(mockMonthlyCosts.getId(),mockMonthlyCosts.getRent(),
                mockMonthlyCosts.getFoodCosts(),mockMonthlyCosts.getCurrentElectricityBill(),
                mockMonthlyCosts.getCurrentGasBill(),
                mockMonthlyCosts.getTotalCarServiceCosts() != null ? mockMonthlyCosts.getTotalCarServiceCosts() : 0.0,
                mockMonthlyCosts.getCarInsuranceCosts(),
                mockMonthlyCosts.getCreateDate());

        MonthlyCostsService spyMonthlyCostsService = Mockito.spy(monthlyCostsService);

        doReturn(expectedMonthlyCostsDTO).when(spyMonthlyCostsService).convertMonthlyCostsToDTO(mockMonthlyCosts);

        MonthlyCostsDTO actualResultDTO = spyMonthlyCostsService.convertMonthlyCostsToDTO(mockMonthlyCosts);

        assertEquals(expectedMonthlyCostsDTO,actualResultDTO);
    }

    @Test
    public void testConvertMonthlyCostsSummaryToDTO(){

        MonthlyCostsSummaryDTO expectedMonthlyCostsSummaryDTO = new MonthlyCostsSummaryDTO(
                mo
        )
    }
}
