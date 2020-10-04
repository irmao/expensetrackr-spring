package com.vdias.expensetrckr.api.controller;

import com.vdias.expensetrckr.api.dto.ExpenseRequest;
import com.vdias.expensetrckr.api.dto.ExpenseResponse;
import com.vdias.expensetrckr.api.validation.OnCreate;
import com.vdias.expensetrckr.api.validation.OnUpdate;
import com.vdias.expensetrckr.domain.service.ExpenseService;
import com.vdias.expensetrckr.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * API Controller class that expose endpoints related to the {@link com.vdias.expensetrckr.model.Expense} model.
 */
@RestController
@Validated
@RequestMapping(ExpenseController.API_ENDPOINT)
public class ExpenseController {

    /**
     * The REST API endpoint related to this controller.
     */
    public static final String API_ENDPOINT = "/expenses";

    /**
     * Instance of {@link ExpenseService} class.
     */
    @Autowired
    private ExpenseService expenseService;

    /**
     * Returns a list with all expenses.
     *
     * @return a list with all expenses
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExpenseResponse> getExpenses() {
        return expenseService.findExpenses().stream().map(ExpenseResponse::new).collect(toList());
    }

    /**
     * Finds an expense by its id.
     *
     * @param id id to find
     * @return the expense
     * @throws com.vdias.expensetrckr.domain.exception.EntityNotFoundException if the id is not found
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseResponse getById(@Valid @NotNull @PathVariable final Long id) {
        return new ExpenseResponse(expenseService.findById(id));
    }

    /**
     * Creates a new expense with the given dto.
     *
     * @param dto the information to create the expense
     * @return the newly created expense
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public String createExpense(@Valid @NotNull @RequestBody final ExpenseRequest dto) {
        Expense expense = expenseService.createExpense(dto);
        return API_ENDPOINT + "/" + expense.getId();
    }

    /**
     * Updates an existing expense with the given dto information.
     *
     * @param id  the id of the expense to be updated
     * @param dto the information to update the expense
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Validated(OnUpdate.class)
    public void updateExpense(@Valid @NotNull @PathVariable final Long id, @Valid @NotNull @RequestBody final ExpenseRequest dto) {
        expenseService.updateExpense(id, dto);
    }

    /**
     * Deletes an existing expense.
     *
     * @param id the id of the expense to be deleted
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@Valid @NotNull @PathVariable final Long id) {
        expenseService.deleteExpense(id);
    }
}
