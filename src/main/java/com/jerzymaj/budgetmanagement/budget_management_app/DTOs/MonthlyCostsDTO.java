package com.jerzymaj.budgetmanagement.budget_management_app.DTOs;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MonthlyCostsDTO {

    private Long id;
    private Double rent;
    private Double foodCosts;
    private Double currentElectricityBill;
    private Double currentGasBill;
    private Double totalCarServiceCosts;
    private Double carInsuranceCosts;
    private Double carOperatingCosts;
    private LocalDateTime createDate;

    public MonthlyCostsDTO(Long id, Double rent, Double foodCosts, Double currentElectricityBill,
                           Double currentGasBill, Double totalCarServiceCosts, Double carInsuranceCosts,
                           Double carOperatingCosts, LocalDateTime createDate) {
        this.id = id;
        this.rent = rent;
        this.foodCosts = foodCosts;
        this.currentElectricityBill = currentElectricityBill;
        this.currentGasBill = currentGasBill;
        this.totalCarServiceCosts = totalCarServiceCosts;
        this.carInsuranceCosts = carInsuranceCosts;
        this.carOperatingCosts = carOperatingCosts;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Double getFoodCosts() {
        return foodCosts;
    }

    public void setFoodCosts(Double foodCosts) {
        this.foodCosts = foodCosts;
    }

    public Double getCurrentElectricityBill() {
        return currentElectricityBill;
    }

    public void setCurrentElectricityBill(Double currentElectricityBill) {
        this.currentElectricityBill = currentElectricityBill;
    }

    public Double getCurrentGasBill() {
        return currentGasBill;
    }

    public void setCurrentGasBill(Double currentGasBill) {
        this.currentGasBill = currentGasBill;
    }

    public Double getTotalCarServiceCosts() {
        return totalCarServiceCosts;
    }

    public void setTotalCarServiceCosts(Double totalCarServiceCosts) {
        this.totalCarServiceCosts = totalCarServiceCosts;
    }

    public Double getCarInsuranceCosts() {
        return carInsuranceCosts;
    }

    public void setCarInsuranceCosts(Double carInsuranceCosts) {
        this.carInsuranceCosts = carInsuranceCosts;
    }

    public Double getCarOperatingCosts() {
        return carOperatingCosts;
    }

    public void setCarOperatingCosts(Double carOperatingCosts) {
        this.carOperatingCosts = carOperatingCosts;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
