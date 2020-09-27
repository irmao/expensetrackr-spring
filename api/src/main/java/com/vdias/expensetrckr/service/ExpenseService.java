package com.vdias.expensetrckr.service;

import com.vdias.expensetrckr.api.dto.ExpenseCreateRequest;
import com.vdias.expensetrckr.model.Expense;
import com.vdias.expensetrckr.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class that provide services related to the {@link com.vdias.expensetrckr.model.Expense} model.
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

    /**
     * Creates and saves a new {@link Expense} using the given request data.
     *
     * @param pRequest the data to create the expense
     * @return the create expense
     */
    public Expense createExpense(final ExpenseCreateRequest pRequest) {
        return expenseRepository.save(build(pRequest));
    }

    private static Expense build(final ExpenseCreateRequest pRequest) {
        Expense expense = new Expense();

        expense.setDate(pRequest.getDate());
        expense.setDescription(pRequest.getDescription());
        expense.setExpenseType(pRequest.getExpenseType());
        expense.setValue(pRequest.getValue());

        return expense;
    }
}
