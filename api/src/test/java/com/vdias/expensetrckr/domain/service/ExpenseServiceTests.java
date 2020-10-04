package com.vdias.expensetrckr.domain.service;

import com.vdias.expensetrckr.api.dto.ExpenseRequest;
import com.vdias.expensetrckr.domain.exception.EntityNotFoundException;
import com.vdias.expensetrckr.model.Expense;
import com.vdias.expensetrckr.model.ExpenseType;
import com.vdias.expensetrckr.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.vdias.expensetrckr.test.util.ExpenseTestUtils.buildExpenseRequest;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTests {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    public void findAll_allValid_success() {
        // mock data
        List<Expense> expenses = asList(new Expense(1, LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00),
                new Expense(2, LocalDateTime.now(), ExpenseType.BILL, "water", 90.00),
                new Expense(3, LocalDateTime.now(), ExpenseType.BILL, "internet", 160.00));

        when(expenseRepository.findAll()).thenReturn(expenses);

        // test
        List<Expense> returnedExpenses = expenseService.findExpenses();

        // assert
        assertThat(returnedExpenses).hasSize(expenses.size());
    }

    @Test
    public void findById_allValid_success() {
        // mock data
        Expense expense = new Expense(1, LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(expense));

        // test
        Expense returnedExpense = expenseService.findById(1L);

        // assert
        assertThat(returnedExpense).isNotNull();
    }

    @Test
    public void findById_invalidId_throwNotFoundException() {
        // mock data
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        // test and verify
        assertThrows(EntityNotFoundException.class, () -> expenseService.findById(1L));
    }

    @Test
    public void createExpense_allValid_success() {
        // mock data
        ExpenseRequest request = buildExpenseRequest(null, LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);
        when(expenseRepository.save(any(Expense.class)))
                .thenReturn(new Expense(1L, request.getDate(), request.getExpenseType(), request.getDescription(), request.getValue()));

        // test
        Expense expense = expenseService.createExpense(request);

        // assert
        assertThat(expense.getDate()).isEqualTo(request.getDate());
        assertThat(expense.getExpenseType()).isEqualTo(request.getExpenseType());
        assertThat(expense.getDescription()).isEqualTo(request.getDescription());
        assertThat(expense.getValue()).isEqualTo(request.getValue());

        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    public void updateExpense_allValid_success() {
        // mock data
        ExpenseRequest request = buildExpenseRequest(1L, LocalDateTime.now().plusDays(30),
                ExpenseType.DRUGSTORE, "medicine", 120.00);
        Expense dbExpense = new Expense(1L, LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(dbExpense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(dbExpense);

        // test
        Expense expense = expenseService.updateExpense(request);

        // assert
        assertThat(expense.getDate()).isEqualTo(request.getDate());
        assertThat(expense.getExpenseType()).isEqualTo(request.getExpenseType());
        assertThat(expense.getDescription()).isEqualTo(request.getDescription());
        assertThat(expense.getValue()).isEqualTo(request.getValue());

        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    public void updateExpense_invalidId_throwNotFoundException() {
        // mock data
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        // test and verify
        assertThrows(EntityNotFoundException.class, () -> expenseService.findById(1L));
    }
}
