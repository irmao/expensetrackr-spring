package com.vdias.expensetrckr.api.controller;

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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
        List<Expense> expenses = asList(new Expense(1, LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00),
                new Expense(2, LocalDateTime.now(), ExpenseType.BILL, "water", 90.00),
                new Expense(3, LocalDateTime.now(), ExpenseType.BILL, "internet", 160.00));

        when(expenseService.findExpenses()).thenReturn(expenses);

        // test and assert
        mockMvc.perform(get("/expenses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }
}
