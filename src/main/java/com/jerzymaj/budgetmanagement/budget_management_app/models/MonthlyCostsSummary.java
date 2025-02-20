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

    private double monthlyCostsSum;

    private BigDecimal costsPercentageOfUserSalary;

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

    public MonthlyCostsSummary(int id, double monthlyCostsSum, BigDecimal costsPercentageOfUserSalary) {
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
        return "MonthlyCostsResults{" +
                "costsPercentageOfUserSalary=" + costsPercentageOfUserSalary +
                ", monthlyCostsSum=" + monthlyCostsSum +
                ", id=" + id +
                ", createDate=" + createDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", deleteDate=" + deleteDate +
                '}';
    }
}
