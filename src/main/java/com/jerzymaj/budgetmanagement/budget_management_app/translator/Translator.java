package com.jerzymaj.budgetmanagement.budget_management_app.translator;

import com.jerzymaj.budgetmanagement.budget_management_app.DTOs.UserDTO;
import com.jerzymaj.budgetmanagement.budget_management_app.models.User;

public class Translator {

    public static UserDTO convertUserToDto(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getNetSalary());
    }
}
