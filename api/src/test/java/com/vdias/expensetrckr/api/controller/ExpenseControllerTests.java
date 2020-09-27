package com.vdias.expensetrckr.api.controller;

import com.vdias.expensetrckr.api.dto.ExpenseCreateRequest;
import com.vdias.expensetrckr.model.Expense;
import com.vdias.expensetrckr.model.ExpenseType;
import com.vdias.expensetrckr.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class ExpenseControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Test
    public void getExpenses() throws Exception {
        // mock data
        Expense mockExpense = new Expense(1, LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);

        when(expenseService.findExpenses()).thenReturn(asList(mockExpense));

        // test and assert
        mockMvc.perform(get("/expenses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(mockExpense.getId()))
                .andExpect(jsonPath("$[0].date").isNotEmpty())
                .andExpect(jsonPath("$[0].description").value(mockExpense.getDescription()))
                .andExpect(jsonPath("$[0].expenseType").value(mockExpense.getExpenseType().toString()))
                .andExpect(jsonPath("$[0].value").value(mockExpense.getValue()));
    }

    @Test
    public void createExpenseWithSuccess() throws Exception {
        // mock data
        Expense mockExpense = new Expense(1, LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);

        when(expenseService.createExpense(any(ExpenseCreateRequest.class))).thenReturn(mockExpense);

        // test and assert
        mockMvc.perform(post("/expenses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(mockExpense.getId()))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.description").value(mockExpense.getDescription()))
                .andExpect(jsonPath("$.expenseType").value(mockExpense.getExpenseType().toString()))
                .andExpect(jsonPath("$.value").value(mockExpense.getValue()));
    }
}
