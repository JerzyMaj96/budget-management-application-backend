package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.MonthlyCostsSummaryDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsSummaryNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.NetSalaryAfterCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import com.jerzymaj.budgetmanagement.budget_management_app.services.GPTService;
import com.jerzymaj.budgetmanagement.budget_management_app.services.MonthlyCostsService;
import com.jerzymaj.budgetmanagement.budget_management_app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/budget-management/users/{userId}/monthly_costs")
public class MonthlyCostsSummaryController {

    private final UserService userService;
    private final MonthlyCostsService monthlyCostsService;

    public MonthlyCostsSummaryController(UserService userService, MonthlyCostsService monthlyCostsService) {
        this.userService = userService;
        this.monthlyCostsService = monthlyCostsService;
    }

    @PostMapping("/sum")
    public ResponseEntity<Void> sumUpAllTheMonthlyCostsForUserByMonth(@PathVariable Long userId, @RequestParam int month) {

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId, month);
        double monthlyCostsSum = monthlyCostsService.addUpAllMonthlyCostsForUser(userId, month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);
        monthlyCostsSummary.setMonthlyCostsSum(monthlyCostsSum);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/rent-percentage")
    public ResponseEntity<BigDecimal> createRentPercentageOfUserSalary(@PathVariable Long userId, @RequestParam int month) {
        BigDecimal rentPercentage = userService.calculateRentCostPercentageOfUserSalaryForMonthlyCosts(userId, month);
        monthlyCostsService.updateMonthlyCostsSummary(userId, month, rentPercentage, "rent");
        return ResponseEntity.ok(rentPercentage);
    }

    @PostMapping("/food_costs-percentage")
    public ResponseEntity<BigDecimal> createFoodCostsPercentageOfUserSalary(@PathVariable Long userId, @RequestParam int month) {
        BigDecimal foodCostsPercentage = userService.calculateFoodCostsCostPercentageOfUserSalaryForMonthlyCosts(userId, month);
        monthlyCostsService.updateMonthlyCostsSummary(userId, month, foodCostsPercentage, "food");
        return ResponseEntity.ok(foodCostsPercentage);
    }

    @PostMapping("/current_electricity_bill-percentage")
    public ResponseEntity<BigDecimal> createCurrentElectricityBillPercentageOfUserSalary(@PathVariable Long userId, @RequestParam int month) {
        BigDecimal electricityPercentage = userService.calculateCurrentElectricityBillCostPercentageOfUserSalaryForMonthlyCosts(userId, month);
        monthlyCostsService.updateMonthlyCostsSummary(userId, month, electricityPercentage, "electricity");
        return ResponseEntity.ok(electricityPercentage);
    }

    @PostMapping("/current_gas_bill-percentage")
    public ResponseEntity<BigDecimal> createCurrentGasBillPercentageOfUserSalary(@PathVariable Long userId, @RequestParam int month) {
        BigDecimal gasPercentage = userService.calculateCurrentGasBillCostPercentageOfUserSalaryForMonthlyCosts(userId, month);
        monthlyCostsService.updateMonthlyCostsSummary(userId, month, gasPercentage, "gas");
        return ResponseEntity.ok(gasPercentage);
    }

    @PostMapping("/total_car_service-percentage")
    public ResponseEntity<BigDecimal> createTotalCarServicePercentageOfUserSalary(@PathVariable Long userId, @RequestParam int month) {
        BigDecimal carServicePercentage = userService.calculateTotalCarServiceCostsPercentageOfUserSalaryForMonthlyCosts(userId, month);
        monthlyCostsService.updateMonthlyCostsSummary(userId, month, carServicePercentage, "car_service");
        return ResponseEntity.ok(carServicePercentage);
    }

    @PostMapping("/car_insurance_costs-percentage")
    public ResponseEntity<BigDecimal> createCarInsuranceCostsPercentageOfUserSalary(@PathVariable Long userId, @RequestParam int month) {
        BigDecimal carInsurancePercentage = userService.calculateCarInsuranceCostsPercentageOfUserSalaryForMonthlyCosts(userId, month);
        monthlyCostsService.updateMonthlyCostsSummary(userId, month, carInsurancePercentage, "car_insurance");
        return ResponseEntity.ok(carInsurancePercentage);
    }

    @PostMapping("/car_operating_costs-percentage")
    public ResponseEntity<BigDecimal> createCarOperatingCostsPercentageOfUserSalary(@PathVariable Long userId, @RequestParam int month) {
        BigDecimal carOperatingPercentage = userService.calculateCarOperatingCostsPercentageOfUserSalaryForMonthlyCosts(userId, month);
        monthlyCostsService.updateMonthlyCostsSummary(userId, month, carOperatingPercentage, "car_operating");
        return ResponseEntity.ok(carOperatingPercentage);
    }

    @PostMapping("/costs-percentage")
    public ResponseEntity<BigDecimal> createAllCostsPercentageOfUserSalary(@PathVariable Long userId, @RequestParam int month) {
        BigDecimal costsPercentage = userService.calculateAllCostsPercentageOfUserSalaryForMonthlyCosts(userId, month);
        monthlyCostsService.updateMonthlyCostsSummary(userId, month, costsPercentage, "total_costs");
        return ResponseEntity.ok(costsPercentage);
    }

    @GetMapping("/monthly_costs_summary")
    public ResponseEntity<MonthlyCostsSummaryDTO> retrieveMonthlyCostsSummaryByUserIdAndMonth(@PathVariable Long userId, @RequestParam int month) {

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId, month);
        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService
                .getMonthlyCostsSummaryByMonthlyCostsId(monthlyCosts.getId())
                .orElseThrow(() -> new MonthlyCostsSummaryNotFoundException("No summary found for user " + userId));

        return ResponseEntity.ok(monthlyCostsService.convertMonthlyCostsSummaryToDTO(monthlyCostsSummary));
    }

    @PostMapping("/net_salary_after_costs")
    public ResponseEntity<BigDecimal> createNetSalaryAfterCostsForUserByMonth(@PathVariable Long userId, @RequestParam int month) {
        BigDecimal netSalaryAfterCosts = userService.calculateNetSalaryAfterCostsForUser(userId, month);
        monthlyCostsService.updateMonthlyCostsSummary(userId, month, netSalaryAfterCosts, "net_salary");
        return ResponseEntity.ok(netSalaryAfterCosts);
    }

    @PostMapping("/advice")
    public ResponseEntity<String> getOrCreateFinancialAdvice(@PathVariable Long userId, @RequestParam int month) {

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getMonthlyCostsSummaryForUserByMonth(userId, month)
                .orElseThrow(() -> new MonthlyCostsSummaryNotFoundException("No summary found for user " + userId));

        BigDecimal netSalaryAfterCostsNow = userService.calculateNetSalaryAfterCostsForUser(userId, month);
        BigDecimal netSalaryAfterCostsPrevious = monthlyCostsSummary.getNetSalaryAfterCosts();

        if (netSalaryAfterCostsPrevious == null && netSalaryAfterCostsNow == null) {
            throw new NetSalaryAfterCostsNotFoundException("No net salary after costs found for user: " + userId);
        }

        if (monthlyCostsSummary.getFinancialAdvice() != null && netSalaryAfterCostsNow.compareTo(netSalaryAfterCostsPrevious) == 0) {
            return ResponseEntity.ok(monthlyCostsSummary.getFinancialAdvice());
        }

        String prompt = userService.generatePromptBasedOnSalary(netSalaryAfterCostsNow);
        String financialAdvice = GPTService.getAdviceFromGPT(prompt);

        monthlyCostsSummary.setFinancialAdvice(financialAdvice);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(financialAdvice);
    }
}
