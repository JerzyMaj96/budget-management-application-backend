package com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories;

import com.jerzymaj.budgetmanagement.budget_management_app.costs.MonthlyCostsResults;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlyCostsResultsRepository extends JpaRepository<MonthlyCostsResults, Integer> {

       Optional<MonthlyCostsResults> findByMonthlyCostsId(int monthlyCostsIdd);
}
