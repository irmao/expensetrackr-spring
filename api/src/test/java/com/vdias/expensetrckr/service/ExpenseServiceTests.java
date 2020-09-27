package com.vdias.expensetrckr.service;

import com.vdias.expensetrckr.api.dto.ExpenseCreateRequest;
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

import static com.vdias.expensetrckr.test.util.ExpenseTestUtils.buildExpenseCreateRequest;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    public void findAllWithSuccess() {
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
    public void createExpenseWithSuccess() {
        // mock data
        ExpenseCreateRequest request = buildExpenseCreateRequest(LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);
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
}
