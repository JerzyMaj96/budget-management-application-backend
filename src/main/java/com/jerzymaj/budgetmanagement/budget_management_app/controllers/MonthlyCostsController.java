package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.UserNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import com.jerzymaj.budgetmanagement.budget_management_app.services.MonthlyCostsService;
import com.jerzymaj.budgetmanagement.budget_management_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/budget-management/users/{userId}/monthly_costs")
public class MonthlyCostsController {

    private final UserService userService;
    private final MonthlyCostsService monthlyCostsService;

    public MonthlyCostsController(UserService userService, MonthlyCostsService monthlyCostsService) {
        this.userService = userService;
        this.monthlyCostsService = monthlyCostsService;
    }

    @PostMapping
    public ResponseEntity<MonthlyCosts> createMonthlyCostsForUser(@PathVariable Long userId,
                                                                  @Valid @RequestBody MonthlyCosts monthlyCosts) {
        Optional<User> user = userService.getUserById(userId);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found: " + userId);

        List<MonthlyCosts> existingMonthlyCostsCheck = monthlyCostsService.getMonthlyCostsByUserId(userId);

        for (MonthlyCosts monthlyCost : existingMonthlyCostsCheck)
            if (monthlyCost.getCreateDate().getMonth() == monthlyCosts.getCreateDate().getMonth()) {
                monthlyCost.setRent(monthlyCosts.getRent());
                monthlyCost.setFoodCosts(monthlyCosts.getFoodCosts());
                monthlyCost.setCurrentElectricityBill(monthlyCosts.getCurrentElectricityBill());
                monthlyCost.setCurrentGasBill(monthlyCosts.getCurrentGasBill());

                MonthlyCosts updatedCost = monthlyCostsService.createMonthlyCostsForUser(monthlyCost);
                return ResponseEntity.ok(updatedCost);
            }

        monthlyCosts.setUser(user.get());

        MonthlyCosts savedMonthlyCosts = monthlyCostsService.createMonthlyCostsForUser(monthlyCosts);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMonthlyCosts.getId())
                .toUri();


        return ResponseEntity.created(location).build();
    }

    @GetMapping("/byUserId")
    public ResponseEntity<List<MonthlyCosts>> retrieveMonthlyCostsByUserId(@PathVariable Long userId) {
        List<MonthlyCosts> monthlyCosts = monthlyCostsService.getMonthlyCostsByUserId(userId);

        if (monthlyCosts.isEmpty()) {
            throw new MonthlyCostsNotFoundException("No monthly costs found for user " + userId);
        }

        return ResponseEntity.ok(monthlyCosts);
    }

    @GetMapping("/byMonth")
    public ResponseEntity<MonthlyCosts> retrieveMonthlyCostsByUserIdAndMonthOfMonthlyCosts(
            @PathVariable Long userId, @RequestParam int creationMonth) {

        if (creationMonth < 1 || creationMonth > 12) {
            throw new IllegalArgumentException("Invalid month: " + creationMonth + ". Month should be between 1 and 12.");
        }

        List<MonthlyCosts> monthlyCosts = monthlyCostsService.getMonthlyCostsByUserId(userId);

        if (monthlyCosts.isEmpty()) {
            throw new MonthlyCostsNotFoundException("User with id " + userId + " has no monthly costs.");
        }

        Month requestedMonth = Month.of(creationMonth);

        for (MonthlyCosts monthlyCost : monthlyCosts) {
            if (monthlyCost.getCreateDate().getMonth() == requestedMonth) {
                return ResponseEntity.ok(monthlyCost);
            }
        }

        throw new MonthlyCostsNotFoundException("User with id " + userId + " has no monthly costs in " +
                requestedMonth.name().toLowerCase());
    }
}
