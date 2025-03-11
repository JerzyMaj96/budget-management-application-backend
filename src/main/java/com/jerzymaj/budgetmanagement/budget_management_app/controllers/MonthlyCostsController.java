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
import java.time.YearMonth;
import java.util.List;
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
    public ResponseEntity<MonthlyCostsDTO> createOrUpdateMonthlyCosts(@PathVariable Long userId,
                                                                      @Valid @RequestBody MonthlyCostsDTO monthlyCostsDTO) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        List<MonthlyCosts> costsFromDB = monthlyCostsService.getMonthlyCostsByUserId(user.getId());

        YearMonth currentMonth = YearMonth.now();
        MonthlyCosts existingMonthlyCosts = null;

        for (MonthlyCosts cost : costsFromDB) {
            if (cost.getCreateDate() != null &&
                    YearMonth.from(cost.getCreateDate()).equals(currentMonth)) {
                existingMonthlyCosts = cost;
                break;
            }
        }
        if (existingMonthlyCosts != null) {
            existingMonthlyCosts.setRent(monthlyCostsDTO.getRent());
            existingMonthlyCosts.setFoodCosts(monthlyCostsDTO.getFoodCosts());
            existingMonthlyCosts.setCurrentElectricityBill(monthlyCostsDTO.getCurrentElectricityBill());
            existingMonthlyCosts.setCurrentGasBill(monthlyCostsDTO.getCurrentGasBill());
            existingMonthlyCosts.setTotalCarServiceCosts(monthlyCostsDTO.getTotalCarServiceCosts());
            existingMonthlyCosts.setCarInsuranceCosts(monthlyCostsDTO.getCarInsuranceCosts());
            existingMonthlyCosts.setCarOperatingCosts(monthlyCostsDTO.getCarOperatingCosts());

            MonthlyCosts updatedCosts = monthlyCostsService.createMonthlyCostsForUser(existingMonthlyCosts);
            return ResponseEntity.ok(monthlyCostsService.convertMonthlyCostsToDTO(updatedCosts));
        }

        MonthlyCosts newCosts = new MonthlyCosts();
        newCosts.setUser(user);
        newCosts.setRent(monthlyCostsDTO.getRent());
        newCosts.setFoodCosts(monthlyCostsDTO.getFoodCosts());
        newCosts.setCurrentElectricityBill(monthlyCostsDTO.getCurrentElectricityBill());
        newCosts.setCurrentGasBill(monthlyCostsDTO.getCurrentGasBill());
        newCosts.setTotalCarServiceCosts(monthlyCostsDTO.getTotalCarServiceCosts());
        newCosts.setCarInsuranceCosts(monthlyCostsDTO.getCarInsuranceCosts());
        newCosts.setCarOperatingCosts(monthlyCostsDTO.getCarOperatingCosts());

        MonthlyCosts savedCosts = monthlyCostsService.createMonthlyCostsForUser(newCosts);
        MonthlyCostsDTO responseDTO = monthlyCostsService.convertMonthlyCostsToDTO(savedCosts);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCosts.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
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
