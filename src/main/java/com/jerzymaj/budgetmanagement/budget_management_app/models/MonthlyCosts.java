package com.jerzymaj.budgetmanagement.budget_management_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "monthly_costs")
public class MonthlyCosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Positive
    private Double totalCarServiceCosts;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Positive
    private Double carInsuranceCosts;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Positive
    private Double carOperatingCosts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime deleteDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;

    public MonthlyCosts() {}

    public MonthlyCosts(double rent, double foodCosts, double currentElectricityBill, double currentGasBill,
                        double totalCarServiceCosts, double carInsuranceCosts, double carOperatingCosts) {
        this.rent = rent;
        this.foodCosts = foodCosts;
        this.currentElectricityBill = currentElectricityBill;
        this.currentGasBill = currentGasBill;
        this.totalCarServiceCosts = totalCarServiceCosts;
        this.carInsuranceCosts = carInsuranceCosts;
        this.carOperatingCosts = carOperatingCosts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setCurrentGasBill(double currentGasBill) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
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

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "MonthlyCosts{" +
                "id=" + id +
                ", rent=" + rent +
                ", foodCosts=" + foodCosts +
                ", currentElectricityBill=" + currentElectricityBill +
                ", currentGasBill=" + currentGasBill +
                ", totalCarServiceCosts=" + totalCarServiceCosts +
                ", carInsuranceCosts=" + carInsuranceCosts +
                ", carOperatingCosts=" + carOperatingCosts +
                ", user=" + user +
                ", createDate=" + createDate +
                ", deleteDate=" + deleteDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
