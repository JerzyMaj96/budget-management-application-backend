package com.jerzymaj.budgetmanagement.budget_management_app.controllers;

import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.UserDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.ExistingUserException;
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
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<UserDTO> retrieveAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getNetSalary()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public EntityModel<UserDTO> retrieveUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getNetSalary());

        EntityModel<UserDTO> entityModel = EntityModel.of(userDTO);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        for (User user : userService.getAllUsers()) {
            if (Objects.equals(userDTO.getName(), user.getName())) {
                throw new ExistingUserException("User with the name " + userDTO.getName() + " already exists.");
            }
        }

        User newUser = new User(userDTO.getId(), userDTO.getName(), userDTO.getNetSalary());
        User savedUser = userService.createUser(newUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        UserDTO savedUserDTO = userService.convertUserToDTO(savedUser);

        return ResponseEntity.created(location).body(savedUserDTO);
    }


}
