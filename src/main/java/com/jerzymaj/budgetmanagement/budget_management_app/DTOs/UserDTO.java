package com.jerzymaj.budgetmanagement.budget_management_app.DTOs;

public class UserDTO {

    private Long id;
    private String name;
    private double netSalary;

    public UserDTO(Long id, String name, double netSalary) {
        this.id = id;
        this.name = name;
        this.netSalary = netSalary;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getNetSalary() { return netSalary; }
}
