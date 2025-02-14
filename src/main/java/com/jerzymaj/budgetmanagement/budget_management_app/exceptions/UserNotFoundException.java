package com.jerzymaj.budgetmanagement.budget_management_app.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
