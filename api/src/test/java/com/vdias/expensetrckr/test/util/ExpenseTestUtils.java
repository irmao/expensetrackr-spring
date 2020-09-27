package com.vdias.expensetrckr.test.util;

import com.vdias.expensetrckr.api.dto.ExpenseCreateRequest;
import com.vdias.expensetrckr.model.ExpenseType;

import java.time.LocalDateTime;

public class ExpenseTestUtils {
    /**
     * Default constructor.
     */
    private ExpenseTestUtils() {
    }

    public static ExpenseCreateRequest buildExpenseCreateRequest(final LocalDateTime date, final ExpenseType expenseType,
                                                                 final String description, final double value) {
        ExpenseCreateRequest request = new ExpenseCreateRequest();

        request.setDate(date);
        request.setExpenseType(expenseType);
        request.setDescription(description);
        request.setValue(value);

        return request;
    }
}
