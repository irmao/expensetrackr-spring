package com.vdias.expensetrckr.test.util;

import com.vdias.expensetrckr.api.dto.ExpenseRequest;
import com.vdias.expensetrckr.model.ExpenseType;

import java.time.LocalDateTime;

public class ExpenseTestUtils {
    /**
     * Default constructor.
     */
    private ExpenseTestUtils() {
    }

    public static ExpenseRequest buildExpenseRequest(final LocalDateTime date, final ExpenseType expenseType,
                                                     final String description, final double value) {
        ExpenseRequest request = new ExpenseRequest();

        request.setDate(date);
        request.setExpenseType(expenseType);
        request.setDescription(description);
        request.setValue(value);

        return request;
    }
}
