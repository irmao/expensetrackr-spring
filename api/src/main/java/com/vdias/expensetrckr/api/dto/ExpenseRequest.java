package com.vdias.expensetrckr.api.dto;

import com.vdias.expensetrckr.api.validation.OnCreate;
import com.vdias.expensetrckr.api.validation.OnUpdate;
import com.vdias.expensetrckr.model.ExpenseType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * Represents the request for creating or updating {@link com.vdias.expensetrckr.model.Expense} instances via API controller.
 */
public class ExpenseRequest {
    private static final String MIN_EXPENSE_VALUE = "0.01";

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private ExpenseType expenseType;

    @NotBlank
    private String description;

    @DecimalMin(value = MIN_EXPENSE_VALUE)
    private double value;

    /**
     * Default constructor.
     */
    public ExpenseRequest() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime pDate) {
        date = pDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long pId) {
        id = pId;
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
