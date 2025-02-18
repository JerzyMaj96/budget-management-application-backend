package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import com.jerzymaj.budgetmanagement.budget_management_app.services.MonthlyCostsService;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.UserNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import com.jerzymaj.budgetmanagement.budget_management_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/budget-management")
public class UserResource {

    private final UserService userService;
    private final MonthlyCostsService monthlyCostsService;

    public UserResource(UserService userService, MonthlyCostsService monthlyCostsService) {
        this.userService = userService;
        this.monthlyCostsService = monthlyCostsService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable Long id) {

        User user = userService.getUserById(id).orElseThrow(() -> new UserNotFoundException("id " + id));

        EntityModel<User> entityModel = EntityModel.of(user);

        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        User savedUser = userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/monthly_costs")
    public ResponseEntity<MonthlyCosts> retrieveMonthlyCostsByUserId(@PathVariable Long id) {
        Optional<MonthlyCosts> monthlyCosts = monthlyCostsService.getMonthlyCostsByUserId(id);

        if (monthlyCosts.isEmpty()) {
            throw new MonthlyCostsNotFoundException("User with id " + id + " has no monthly costs.");
        }

        return ResponseEntity.ok(monthlyCosts.get());
    }

    @PostMapping("/users/{id}/monthly_costs")
    public ResponseEntity<MonthlyCosts> createMonthlyCostsForUser(@PathVariable Long id,
                                                                  @Valid @RequestBody MonthlyCosts monthlyCosts) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty())
            throw new UserNotFoundException("id " + id);

        monthlyCosts.setUser(user.get());

        MonthlyCosts savedMonthlyCosts = monthlyCostsService.createMonthlyCostsForUser(monthlyCosts);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMonthlyCosts.getId())
                .toUri();


        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{userId}/monthly_costs/monthly_costs_results")
    public ResponseEntity<MonthlyCostsSummary> retrieveMonthlyCostsResultsByUserId(@PathVariable Long userId){

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsByUserId(userId)
                .orElseThrow(() -> new MonthlyCostsNotFoundException("User with id " + userId + " has no monthly costs."));

        MonthlyCostsSummary monthlyCostsResults = monthlyCosts.getMonthlyCostsResults();

        return ResponseEntity.ok(monthlyCostsResults);
    }

    @PostMapping("/users/{userId}/monthly_costs/sum")
    public ResponseEntity<Void> sumUpAllTheMonthlyCosts(@PathVariable Long userId){
       double monthlyCostsSum = monthlyCostsService.addUpAllMonthlyCostsForUser(userId);

       Optional<MonthlyCosts> monthlyCosts = monthlyCostsService.getMonthlyCostsByUserId(userId);

        if(monthlyCosts.isEmpty())
            throw new MonthlyCostsNotFoundException("User with id " + userId + " has no monthly costs.");

       Optional<MonthlyCostsSummary> monthlyCostsResults = monthlyCostsService
               .getMonthlyCostsResultsByMonthlyCostsId(monthlyCosts.get().getId());

       if (monthlyCostsResults.isEmpty()){
           MonthlyCostsSummary newMonthlyCostsResults = new MonthlyCostsSummary();
           newMonthlyCostsResults.setMonthlyCosts(monthlyCosts.get());
           newMonthlyCostsResults.setMonthlyCostsSum(monthlyCostsSum);

           monthlyCostsService.createMonthlyCostsResultsForUser(newMonthlyCostsResults);
       } else {
           monthlyCostsResults.get().setMonthlyCostsSum(monthlyCostsSum);
           monthlyCostsService.createMonthlyCostsResultsForUser(monthlyCostsResults.get());
       }

        return ResponseEntity.ok().build();
    }


    @GetMapping("/users/{id}/monthly_costs/costs_percentage_of_salary")
    public BigDecimal retrieveCostsPercentageOfUserSalary(@PathVariable Long id){

        double costsPercentageOfUserSalary = userService.calculateCostsPercentageOfUserSalary(id);

        BigDecimal costsPercentageOfUserSalaryBD = new BigDecimal(costsPercentageOfUserSalary);

        costsPercentageOfUserSalaryBD = costsPercentageOfUserSalaryBD.setScale(2, RoundingMode.HALF_UP);

        return costsPercentageOfUserSalaryBD;
    }

}
