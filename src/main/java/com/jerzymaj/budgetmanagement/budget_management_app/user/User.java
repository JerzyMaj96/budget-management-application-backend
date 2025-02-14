package com.jerzymaj.budgetmanagement.budget_management_app.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jerzymaj.budgetmanagement.budget_management_app.costs.MonthlyCosts;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity(name = "user_details")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 5, message = "Name should have at least 5 characters")
    private String name;

    @Positive
    private double grossSalary;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnore
    private MonthlyCosts monthlyCosts;


    public User (){}

    public User(Long id, String name, double grossSalary) {
        this.id = id;
        this.name = name;
        this.grossSalary = grossSalary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public MonthlyCosts getMonthlyCosts() {
        return monthlyCosts;
    }

    public void setMonthlyCosts(MonthlyCosts monthlyCosts) {
        this.monthlyCosts = monthlyCosts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grossSalary=" + grossSalary +
                '}';
    }
}
