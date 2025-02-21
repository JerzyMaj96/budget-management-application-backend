package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
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

        MonthlyCostsSummary monthlyCostsSummary;

        MonthlyCostsSummary summaryFromDB = monthlyCostsService
                .getMonthlyCostsSummaryByMonthlyCostsId(monthlyCosts.getId()).orElse(null);

        if (summaryFromDB == null) {
            monthlyCostsSummary = new MonthlyCostsSummary();
            monthlyCostsSummary.setMonthlyCosts(monthlyCosts);
        } else {
            monthlyCostsSummary = summaryFromDB;
        }

        monthlyCostsSummary.setMonthlyCostsSum(monthlyCostsSum);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/rent-percentage")
    public ResponseEntity<BigDecimal> createRentPercentageOfUserSalary(){

    }

    @PostMapping("/food_costs-percentage")
    public ResponseEntity<BigDecimal> createRentPercentageOfUserSalary(){

    }

    @PostMapping("/current_electricity_bill-percentage")
    public ResponseEntity<BigDecimal> createRentPercentageOfUserSalary(){

    }

    @PostMapping("/current_gas_bill-percentage")
    public ResponseEntity<BigDecimal> createRentPercentageOfUserSalary(){

    }

    @PostMapping("/costs-percentage")
    public ResponseEntity<BigDecimal> createCostsPercentageOfUserSalary(@PathVariable Long userId,
                                                                        @RequestParam int month) {

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId, month);

        double costsPercentageOfUserSalary = userService
                .calculateCostsPercentageOfUserSalaryForMonthlyCosts(userId, monthlyCosts.getId());

                BigDecimal costsPercentageOfUserSalaryBD = BigDecimal.valueOf(costsPercentageOfUserSalary)
                        .setScale(2, RoundingMode.HALF_UP);

        MonthlyCostsSummary monthlyCostsSummary;

        MonthlyCostsSummary summaryFromDB = monthlyCostsService
                .getMonthlyCostsSummaryByMonthlyCostsId(monthlyCosts.getId()).orElse(null);
        if (summaryFromDB == null) {
            monthlyCostsSummary = new MonthlyCostsSummary();
            monthlyCostsSummary.setMonthlyCosts(monthlyCosts);
        } else {
            monthlyCostsSummary = summaryFromDB;
        }

        monthlyCostsSummary.setCostsPercentageOfUserSalary(costsPercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(costsPercentageOfUserSalaryBD);
    }

    @GetMapping("/monthly_costs_summary")
    public ResponseEntity<MonthlyCostsSummary> retrieveMonthlyCostsSummaryByUserIdAndMonth(@PathVariable Long userId,
                                                                                           @RequestParam int month){

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId, month);

        Optional<MonthlyCostsSummary> monthlyCostsSummary = monthlyCostsService
                        .getMonthlyCostsSummaryByMonthlyCostsId(monthlyCosts.getId());

        if(monthlyCostsSummary.isEmpty())
            throw new MonthlyCostsNotFoundException("No monthly costs summary found for user with id " + userId);

        return ResponseEntity.ok(monthlyCostsSummary.get());
     }

}
