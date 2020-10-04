package com.vdias.expensetrckr.domain.service;

import com.vdias.expensetrckr.api.dto.ExpenseRequest;
import com.vdias.expensetrckr.domain.exception.EntityNotFoundException;
import com.vdias.expensetrckr.model.Expense;
import com.vdias.expensetrckr.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vdias.expensetrckr.domain.exception.ApplicationExceptionReason.EXPENSE_NOT_FOUND;

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
     * Returns the expense with the given id. If not found, throws an EntityNotFoundException.
     *
     * @param id the id
     * @return the found expense
     * @throws EntityNotFoundException if the expense is not found
     */
    public Expense findById(final Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(EXPENSE_NOT_FOUND));
    }

    /**
     * Creates and saves a new {@link Expense} using the given request data.
     *
     * @param pRequest the data to create the expense
     * @return the created expense
     */
    public Expense createExpense(final ExpenseRequest pRequest) {
        return expenseRepository.save(assignFields(pRequest, new Expense()));
    }

    /**
     * Updates and saves an existing {@link Expense} using the given request data.
     *
     * @param pRequest the data to update the expense
     * @return the updated expense
     * @throws EntityNotFoundException if the expense is not found
     */
    public Expense updateExpense(final ExpenseRequest pRequest) {
        Expense expense = findById(pRequest.getId());
        return expenseRepository.save(assignFields(pRequest, expense));
    }

    /**
     * Assign the fields of the request to the given expense model. Returns the updated model.
     *
     * @param pRequest the values to assign
     * @param expense  the assign destination
     * @return the updated expense
     */
    private static Expense assignFields(final ExpenseRequest pRequest, final Expense expense) {
        expense.setDate(pRequest.getDate());
        expense.setDescription(pRequest.getDescription());
        expense.setExpenseType(pRequest.getExpenseType());
        expense.setValue(pRequest.getValue());

        return expense;
    }
}
