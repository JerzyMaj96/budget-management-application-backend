package com.jerzymaj.budgetmanagement.budget_management_app.repositories;

import com.jerzymaj.budgetmanagement.budget_management_app.models.MonthlyCostsSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlyCostsSummaryRepository extends JpaRepository<MonthlyCostsSummary, Integer> {

    Optional<MonthlyCostsSummary> findByMonthlyCostsId(Long monthlyCostsId);
}
