package com.jerzymaj.budgetmanagement.budget_management_app.exceptions;

public class ExistingUserException extends RuntimeException {
    public ExistingUserException(String message) {
        super(message);
    }
}
