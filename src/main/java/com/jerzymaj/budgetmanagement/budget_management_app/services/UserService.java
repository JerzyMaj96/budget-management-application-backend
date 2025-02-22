package com.jerzymaj.budgetmanagement.budget_management_app.services;

import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.UserNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.UserRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
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

    public BigDecimal calculateRentCostPercentageOfUserSalaryForMonthlyCosts(Long userId, int month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        double rentPercentageOdUserSalary = monthlyCosts.getRent() / user.getNetSalary() * 100;

        return  BigDecimal.valueOf(rentPercentageOdUserSalary).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateFoodCostsCostPercentageOfUserSalaryForMonthlyCosts(Long userId, int month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        double foodCostsPercentageOdUserSalary = monthlyCosts.getFoodCosts() / user.getNetSalary() * 100;

        return BigDecimal.valueOf(foodCostsPercentageOdUserSalary).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateCurrentElectricityBillCostPercentageOfUserSalaryForMonthlyCosts(Long userId,
                                                                                           int month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        double currentElectricityBillPercentageOdUserSalary = monthlyCosts.getCurrentElectricityBill() / user.getNetSalary() * 100;

        return BigDecimal.valueOf(currentElectricityBillPercentageOdUserSalary).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateCurrentGasBillCostPercentageOfUserSalaryForMonthlyCosts(Long userId,
                                                                                           int month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId,month);

        double currentGasBillPercentageOdUserSalary = monthlyCosts.getCurrentGasBill() / user.getNetSalary() * 100;

        return BigDecimal.valueOf(currentGasBillPercentageOdUserSalary).setScale(2, RoundingMode.HALF_UP);
    }



    public BigDecimal calculateAllCostsPercentageOfUserSalaryForMonthlyCosts(Long userId, int month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        MonthlyCosts monthlyCosts = monthlyCostsService.getMonthlyCostsForUserByMonth(userId, month);

        double totalCosts = monthlyCostsService.addUpAllMonthlyCostsForUser(userId, monthlyCosts.getId());

        double costsPercentageOfUserSalary = totalCosts / user.getNetSalary() * 100;

        return BigDecimal.valueOf(costsPercentageOfUserSalary).setScale(2, RoundingMode.HALF_UP);
    }
}
