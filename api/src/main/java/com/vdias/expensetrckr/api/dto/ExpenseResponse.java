package com.vdias.expensetrckr.api.dto;

import com.vdias.expensetrckr.model.Expense;
import com.vdias.expensetrckr.model.ExpenseType;

import java.time.LocalDateTime;

/**
 * Represents a response given by the controller endpoints with {@link com.vdias.expensetrckr.model.Expense} information.
 */
public class ExpenseResponse {
    private long id;
    private LocalDateTime date;
    private ExpenseType expenseType;
    private String description;
    private double value;

    /**
     * Default constructor.
     */
    public ExpenseResponse() {
    } 

    /**
     * Builds a new instance using the given {@link Expense} data.
     * @param expense
     */
    public ExpenseResponse(final Expense expense) {
        this.id = expense.getId();
        this.date = expense.getDate();
        this.expenseType = expense.getExpenseType();
        this.description = expense.getDescription();
        this.value = expense.getValue();
    }

    public long getId() {
        return id;
    }

    public void setId(final long pId) {
        id = pId;
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
