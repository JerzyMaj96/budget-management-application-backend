package com.jerzymaj.budgetmanagement.budget_management_app.costs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jerzymaj.budgetmanagement.budget_management_app.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity(name = "monthly_costs")
public class MonthlyCosts {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Positive
    private double rent;

    @NotNull
    @Positive
    private double foodCosts;

    @NotNull
    @Positive
    private double currentElectricityBill;

    @NotNull
    @Positive
    private double currentGasBill;

    private double MonthlyCostsSum; // NEED TO FINISH !!!

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public MonthlyCosts() {
    }

    public MonthlyCosts(double rent, double foodCosts, double currentElectricityBill, double currentGasBill) {
        this.rent = rent;
        this.foodCosts = foodCosts;
        this.currentElectricityBill = currentElectricityBill;
        this.currentGasBill = currentGasBill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getFoodCosts() {
        return foodCosts;
    }

    public void setFoodCosts(double foodCosts) {
        this.foodCosts = foodCosts;
    }

    public double getCurrentElectricityBill() {
        return currentElectricityBill;
    }

    public void setCurrentElectricityBill(double currentElectricityBill) {
        this.currentElectricityBill = currentElectricityBill;
    }

    public double getCurrentGasBill() {
        return currentGasBill;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCurrentGasBill(double currentGasBill) {
        this.currentGasBill = currentGasBill;
    }

    public double getMonthlyCostsSum() {
        return MonthlyCostsSum;
    }

    public void setMonthlyCostsSum(double monthlyCostsSum) {
        MonthlyCostsSum = monthlyCostsSum;
    }

    @Override
    public String toString() {
        return "MonthlyCosts{" +
                "rent=" + rent +
                ", avgFoodCosts=" + foodCosts +
                ", currentElectricityBill=" + currentElectricityBill +
                ", currentGasBill=" + currentGasBill +
                '}';
    }

}
