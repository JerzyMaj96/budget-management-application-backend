package com.jerzymaj.budgetmanagement.budget_management_app.user;

import com.jerzymaj.budgetmanagement.budget_management_app.costs.MonthlyCostsService;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.UserNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MonthlyCostsService monthlyCostsService;

    public UserService(UserRepository userRepository, MonthlyCostsService monthlyCostsService) {
        this.userRepository = userRepository;
        this.monthlyCostsService = monthlyCostsService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public double calculateCostsPercentageOfUserSalary(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException("id " + userId);
        }

        double totalCosts;

        try {
             totalCosts = monthlyCostsService.addUpAllMonthlyCostsForUser(userId);
        } catch (MonthlyCostsNotFoundException e) {
            throw new MonthlyCostsNotFoundException("No monthly costs found for user with id " + userId);
        }
        if (user.get().getGrossSalary() == 0) {
            throw new IllegalArgumentException("Gross salary cannot be zero for user with id " + userId);
        }


        return (totalCosts / user.get().getGrossSalary()) * 100;
    }
}
