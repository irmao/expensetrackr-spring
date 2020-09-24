package com.vdias.expensetrckr.repository;

import com.vdias.expensetrckr.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to retrieve and store data related to the {@link Expense} model.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
