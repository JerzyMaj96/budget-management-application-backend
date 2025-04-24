package com.jerzymaj.budgetmanagement.budget_management_app.DTOs;

public class MonthlyCostsSummaryWithAdviceDTO extends MonthlyCostsSummaryDTO {
    private String financialAdvice;

    public String getFinancialAdvice() { return financialAdvice; }
    public void setFinancialAdvice(String financialAdvice) { this.financialAdvice = financialAdvice; }
}
