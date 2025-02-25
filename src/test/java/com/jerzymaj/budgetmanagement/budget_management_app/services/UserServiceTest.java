package com.jerzymaj.budgetmanagement.budget_management_app.services;

import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.UserRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MonthlyCostsService monthlyCostsService;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private MonthlyCosts testMonthlyCosts;
    @BeforeEach
    public void setUp(){
        testUser = new User();
        testUser.setId(1L);
        testUser.setNetSalary(10000);

        testMonthlyCosts = new MonthlyCosts();
        testMonthlyCosts.setRent(1500);
        testMonthlyCosts.setFoodCosts(500);
        testMonthlyCosts.setCurrentElectricityBill(600);
        testMonthlyCosts.setCurrentGasBill(300);
        testMonthlyCosts.setTotalCarServiceCosts(3000.0);
        testMonthlyCosts.setCarInsuranceCosts(2000.0);
        testMonthlyCosts.setCarOperatingCosts(null);
    }

    @Test
    public void testCalculateRentCostPercentageOfUserSalaryForMonthlyCosts(){

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        when(monthlyCostsService.getMonthlyCostsForUserByMonth(1L,1)).thenReturn(testMonthlyCosts);

        BigDecimal actualResult = userService.calculateRentCostPercentageOfUserSalaryForMonthlyCosts(1L, 1);

        BigDecimal expectedResult = BigDecimal.valueOf(15).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCalculateFoodCostsCostPercentageOfUserSalaryForMonthlyCosts(){

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        when(monthlyCostsService.getMonthlyCostsForUserByMonth(1L,1)).thenReturn(testMonthlyCosts);

        BigDecimal actualResult = userService.calculateFoodCostsCostPercentageOfUserSalaryForMonthlyCosts(1L, 1);

        BigDecimal expectedResult = BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCalculateCurrentElectricityBillCostPercentageOfUserSalaryForMonthlyCosts(){

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        when(monthlyCostsService.getMonthlyCostsForUserByMonth(1L,1)).thenReturn(testMonthlyCosts);

        BigDecimal actualResult = userService.calculateCurrentElectricityBillCostPercentageOfUserSalaryForMonthlyCosts(1L, 1);

        BigDecimal expectedResult = BigDecimal.valueOf(6).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCalculateCurrentGasBillCostPercentageOfUserSalaryForMonthlyCosts(){

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        when(monthlyCostsService.getMonthlyCostsForUserByMonth(1L,1)).thenReturn(testMonthlyCosts);

        BigDecimal actualResult = userService.calculateCurrentGasBillCostPercentageOfUserSalaryForMonthlyCosts(1L, 1);

        BigDecimal expectedResult = BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCalculateTotalCarServiceCostsPercentageOfUserSalaryForMonthlyCosts(){

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        when(monthlyCostsService.getMonthlyCostsForUserByMonth(1L,1)).thenReturn(testMonthlyCosts);

        BigDecimal actualResult = userService.calculateTotalCarServiceCostsPercentageOfUserSalaryForMonthlyCosts(1L, 1);

        BigDecimal expectedResult = BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCalculateCarOperatingCostsPercentageOfUserSalaryForMonthlyCosts(){

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        when(monthlyCostsService.getMonthlyCostsForUserByMonth(1L,1)).thenReturn(testMonthlyCosts);

        BigDecimal actualResult = userService.calculateCarOperatingCostsPercentageOfUserSalaryForMonthlyCosts(1L, 1);

        BigDecimal expectedResult = BigDecimal.valueOf(0.0).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedResult, actualResult);

    }
}
