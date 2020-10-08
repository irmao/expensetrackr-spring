package com.vdias.expensetrckr.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdias.expensetrckr.api.dto.ExpenseRequest;
import com.vdias.expensetrckr.configuration.JwtTokenUtil;
import com.vdias.expensetrckr.domain.exception.EntityNotFoundException;
import com.vdias.expensetrckr.domain.service.AuthenticationService;
import com.vdias.expensetrckr.domain.service.ExpenseService;
import com.vdias.expensetrckr.model.Expense;
import com.vdias.expensetrckr.model.ExpenseType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.vdias.expensetrckr.domain.exception.ApplicationExceptionReason.EXPENSE_NOT_FOUND;
import static com.vdias.expensetrckr.test.util.ExpenseTestUtils.buildExpenseRequest;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@WithMockUser
public class ExpenseControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private ExpenseService expenseService;

    @Test
    public void getExpenses_allValid_success() throws Exception {
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
    public void getExpenseById_allValid_success() throws Exception {
        // mock data
        Expense mockExpense = new Expense(1, LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);

        when(expenseService.findById(anyLong())).thenReturn(mockExpense);

        // test and assert
        mockMvc.perform(get("/expenses/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(mockExpense.getId()))
                .andExpect(jsonPath("$.date").isNotEmpty())
                .andExpect(jsonPath("$.description").value(mockExpense.getDescription()))
                .andExpect(jsonPath("$.expenseType").value(mockExpense.getExpenseType().toString()))
                .andExpect(jsonPath("$.value").value(mockExpense.getValue()));
    }

    @Test
    public void createExpense_allValid_success() throws Exception {
        // mock data
        ExpenseRequest request = buildExpenseRequest(LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);
        Expense mockExpense = new Expense(1, request.getDate(), request.getExpenseType(), request.getDescription(), request.getValue());

        when(expenseService.createExpense(any(ExpenseRequest.class))).thenReturn(mockExpense);

        // test and assert
        mockMvc.perform(
                post("/expenses")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(jsonPath("$").value("/expenses/" + mockExpense.getId()));
    }

    @Test
    public void createExpense_invalidFields_badRequest() throws Exception {
        // mock data
        ExpenseRequest request = buildExpenseRequest(null, null, "", 0.00);

        // test and assert
        mockMvc.perform(
                post("/expenses")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.validationErrors", hasSize(4)));
    }

    @Test
    public void updateExpense_allValid_success() throws Exception {
        // mock data
        ExpenseRequest request = buildExpenseRequest(LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);
        Expense mockExpense = new Expense(1, request.getDate(), request.getExpenseType(), request.getDescription(), request.getValue());

        when(expenseService.updateExpense(anyLong(), any(ExpenseRequest.class))).thenReturn(mockExpense);

        // test and assert
        mockMvc.perform(
                put("/expenses/1")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateExpense_invalidId_notFound() throws Exception {
        // mock data
        ExpenseRequest request = buildExpenseRequest(LocalDateTime.now(), ExpenseType.BILL, "netflix", 30.00);
        Expense mockExpense = new Expense(1, request.getDate(), request.getExpenseType(), request.getDescription(), request.getValue());

        when(expenseService.updateExpense(anyLong(), any(ExpenseRequest.class))).thenThrow(new EntityNotFoundException(EXPENSE_NOT_FOUND));

        // test and assert
        mockMvc.perform(
                put("/expenses/1")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(EXPENSE_NOT_FOUND.toString()));
    }

    @Test
    public void deleteExpense_allValid_success() throws Exception {
        // mock
        doNothing().when(expenseService).deleteExpense(anyLong());

        // test and assert
        mockMvc.perform(
                delete("/expenses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteExpense_invalidId_notFound() throws Exception {
        // mock data
        doThrow(new EntityNotFoundException(EXPENSE_NOT_FOUND)).when(expenseService).deleteExpense(anyLong());

        // test and assert
        mockMvc.perform(
                delete("/expenses/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value(EXPENSE_NOT_FOUND.toString()));
    }
}
