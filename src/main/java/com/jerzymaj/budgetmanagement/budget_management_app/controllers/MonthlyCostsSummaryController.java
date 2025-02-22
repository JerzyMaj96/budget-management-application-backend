package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsSummaryNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.UserNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import com.jerzymaj.budgetmanagement.budget_management_app.services.MonthlyCostsService;
import com.jerzymaj.budgetmanagement.budget_management_app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        double monthlyCostsSum = monthlyCostsService.addUpAllMonthlyCostsForUser(userId, monthlyCosts.getId());

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setMonthlyCostsSum(monthlyCostsSum);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/rent-percentage")
    public ResponseEntity<BigDecimal> createRentPercentageOfUserSalary(@PathVariable Long userId,
                                                                       @RequestParam int month){
       BigDecimal rentPercentageOdUserSalaryBD = userService.calculateRentCostPercentageOfUserSalaryForMonthlyCosts(userId,month);

       MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

       MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setRentPercentageOfUserSalary(rentPercentageOdUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(rentPercentageOdUserSalaryBD);
    }

    @PostMapping("/food_costs-percentage")
    public ResponseEntity<BigDecimal> createFoodCostsPercentageOfUserSalary(@PathVariable Long userId,
                                                                       @RequestParam int month){
        BigDecimal foodCostsPercentageOdUserSalaryBD = userService
                .calculateFoodCostsCostPercentageOfUserSalaryForMonthlyCosts(userId,month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setFoodCostsPercentageOfUserSalary(foodCostsPercentageOdUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(foodCostsPercentageOdUserSalaryBD);

    }

    @PostMapping("/current_electricity_bill-percentage")
    public ResponseEntity<BigDecimal> createCurrentElectricityBillPercentageOfUserSalary(@PathVariable Long userId,
                                                                       @RequestParam int month){
        BigDecimal currentElectricityBillPercentageOdUserSalaryBD = userService
                .calculateCurrentElectricityBillCostPercentageOfUserSalaryForMonthlyCosts(userId, month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setCurrentElectricityBillPercentageOfUserSalary(currentElectricityBillPercentageOdUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(currentElectricityBillPercentageOdUserSalaryBD);
    }

    @PostMapping("/current_gas_bill-percentage")
    public ResponseEntity<BigDecimal> createCurrentGasBillPercentageOfUserSalary(@PathVariable Long userId,
                                                                                 @RequestParam int month){
        BigDecimal currentGasBillPercentageOdUserSalaryBD = userService
                .calculateCurrentGasBillCostPercentageOfUserSalaryForMonthlyCosts(userId, month);

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        MonthlyCostsSummary monthlyCostsSummary = monthlyCostsService.getOrCreateMonthlyCostsSummary(monthlyCosts);

        monthlyCostsSummary.setCurrentGasBillPercentageOfUserSalary(currentGasBillPercentageOdUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(currentGasBillPercentageOdUserSalaryBD);
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

}
