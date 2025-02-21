package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.UserNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import com.jerzymaj.budgetmanagement.budget_management_app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/budget-management/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> retrieveAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public EntityModel<User> retrieveUserById(@PathVariable Long userId) {

        User user = userService.getUserById(userId).orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        EntityModel<User> entityModel = EntityModel.of(user);

        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        User savedUser = userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
