package com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories;

import com.jerzymaj.budgetmanagement.budget_management_app.costs.MonthlyCosts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MonthlyCostsRepository extends JpaRepository<MonthlyCosts, Long> {

    Optional<MonthlyCosts> findByUserId(Long userId);

}
