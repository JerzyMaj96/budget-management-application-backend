package com.jerzymaj.budgetmanagement.budget_management_app.services;

import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsSummaryNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsSummaryRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import org.springframework.stereotype.Service;

import java.time.Month;
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
}

