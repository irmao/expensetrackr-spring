package com.vdias.expensetrckr.service;

import com.vdias.expensetrckr.model.Expense;
import com.vdias.expensetrckr.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class that provide services related to the {@link Expense} model.
 */
@Service
public class ExpenseService {

    /**
     * Instance of {@link ExpenseRepository}.
     */
    @Autowired
    private ExpenseRepository expenseRepository;

    /**
     * Returns a list with all expenses.
     *
     * @return All expenses
     */
    public List<Expense> findExpenses() {
        return expenseRepository.findAll();
    }
}
