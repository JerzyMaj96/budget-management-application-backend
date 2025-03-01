package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.MonthlyCostsDTO;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<MonthlyCostsDTO> createMonthlyCostsForUser(@PathVariable Long userId,
                                                                     @Valid @RequestBody MonthlyCostsDTO monthlyCostsDTO) {
        Optional<User> user = userService.getUserById(userId);

        if (user.isEmpty())
            throw new UserNotFoundException("User not found: " + userId);

        List<MonthlyCosts> existingMonthlyCostsCheck = monthlyCostsService.getMonthlyCostsByUserId(userId);

        for (MonthlyCosts monthlyCost : existingMonthlyCostsCheck)
            if (monthlyCost.getCreateDate().getMonth() == monthlyCostsDTO.getCreateDate().getMonth()) {
                monthlyCost.setRent(monthlyCostsDTO.getRent());
                monthlyCost.setFoodCosts(monthlyCostsDTO.getFoodCosts());
                monthlyCost.setCurrentElectricityBill(monthlyCostsDTO.getCurrentElectricityBill());
                monthlyCost.setCurrentGasBill(monthlyCostsDTO.getCurrentGasBill());

                MonthlyCosts updatedCost = monthlyCostsService.createMonthlyCostsForUser(monthlyCost);
                return ResponseEntity.ok(monthlyCostsService.convertMonthlyCostsToDTO(updatedCost));
            }

        MonthlyCosts monthlyCosts = new MonthlyCosts();
        monthlyCosts.setUser(user.get());
        monthlyCosts.setRent(monthlyCostsDTO.getRent());
        monthlyCosts.setFoodCosts(monthlyCostsDTO.getFoodCosts());
        monthlyCosts.setCurrentElectricityBill(monthlyCostsDTO.getCurrentElectricityBill());
        monthlyCosts.setCurrentGasBill(monthlyCostsDTO.getCurrentGasBill());

        MonthlyCosts savedMonthlyCosts = monthlyCostsService.createMonthlyCostsForUser(monthlyCosts);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMonthlyCosts.getId())
                .toUri();

        return ResponseEntity.created(location).body(monthlyCostsService.convertMonthlyCostsToDTO(savedMonthlyCosts));
    }

    @GetMapping("/byUserId")
    public ResponseEntity<List<MonthlyCostsDTO>> retrieveMonthlyCostsByUserId(@PathVariable Long userId) {
        List<MonthlyCosts> monthlyCosts = monthlyCostsService.getMonthlyCostsByUserId(userId);

        if (monthlyCosts.isEmpty()) {
            throw new MonthlyCostsNotFoundException("No monthly costs found for user " + userId);
        }

        List<MonthlyCostsDTO> dtos = monthlyCosts
                .stream()
                .map(monthlyCostsService::convertMonthlyCostsToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/byMonth")
    public ResponseEntity<MonthlyCostsDTO> retrieveMonthlyCostsByUserIdAndMonthOfMonthlyCosts(
            @PathVariable Long userId, @RequestParam int creationMonth) {

        if (creationMonth < 1 || creationMonth > 12) {
            throw new IllegalArgumentException("Invalid month: " + creationMonth + ". Month should be between 1 and 12.");
        }

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId, creationMonth);

        return ResponseEntity.ok(monthlyCostsService.convertMonthlyCostsToDTO(monthlyCosts));
    }

}
