package com.jerzymaj.budgetmanagement.budget_management_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public MonthlyCosts(double rent, double foodCosts, double currentElectricityBill, double currentGasBill) {
        this.rent = rent;
        this.foodCosts = foodCosts;
        this.currentElectricityBill = currentElectricityBill;
        this.currentGasBill = currentGasBill;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", createDate=" + createDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", deleteDate=" + deleteDate +
                '}';
    }
}
