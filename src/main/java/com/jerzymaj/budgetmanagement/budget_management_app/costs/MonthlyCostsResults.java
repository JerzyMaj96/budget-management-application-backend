package com.jerzymaj.budgetmanagement.budget_management_app.costs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jerzymaj.budgetmanagement.budget_management_app.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "monthly_costs_results")
public class MonthlyCostsResults {

    @Id
    @GeneratedValue
    private int id;

    private double monthlyCostsSum;

    private BigDecimal costsPercentageOfUserSalary;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private MonthlyCosts monthlyCosts;

    public MonthlyCostsResults(){}

    public MonthlyCostsResults(int id, double monthlyCostsSum, BigDecimal costsPercentageOfUserSalary) {
        this.id = id;
        this.monthlyCostsSum = monthlyCostsSum;
        this.costsPercentageOfUserSalary = costsPercentageOfUserSalary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonthlyCostsSum() {
        return monthlyCostsSum;
    }

    public void setMonthlyCostsSum(double monthlyCostsSum) {
        this.monthlyCostsSum = monthlyCostsSum;
    }

    public BigDecimal getCostsPercentageOfUserSalary() {
        return costsPercentageOfUserSalary;
    }

    public void setCostsPercentageOfUserSalary(BigDecimal costsPercentageOfUserSalary) {
        this.costsPercentageOfUserSalary = costsPercentageOfUserSalary;
    }

    public MonthlyCosts getMonthlyCosts() {
        return monthlyCosts;
    }

    public void setMonthlyCosts(MonthlyCosts monthlyCosts) {
        this.monthlyCosts = monthlyCosts;
    }

    @Override
    public String toString() {
        return "MonthlyCostsResults{" +
                "costsPercentageOfUserSalary=" + costsPercentageOfUserSalary +
                ", monthlyCostsSum=" + monthlyCostsSum +
                ", id=" + id +
                '}';
    }
}
