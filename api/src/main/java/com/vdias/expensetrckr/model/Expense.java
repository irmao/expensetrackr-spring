package com.vdias.expensetrckr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Entity class that represent an expense.
 */
@Entity
public class Expense {
    @Id
    private long id;
    private LocalDateTime date;
    private ExpenseType expenseType;
    private String description;
    private double value;

    /**
     * Default constructor.
     */
    public Expense() {
        // JPA requires a default constructor
    }

    /**
     * Initializer constructor.
     *
     * @param pId          the id
     * @param pDate        the date
     * @param pExpenseType the expense type
     * @param pDescription the description
     * @param pValue       the value
     */
    public Expense(final long pId, final LocalDateTime pDate, final ExpenseType pExpenseType,
                   final String pDescription, final double pValue) {
        this.id = pId;
        this.date = pDate;
        this.expenseType = pExpenseType;
        this.description = pDescription;
        this.value = pValue;
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
