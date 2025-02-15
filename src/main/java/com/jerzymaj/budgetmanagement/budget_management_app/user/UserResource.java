package com.jerzymaj.budgetmanagement.budget_management_app.user;

import com.jerzymaj.budgetmanagement.budget_management_app.costs.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.costs.MonthlyCostsResults;
import com.jerzymaj.budgetmanagement.budget_management_app.costs.MonthlyCostsService;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsResultsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.UserNotFoundException;
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
public class UserResource {

    private final UserService userService;
    private final MonthlyCostsService monthlyCostsService;

    public UserResource(UserService userService, MonthlyCostsService monthlyCostsService) {
        this.userService = userService;
        this.monthlyCostsService = monthlyCostsService;
    }

    @GetMapping("/budget-management/users")
    public List<User> retrieveAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/budget-management/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable Long id) {

        User user = userService.getUserById(id).orElseThrow(() -> new UserNotFoundException("id " + id));

        EntityModel<User> entityModel = EntityModel.of(user);

        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/budget-management/users/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/budget-management/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        User savedUser = userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/budget-management/users/{id}/monthly_costs")
    public ResponseEntity<MonthlyCosts> retrieveMonthlyCostsByUserId(@PathVariable Long id) {
        Optional<MonthlyCosts> monthlyCosts = monthlyCostsService.getMonthlyCostsByUserId(id);

        if (monthlyCosts.isEmpty()) {
            throw new MonthlyCostsNotFoundException("User with id " + id + " has no monthly costs.");
        }

        return ResponseEntity.ok(monthlyCosts.get());
    }

    @PostMapping("/budget-management/users/{id}/monthly_costs")
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

    @PostMapping("/budget-management/users/{userId}/monthly_costs/sum")
    public void sumUpAllTheMonthlyCosts(@PathVariable Long userId){
       double monthlyCostsSum = monthlyCostsService.addUpAllMonthlyCostsForUser(userId);

       Optional<MonthlyCosts> monthlyCosts = monthlyCostsService.getMonthlyCostsByUserId(userId);

        if(monthlyCosts.isEmpty())
            throw new MonthlyCostsNotFoundException("User with id " + userId + " has no monthly costs.");

       Optional<MonthlyCostsResults> monthlyCostsResults = monthlyCostsService
               .getMonthlyCostsResultsByMonthlyCostsId(monthlyCosts.get().getId());

       if (monthlyCostsResults.isEmpty())
           throw new MonthlyCostsResultsNotFoundException("User with id " + userId + " has no monthly costs results.");


        monthlyCostsResults.get().setMonthlyCostsSum(monthlyCostsSum);

        monthlyCostsService.createMonthlyCostsResultsForUser(monthlyCostsResults.get());

        monthlyCostsService.createMonthlyCostsForUser(monthlyCosts.get());
    }


    @GetMapping("/budget-management/users/{id}/costs_percentage_of_salary")
    public BigDecimal retrieveCostsPercentageOfUserSalary(@PathVariable Long id){

        double costsPercentageOfUserSalary = userService.calculateCostsPercentageOfUserSalary(id);

        BigDecimal costsPercentageOfUserSalaryBD = new BigDecimal(costsPercentageOfUserSalary);

        costsPercentageOfUserSalaryBD = costsPercentageOfUserSalaryBD.setScale(2, RoundingMode.HALF_UP);

        return costsPercentageOfUserSalaryBD;
    }

}
