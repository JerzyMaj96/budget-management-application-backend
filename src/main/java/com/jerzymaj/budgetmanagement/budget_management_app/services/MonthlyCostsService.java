package com.jerzymaj.budgetmanagement.budget_management_app.services;

import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.exceptions.MonthlyCostsNotFoundException;
import com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories.MonthlyCostsSummaryRepository;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCosts;
import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public double addUpAllMonthlyCostsForUser(Long userId, Long monthlyCostsId ) {
        List<MonthlyCosts> monthlyCosts = monthlyCostsRepository.findByUserId(userId);

//        if (monthlyCosts.isEmpty())
//            throw new MonthlyCostsNotFoundException("No monthly costs found for user with id " + userId);

        for (MonthlyCosts monthlyCost : monthlyCosts)
            if (Objects.equals(monthlyCost.getId(), monthlyCostsId)) {
                return monthlyCost.getRent() + monthlyCost.getFoodCosts() +
                        monthlyCost.getCurrentElectricityBill() + monthlyCost.getCurrentGasBill();
            }
        throw new MonthlyCostsNotFoundException("No monthly costs found with id " + monthlyCostsId + " for user " + userId);
    }

}
