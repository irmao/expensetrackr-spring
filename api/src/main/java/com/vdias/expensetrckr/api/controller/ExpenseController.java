package com.vdias.expensetrckr.api.controller;

import com.vdias.expensetrckr.model.Expense;
import java.util.List;

import com.vdias.expensetrckr.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API Controller class that expose endpoints related to the {@link Expense} model.
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
     * @return a list with all expenses
     */
    @GetMapping
    public List<Expense> getExpenses() {
        return expenseService.findExpenses();
    }
}
