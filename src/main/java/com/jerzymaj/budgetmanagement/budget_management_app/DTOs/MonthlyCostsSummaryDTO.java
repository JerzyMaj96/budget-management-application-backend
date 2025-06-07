package com.jerzymaj.budgetmanagement.budget_management_app.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MonthlyCostsSummaryDTO {

    private int id;
    private Double monthlyCostsSum;
    private BigDecimal rentPercentageOfUserSalary;
    private BigDecimal foodCostsPercentageOfUserSalary;
    private BigDecimal currentElectricityBillPercentageOfUserSalary;
    private BigDecimal currentGasBillPercentageOfUserSalary;
    private BigDecimal totalCarServicePercentageOfUserSalary;
    private BigDecimal carInsuranceCostsPercentageOfUserSalary;
    private BigDecimal carOperatingCostsPercentageOfUserSalary;
    private BigDecimal costsPercentageOfUserSalary;
    private BigDecimal netSalaryAfterCosts;
    private LocalDateTime createDate;

    public MonthlyCostsSummaryDTO() {}

    public MonthlyCostsSummaryDTO(int id, Double monthlyCostsSum, BigDecimal rentPercentageOfUserSalary,
                                  BigDecimal foodCostsPercentageOfUserSalary,
                                  BigDecimal currentElectricityBillPercentageOfUserSalary,
                                  BigDecimal currentGasBillPercentageOfUserSalary,
                                  BigDecimal totalCarServicePercentageOfUserSalary,
                                  BigDecimal carInsuranceCostsPercentageOfUserSalary,
                                  BigDecimal carOperatingCostsPercentageOfUserSalary,
                                  BigDecimal costsPercentageOfUserSalary,
                                  BigDecimal netSalaryAfterCosts,
                                  LocalDateTime createDate) {
        this.id = id;
        this.monthlyCostsSum = monthlyCostsSum;
        this.rentPercentageOfUserSalary = rentPercentageOfUserSalary;
        this.foodCostsPercentageOfUserSalary = foodCostsPercentageOfUserSalary;
        this.currentElectricityBillPercentageOfUserSalary = currentElectricityBillPercentageOfUserSalary;
        this.currentGasBillPercentageOfUserSalary = currentGasBillPercentageOfUserSalary;
        this.totalCarServicePercentageOfUserSalary = totalCarServicePercentageOfUserSalary;
        this.carInsuranceCostsPercentageOfUserSalary = carInsuranceCostsPercentageOfUserSalary;
        this.carOperatingCostsPercentageOfUserSalary = carOperatingCostsPercentageOfUserSalary;
        this.costsPercentageOfUserSalary = costsPercentageOfUserSalary;
        this.netSalaryAfterCosts = netSalaryAfterCosts;
        this.createDate = createDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getMonthlyCostsSum() {
        return monthlyCostsSum;
    }

    public void setMonthlyCostsSum(Double monthlyCostsSum) {
        this.monthlyCostsSum = monthlyCostsSum;
    }

    public BigDecimal getRentPercentageOfUserSalary() {
        return rentPercentageOfUserSalary;
    }

    public void setRentPercentageOfUserSalary(BigDecimal rentPercentageOfUserSalary) {
        this.rentPercentageOfUserSalary = rentPercentageOfUserSalary;
    }

    public BigDecimal getFoodCostsPercentageOfUserSalary() {
        return foodCostsPercentageOfUserSalary;
    }

    public void setFoodCostsPercentageOfUserSalary(BigDecimal foodCostsPercentageOfUserSalary) {
        this.foodCostsPercentageOfUserSalary = foodCostsPercentageOfUserSalary;
    }

    public BigDecimal getCurrentElectricityBillPercentageOfUserSalary() {
        return currentElectricityBillPercentageOfUserSalary;
    }

    public void setCurrentElectricityBillPercentageOfUserSalary(BigDecimal currentElectricityBillPercentageOfUserSalary) {
        this.currentElectricityBillPercentageOfUserSalary = currentElectricityBillPercentageOfUserSalary;
    }

    public BigDecimal getCurrentGasBillPercentageOfUserSalary() {
        return currentGasBillPercentageOfUserSalary;
    }

    public void setCurrentGasBillPercentageOfUserSalary(BigDecimal currentGasBillPercentageOfUserSalary) {
        this.currentGasBillPercentageOfUserSalary = currentGasBillPercentageOfUserSalary;
    }

    public BigDecimal getTotalCarServicePercentageOfUserSalary() {
        return totalCarServicePercentageOfUserSalary;
    }

    public void setTotalCarServicePercentageOfUserSalary(BigDecimal totalCarServicePercentageOfUserSalary) {
        this.totalCarServicePercentageOfUserSalary = totalCarServicePercentageOfUserSalary;
    }

    public BigDecimal getCarInsuranceCostsPercentageOfUserSalary() {
        return carInsuranceCostsPercentageOfUserSalary;
    }

    public void setCarInsuranceCostsPercentageOfUserSalary(BigDecimal carInsuranceCostsPercentageOfUserSalary) {
        this.carInsuranceCostsPercentageOfUserSalary = carInsuranceCostsPercentageOfUserSalary;
    }

    public BigDecimal getCarOperatingCostsPercentageOfUserSalary() {
        return carOperatingCostsPercentageOfUserSalary;
    }

    public void setCarOperatingCostsPercentageOfUserSalary(BigDecimal carOperatingCostsPercentageOfUserSalary) {
        this.carOperatingCostsPercentageOfUserSalary = carOperatingCostsPercentageOfUserSalary;
    }

    public BigDecimal getCostsPercentageOfUserSalary() {
        return costsPercentageOfUserSalary;
    }

    public void setCostsPercentageOfUserSalary(BigDecimal costsPercentageOfUserSalary) {
        this.costsPercentageOfUserSalary = costsPercentageOfUserSalary;
    }

    public BigDecimal getNetSalaryAfterCosts() {
        return netSalaryAfterCosts;
    }

    public void setNetSalaryAfterCosts(BigDecimal netSalaryAfterCosts) {
        this.netSalaryAfterCosts = netSalaryAfterCosts;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
