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

        MonthlyCostsDTO responseMonthlyCostsDTO = monthlyCostsService.createOrUpdateMonthlyCosts(user, monthlyCostsDTO, costsFromDB);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseMonthlyCostsDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseMonthlyCostsDTO);
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
