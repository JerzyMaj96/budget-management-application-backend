package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import com.jerzymaj.budgetmanagement.budget_management_app.services.MonthlyCostsService;
import com.jerzymaj.budgetmanagement.budget_management_app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/budget-management/users/{userId}/monthly_costs/{monthlyCostsId}")
public class MonthlyCostsSummaryController {

    private final UserService userService;
    private final MonthlyCostsService monthlyCostsService;

    public MonthlyCostsSummaryController(UserService userService, MonthlyCostsService monthlyCostsService) {
        this.userService = userService;
        this.monthlyCostsService = monthlyCostsService;
    }

    @PostMapping("/sum")
    public ResponseEntity<Void> sumUpAllTheMonthlyCosts(@PathVariable Long userId, @PathVariable Long monthlyCostsId){
        double monthlyCostsSum = monthlyCostsService.addUpAllMonthlyCostsForUser(userId, monthlyCostsId);

        Optional<MonthlyCosts> monthlyCostsOptional = monthlyCostsService.getMonthlyCostsById(monthlyCostsId);

        if(monthlyCostsOptional.isEmpty())
            throw new MonthlyCostsNotFoundException("User with id " + userId + " has no monthly costs.");

        Optional<MonthlyCostsSummary> monthlyCostsSummaryOptional = monthlyCostsService
                        .getMonthlyCostsSummaryByMonthlyCostsId(monthlyCostsId);

        MonthlyCostsSummary monthlyCostsSummary;

        if (monthlyCostsSummaryOptional.isEmpty()) {
            monthlyCostsSummary = new MonthlyCostsSummary();
            monthlyCostsSummary.setMonthlyCosts(monthlyCostsOptional.get());
        } else {
            monthlyCostsSummary = monthlyCostsSummaryOptional.get();
        }

        monthlyCostsSummary.setMonthlyCostsSum(monthlyCostsSum);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/costs-percentage")
    public ResponseEntity<BigDecimal> createCostsPercentageOfUserSalary(@PathVariable Long userId,
                                                                        @PathVariable Long monthlyCostsId) {
        double costsPercentageOfUserSalary = userService.calculateCostsPercentageOfUserSalaryForMonthlyCosts(userId,
                monthlyCostsId);

        BigDecimal costsPercentageOfUserSalaryBD = BigDecimal.valueOf(costsPercentageOfUserSalary)
                .setScale(2, RoundingMode.HALF_UP);

        Optional<MonthlyCosts> monthlyCostsOptional = monthlyCostsService.getMonthlyCostsById(monthlyCostsId);

        if (monthlyCostsOptional.isEmpty()) {
            throw new MonthlyCostsNotFoundException("Monthly costs with id " + monthlyCostsId +
                    " not found for user with id " + userId);
        }
        MonthlyCosts monthlyCosts = monthlyCostsOptional.get();

        Optional<MonthlyCostsSummary> monthlyCostsSummaryOptional = monthlyCostsService
                .getMonthlyCostsSummaryByMonthlyCostsId(monthlyCostsId);

        MonthlyCostsSummary monthlyCostsSummary;

        if (monthlyCostsSummaryOptional.isEmpty()) {
            monthlyCostsSummary = new MonthlyCostsSummary();
            monthlyCostsSummary.setMonthlyCosts(monthlyCosts);
        } else {
            monthlyCostsSummary = monthlyCostsSummaryOptional.get();
        }

        monthlyCostsSummary.setCostsPercentageOfUserSalary(costsPercentageOfUserSalaryBD);
        monthlyCostsService.createMonthlyCostsSummaryForUser(monthlyCostsSummary);

        return ResponseEntity.ok(costsPercentageOfUserSalaryBD);
    }

    @GetMapping("/monthly_costs_summary")
    public ResponseEntity<MonthlyCostsSummary> retrieveMonthlyCostsSummaryByUserIdAndMonthlyCostsId(@PathVariable Long userId,
                                                                                   @PathVariable Long monthlyCostsId){
        List<MonthlyCosts> monthlyCosts = monthlyCostsService.getMonthlyCostsByUserId(userId);

        for (MonthlyCosts monthlyCost : monthlyCosts)
            if (Objects.equals(monthlyCost.getId(), monthlyCostsId)){

                Optional<MonthlyCostsSummary> monthlyCostsSummary = monthlyCostsService
                        .getMonthlyCostsSummaryByMonthlyCostsId(monthlyCostsId);

                if(monthlyCostsSummary.isEmpty())
                    throw new MonthlyCostsNotFoundException("No monthly costs summary found for user with id " + userId);

                return ResponseEntity.ok(monthlyCostsSummary.get());
         }
        throw new MonthlyCostsNotFoundException("No monthly costs summary found for user with id " + userId);
     }

}
