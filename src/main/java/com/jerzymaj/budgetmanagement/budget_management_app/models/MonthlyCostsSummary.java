package com.jerzymaj.budgetmanagement.budget_management_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "monthly_costs_results")
public class MonthlyCostsSummary {

    @Id
    @GeneratedValue
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_costs_id", unique = true)
    @JsonIgnore
    private MonthlyCosts monthlyCosts;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime deleteDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    public MonthlyCostsSummary(){}

    public MonthlyCostsSummary(int id, Double monthlyCostsSum, BigDecimal rentPercentageOfUserSalary,
                               BigDecimal foodCostsPercentageOfUserSalary,
                               BigDecimal currentElectricityBillPercentageOfUserSalary, BigDecimal currentGasBillPercentageOfUserSalary,
                               BigDecimal totalCarServicePercentageOfUserSalary, BigDecimal carInsuranceCostsPercentageOfUserSalary,
                               BigDecimal carOperatingCostsPercentageOfUserSalary, BigDecimal costsPercentageOfUserSalary,
                               BigDecimal netSalaryAfterCosts) {
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

    public MonthlyCosts getMonthlyCosts() {
        return monthlyCosts;
    }

    public void setMonthlyCosts(MonthlyCosts monthlyCosts) {
        this.monthlyCosts = monthlyCosts;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public String toString() {
        return "MonthlyCostsSummary{" +
                "id=" + id +
                ", monthlyCostsSum=" + monthlyCostsSum +
                ", rentPercentageOfUserSalary=" + rentPercentageOfUserSalary +
                ", foodCostsPercentageOfUserSalary=" + foodCostsPercentageOfUserSalary +
                ", currentElectricityBillPercentageOfUserSalary=" + currentElectricityBillPercentageOfUserSalary +
                ", currentGasBillPercentageOfUserSalary=" + currentGasBillPercentageOfUserSalary +
                ", costsPercentageOfUserSalary=" + costsPercentageOfUserSalary +
                ", netSalaryAfterCosts=" + netSalaryAfterCosts +
                ", monthlyCosts=" + monthlyCosts +
                ", createDate=" + createDate +
                ", deleteDate=" + deleteDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
