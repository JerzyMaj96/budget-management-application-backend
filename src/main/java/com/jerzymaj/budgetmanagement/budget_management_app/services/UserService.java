package com.jerzymaj.budgetmanagement.budget_management_app.services;

import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.UserNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.UserRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public double calculateRentCostPercentageOfUserSalaryForMonthlyCosts(Long userId, Long monthlyCostsId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        List<MonthlyCosts> monthlyCostsList = monthlyCostsService.getMonthlyCostsByUserId(userId);

        for(MonthlyCosts monthlyCost : monthlyCostsList)
            if (Objects.equals(monthlyCost.getId(), monthlyCostsId)) {
                return (monthlyCost.getRent() / user.getNettSalary() * 100);
            }
        throw new MonthlyCostsNotFoundException("No monthly costs found with id " + monthlyCostsId + " for user " + userId);
    }

    public double calculateFoodCostsCostPercentageOfUserSalaryForMonthlyCosts(Long userId, Long monthlyCostsId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        List<MonthlyCosts> monthlyCostsList = monthlyCostsService.getMonthlyCostsByUserId(userId);

        for(MonthlyCosts monthlyCost : monthlyCostsList)
            if (Objects.equals(monthlyCost.getId(), monthlyCostsId)) {
                return (monthlyCost.getFoodCosts() / user.getNettSalary() * 100);
            }
        throw new MonthlyCostsNotFoundException("No monthly costs found with id " + monthlyCostsId + " for user " + userId);
    }

    public double calculateCurrentElectricityBillCostPercentageOfUserSalaryForMonthlyCosts(Long userId,
                                                                                           Long monthlyCostsId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        List<MonthlyCosts> monthlyCostsList = monthlyCostsService.getMonthlyCostsByUserId(userId);

        for(MonthlyCosts monthlyCost : monthlyCostsList)
            if (Objects.equals(monthlyCost.getId(), monthlyCostsId)) {
                return (monthlyCost.getCurrentElectricityBill() / user.getNettSalary() * 100);
            }
        throw new MonthlyCostsNotFoundException("No monthly costs found with id " + monthlyCostsId + " for user " + userId);
    }

    public double calculateCurrentGasBillCostPercentageOfUserSalaryForMonthlyCosts(Long userId,
                                                                                           Long monthlyCostsId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        List<MonthlyCosts> monthlyCostsList = monthlyCostsService.getMonthlyCostsByUserId(userId);

        for(MonthlyCosts monthlyCost : monthlyCostsList)
            if (Objects.equals(monthlyCost.getId(), monthlyCostsId)) {
                return (monthlyCost.getCurrentElectricityBill() / user.getNettSalary() * 100);
            }
        throw new MonthlyCostsNotFoundException("No monthly costs found with id " + monthlyCostsId + " for user " + userId);
    }



    public double calculateCostsPercentageOfUserSalaryForMonthlyCosts(Long userId, Long monthlyCostsId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        double totalCosts = monthlyCostsService.addUpAllMonthlyCostsForUser(userId, monthlyCostsId);
        return (totalCosts / user.getNettSalary() * 100);
    }
}
