package com.jerzymaj.budgetmanagement.budget_management_app.jpa_repositories;

import com.jerzymaj.budgetmanagement.budget_management_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
