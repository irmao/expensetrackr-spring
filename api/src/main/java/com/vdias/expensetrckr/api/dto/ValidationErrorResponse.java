package com.vdias.expensetrckr.api.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a response object containing a list of validation errors.
 */
public class ValidationErrorResponse {
    /**
     * The list of validation errors.
     */
    private final List<ValidationError> validationErrors;

    /**
     * Default constructor.
     */
    public ValidationErrorResponse() {
        this.validationErrors = new ArrayList<>();
    }

    /**
     * Returns the list of validation errors.
     *
     * @return the list of validation errors
     */
    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public ValidationErrorResponse addValidationError(final ValidationError error) {
        this.validationErrors.add(error);
        return this;
    }
}
