package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

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
import java.time.Month;

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
    public ResponseEntity<BigDecimal> createRentPercentageOfUserSalary(@PathVariable Long userId,
                                                                       @RequestParam int month){
       BigDecimal rentPercentageOfUserSalaryBD = userService.calculateRentCostPercentageOfUserSalaryForMonthlyCosts(userId,month);

       MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

       MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setRentPercentageOfUserSalary(rentPercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(rentPercentageOfUserSalaryBD);
    }

    @PostMapping("/food_costs-percentage")
    public ResponseEntity<BigDecimal> createFoodCostsPercentageOfUserSalary(@PathVariable Long userId,
                                                                       @RequestParam int month){
        BigDecimal foodCostsPercentageOfUserSalaryBD = userService
                .calculateFoodCostsCostPercentageOfUserSalaryForMonthlyCosts(userId,month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setFoodCostsPercentageOfUserSalary(foodCostsPercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(foodCostsPercentageOfUserSalaryBD);

    }

    @PostMapping("/current_electricity_bill-percentage")
    public ResponseEntity<BigDecimal> createCurrentElectricityBillPercentageOfUserSalary(@PathVariable Long userId,
                                                                       @RequestParam int month){
        BigDecimal currentElectricityBillPercentageOfUserSalaryBD = userService
                .calculateCurrentElectricityBillCostPercentageOfUserSalaryForMonthlyCosts(userId, month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setCurrentElectricityBillPercentageOfUserSalary(currentElectricityBillPercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(currentElectricityBillPercentageOfUserSalaryBD);
    }

    @PostMapping("/current_gas_bill-percentage")
    public ResponseEntity<BigDecimal> createCurrentGasBillPercentageOfUserSalary(@PathVariable Long userId,
                                                                                 @RequestParam int month){
        BigDecimal currentGasBillPercentageOfUserSalaryBD = userService
                .calculateCurrentGasBillCostPercentageOfUserSalaryForMonthlyCosts(userId, month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setCurrentGasBillPercentageOfUserSalary(currentGasBillPercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(currentGasBillPercentageOfUserSalaryBD);
    }

    @PostMapping("/total_car_service-percentage")
    public ResponseEntity<BigDecimal> createTotalCarServicePercentageOfUserSalary(@PathVariable Long userId,
                                                                                 @RequestParam int month){
        BigDecimal totalCarServicePercentageOfUserSalaryBD = userService
                .calculateTotalCarServiceCostsPercentageOfUserSalaryForMonthlyCosts(userId, month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setTotalCarServicePercentageOfUserSalary(totalCarServicePercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(totalCarServicePercentageOfUserSalaryBD);
    }

    @PostMapping("/car_insurance_costs-percentage")
    public ResponseEntity<BigDecimal> createCarInsuranceCostsPercentageOfUserSalary(@PathVariable Long userId,
                                                                                  @RequestParam int month){
        BigDecimal carInsuranceCostsPercentageOfUserSalaryBD = userService
                .calculateCarInsuranceCostsPercentageOfUserSalaryForMonthlyCosts(userId, month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setCarInsuranceCostsPercentageOfUserSalary(carInsuranceCostsPercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(carInsuranceCostsPercentageOfUserSalaryBD);
    }

    @PostMapping("/car_operating_costs-percentage")
    public ResponseEntity<BigDecimal> createCarOperatingCostsPercentageOfUserSalary(@PathVariable Long userId,
                                                                                    @RequestParam int month){
        BigDecimal carOperatingCostsPercentageOfUserSalaryBD = userService
                .calculateCarOperatingCostsPercentageOfUserSalaryForMonthlyCosts(userId, month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setCarOperatingCostsPercentageOfUserSalary(carOperatingCostsPercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(carOperatingCostsPercentageOfUserSalaryBD);
    }

    @PostMapping("/costs-percentage")
    public ResponseEntity<BigDecimal> createAllCostsPercentageOfUserSalary(@PathVariable Long userId,
                                                                        @RequestParam int month) {
        BigDecimal costsPercentageOfUserSalaryBD = userService
                .calculateAllCostsPercentageOfUserSalaryForMonthlyCosts(userId, month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId, month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setCostsPercentageOfUserSalary(costsPercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(costsPercentageOfUserSalaryBD);
    }

    @GetMapping("/monthly_costs_summary")
    public ResponseEntity<MonthlyCostsSummary> retrieveMonthlyCostsSummaryByUserIdAndMonth(@PathVariable Long userId,
                                                                                           @RequestParam int month){

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId, month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService
                        .getMonthlyCostsSummaryByMonthlyCostsId(monthlyCosts.getId())
                .orElseThrow(() -> new MonthlyCostsSummaryNotFoundException("No monthly costs summary found for user with id " + userId));

        return ResponseEntity.ok(monthlyCostsSummary);
     }

     @PostMapping("/net_salary_after_costs")
    public ResponseEntity<BigDecimal> createNetSalaryAfterCostsForUserByMonth(@PathVariable Long userId, @RequestParam int month){

        BigDecimal netSalaryAfterCostsBD = userService.calculateNetSalaryAfterCostsForUser(userId, month);

         MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId, month);

         MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

         monthlyCostsSummary.setNetSalaryAfterCosts(netSalaryAfterCostsBD);
         monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(netSalaryAfterCostsBD);
     }

    @PostMapping("/advice")
    public String getOrCreateFinancialAdvice(@PathVariable Long userId, @RequestParam int month) {

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getMonthlyCostsSummaryForUserByMonth(userId,month)
                .orElseThrow(() -> new MonthlyCostsSummaryNotFoundException("No monthly costs summary found for user with id " + userId));

        BigDecimal netSalaryAfterCostsNow = userService.calculateNetSalaryAfterCostsForUser(userId, month);

        BigDecimal netSalaryAfterCostsPrevious = monthlyCostsSummary.getNetSalaryAfterCosts();

        if(netSalaryAfterCostsPrevious == null && netSalaryAfterCostsNow == null)
            throw new NetSalaryAfterCostsNotFoundException("No net salary after costs found for user: " + userId +
                    "in month " + Month.of(month).name().toLowerCase());

        if(monthlyCostsSummary.getFinancialAdvice() != null && netSalaryAfterCostsNow.compareTo(netSalaryAfterCostsPrevious) == 0){
            return monthlyCostsSummary.getFinancialAdvice();
        }
        String prompt = userService.generatePromptBasedOnSalary(netSalaryAfterCostsNow);

        String financialAdvice = GPTService.getAdviceFromGPT(prompt);

        monthlyCostsSummary.setFinancialAdvice(financialAdvice);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return financialAdvice;

    }
}
