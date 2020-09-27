package com.vdias.expensetrckr.api.dto;

import com.vdias.expensetrckr.model.ExpenseType;

import java.time.LocalDateTime;

/**
 * Represents the request for creating new {@link com.vdias.expensetrckr.model.Expense} instances via API controller.
 */
public class ExpenseCreateRequest {
    private LocalDateTime date;
    private ExpenseType expenseType;
    private String description;
    private double value;

    /**
     * Default constructor.
     */
    public ExpenseCreateRequest() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime pDate) {
        date = pDate;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(final ExpenseType pExpenseType) {
        expenseType = pExpenseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String pDescription) {
        description = pDescription;
    }

    public double getValue() {
        return value;
    }

    public void setValue(final double pValue) {
        value = pValue;
    }
}
