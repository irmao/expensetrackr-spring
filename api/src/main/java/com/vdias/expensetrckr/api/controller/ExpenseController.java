package com.vdias.expensetrckr.api.controller;

import com.sun.istack.NotNull;
import com.vdias.expensetrckr.api.dto.ExpenseCreateRequest;
import com.vdias.expensetrckr.api.dto.ExpenseResponse;
import com.vdias.expensetrckr.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * API Controller class that expose endpoints related to the {@link com.vdias.expensetrckr.model.Expense} model.
 */
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    /**
     * Instance of {@link ExpenseService} class.
     */
    @Autowired
    private ExpenseService expenseService;

    /**
     * Returns a list with all expenses.
     *
     * @return a list with all expenses
     */
    @GetMapping
    public List<ExpenseResponse> getExpenses() {
        return expenseService.findExpenses().stream().map(ExpenseResponse::new).collect(toList());
    }

    /**
     * Creates a new expense with the given dto.
     *
     * @param dto the information to create the expense
     * @return the newly created expense
     */
    @PostMapping
    public ExpenseResponse createExpense(@NotNull final ExpenseCreateRequest dto) {
        return new ExpenseResponse(expenseService.createExpense(dto));
    }
}
