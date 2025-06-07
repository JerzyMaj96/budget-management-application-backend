package com.jerzymaj.budgetmanagement.budget_management_app.services;

import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.MonthlyCostsDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.MonthlyCostsSummaryDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.MonthlyCostsSummaryWithAdviceDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsSummaryNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsSummaryRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlyCostsService {

    private final MonthlyCostsRepository monthlyCostsRepository;
    private final MonthlyCostsSummaryRepository monthlyCostsSummaryRepository;

    public MonthlyCostsService(MonthlyCostsRepository monthlyCostsRepository, MonthlyCostsSummaryRepository monthlyCostsSummaryRepository) {
        this.monthlyCostsRepository = monthlyCostsRepository;

        this.monthlyCostsSummaryRepository = monthlyCostsSummaryRepository;
    }

    public List<MonthlyCosts> getMonthlyCostsByUserId(Long userId) {
        return monthlyCostsRepository.findByUserId(userId);
    }

    public MonthlyCosts createMonthlyCostsForUser(MonthlyCosts monthlyCosts) {
        return monthlyCostsRepository.save(monthlyCosts);
    }

    public Optional<MonthlyCosts> getMonthlyCostsById(Long monthlyCostsId) {
        return monthlyCostsRepository.findById(monthlyCostsId);
    }

    public Optional<MonthlyCostsSummary> getMonthlyCostsSummaryByMonthlyCostsId(Long monthlyCostsId){
        return monthlyCostsSummaryRepository.findByMonthlyCostsId(monthlyCostsId);
    }

    public MonthlyCostsSummary createMonthlyCostsSummaryForUser(MonthlyCostsSummary monthlyCostsSummary){
        return monthlyCostsSummaryRepository.save(monthlyCostsSummary);
    };

    public MonthlyCostsSummary getOrCreateMonthlyCostsSummary(MonthlyCosts monthlyCosts){

        MonthlyCostsSummary summaryFromDB = getMonthlyCostsSummaryByMonthlyCostsId(monthlyCosts.getId())
                .orElse(null);

        if (summaryFromDB == null) {
            MonthlyCostsSummary newSummary = new MonthlyCostsSummary();
            newSummary.setMonthlyCosts(monthlyCosts);
            return newSummary;
        }
        return summaryFromDB;
    }

    public MonthlyCosts getMonthlyCostsForUserByMonth(Long userId, int month) {
        List<MonthlyCosts> monthlyCostsList = getMonthlyCostsByUserId(userId);
        if (monthlyCostsList.isEmpty()) {
            throw new MonthlyCostsNotFoundException("User with id " + userId + " has no monthly costs.");
        }
        for (MonthlyCosts monthlyCost : monthlyCostsList) {
            if (monthlyCost.getLastModifiedDate().getMonth() == Month.of(month)) {
                return monthlyCost;
            }
        }
        throw new MonthlyCostsNotFoundException("No monthly costs found for month " + Month.of(month).name().toLowerCase());
    }

    public double addUpAllMonthlyCostsForUser(Long userId, int month ) {
        MonthlyCosts monthlyCosts = getMonthlyCostsForUserByMonth(userId, month);

        double rent = monthlyCosts.getRent();
        double foodCosts = monthlyCosts.getFoodCosts();
        double electricity = monthlyCosts.getCurrentElectricityBill();
        double gas = monthlyCosts.getCurrentGasBill();
        double carServiceCosts = monthlyCosts.getTotalCarServiceCosts() != null ? monthlyCosts.getTotalCarServiceCosts() : 0.0;
        double carInsuranceCosts = monthlyCosts.getCarInsuranceCosts() != null ? monthlyCosts.getCarInsuranceCosts() : 0.0;
        double carOperatingCosts = monthlyCosts.getCarOperatingCosts() != null ? monthlyCosts.getCarOperatingCosts() : 0.0;

        return rent + foodCosts + electricity + gas + carServiceCosts + carInsuranceCosts + carOperatingCosts;
    }

    public Optional<MonthlyCostsSummary> getMonthlyCostsSummaryForUserByMonth(Long userId, int month) {
        List<MonthlyCosts> monthlyCostsList = getMonthlyCostsByUserId(userId);

        if (monthlyCostsList.isEmpty())
            throw new MonthlyCostsNotFoundException("User with id " + userId + " has no monthly costs.");


        for (MonthlyCosts monthlyCost : monthlyCostsList)
            if (monthlyCost.getLastModifiedDate().getMonth() == Month.of(month))
                return getMonthlyCostsSummaryByMonthlyCostsId(monthlyCost.getId());


        throw new MonthlyCostsSummaryNotFoundException("No monthly cost summary found for user " + userId + " in month " + month);
    }

    public MonthlyCostsDTO convertMonthlyCostsToDTO(MonthlyCosts monthlyCosts) {
        return new MonthlyCostsDTO(
                monthlyCosts.getId(),
                monthlyCosts.getRent(),
                monthlyCosts.getFoodCosts(),
                monthlyCosts.getCurrentElectricityBill(),
                monthlyCosts.getCurrentGasBill(),
                monthlyCosts.getTotalCarServiceCosts() != null ? monthlyCosts.getTotalCarServiceCosts() : 0.0,
                monthlyCosts.getCarInsuranceCosts() != null ? monthlyCosts.getCarInsuranceCosts() : 0.0,
                monthlyCosts.getCarOperatingCosts() != null ? monthlyCosts.getCarOperatingCosts() : 0.0,
                monthlyCosts.getCreateDate()
        );
    }

    public MonthlyCostsSummaryDTO convertMonthlyCostsSummaryToDTO(MonthlyCostsSummary summary) {
        return new MonthlyCostsSummaryDTO(summary.getId(),
                summary.getMonthlyCostsSum(),
                summary.getRentPercentageOfUserSalary(),
                summary.getFoodCostsPercentageOfUserSalary(),
                summary.getCurrentElectricityBillPercentageOfUserSalary(),
                summary.getCurrentGasBillPercentageOfUserSalary(),
                summary.getTotalCarServicePercentageOfUserSalary(),
                summary.getCarInsuranceCostsPercentageOfUserSalary(),
                summary.getCarOperatingCostsPercentageOfUserSalary(),
                summary.getCostsPercentageOfUserSalary(),
                summary.getNetSalaryAfterCosts(),
                summary.getCreateDate());
    }

    public MonthlyCostsSummaryWithAdviceDTO convertMonthlyCostsSummaryToDTOWithAdvice(MonthlyCostsSummary summary) {
        MonthlyCostsSummaryWithAdviceDTO dto = new MonthlyCostsSummaryWithAdviceDTO();
        MonthlyCostsSummaryDTO base = convertMonthlyCostsSummaryToDTO(summary);
        BeanUtils.copyProperties(base, dto);
        dto.setFinancialAdvice(summary.getFinancialAdvice());
        return dto;
    }

    public MonthlyCostsDTO createOrUpdateMonthlyCosts(User user, MonthlyCostsDTO monthlyCostsDTO, List<MonthlyCosts> costsFromDB) {
        YearMonth currentYearMonth = YearMonth.now();
        MonthlyCosts monthlyCosts = null;

        for (MonthlyCosts cost : costsFromDB) {
            if (cost.getCreateDate() != null && YearMonth.from(cost.getCreateDate()).equals(currentYearMonth)) {
                monthlyCosts = cost;
                break;
            }
        }

        if (monthlyCosts == null) {
            monthlyCosts = new MonthlyCosts();
            monthlyCosts.setUser(user);
        }

        monthlyCosts.setRent(monthlyCostsDTO.getRent());
        monthlyCosts.setFoodCosts(monthlyCostsDTO.getFoodCosts());
        monthlyCosts.setCurrentElectricityBill(monthlyCostsDTO.getCurrentElectricityBill());
        monthlyCosts.setCurrentGasBill(monthlyCostsDTO.getCurrentGasBill());
        monthlyCosts.setTotalCarServiceCosts(monthlyCostsDTO.getTotalCarServiceCosts());
        monthlyCosts.setCarInsuranceCosts(monthlyCostsDTO.getCarInsuranceCosts());
        monthlyCosts.setCarOperatingCosts(monthlyCostsDTO.getCarOperatingCosts());

        MonthlyCosts savedCosts = createMonthlyCostsForUser(monthlyCosts);
        return convertMonthlyCostsToDTO(savedCosts);
    }

    public void updateMonthlyCostsSummary(Long userId, int month, BigDecimal value, String costType) {
        MonthlyCosts monthlyCosts = getMonthlyCostsForUserByMonth(userId, month);
        MonthlyCostsSummary monthlyCostsSummary = getOrCreateMonthlyCostsSummary(monthlyCosts);

        switch (costType) {
            case "rent" -> monthlyCostsSummary.setRentPercentageOfUserSalary(value);
            case "food" -> monthlyCostsSummary.setFoodCostsPercentageOfUserSalary(value);
            case "electricity" -> monthlyCostsSummary.setCurrentElectricityBillPercentageOfUserSalary(value);
            case "gas" -> monthlyCostsSummary.setCurrentGasBillPercentageOfUserSalary(value);
            case "car_service" -> monthlyCostsSummary.setTotalCarServicePercentageOfUserSalary(value);
            case "car_insurance" -> monthlyCostsSummary.setCarInsuranceCostsPercentageOfUserSalary(value);
            case "car_operating" -> monthlyCostsSummary.setCarOperatingCostsPercentageOfUserSalary(value);
            case "net_salary" -> monthlyCostsSummary.setNetSalaryAfterCosts(value);
            case "total_costs" -> monthlyCostsSummary.setCostsPercentageOfUserSalary(value);
        }

        createMonthlyCostsSummaryForUser(monthlyCostsSummary);
    }
}

