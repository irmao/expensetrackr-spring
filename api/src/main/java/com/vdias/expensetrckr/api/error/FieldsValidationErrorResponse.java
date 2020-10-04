package com.vdias.expensetrckr.api.error;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a response object containing a list of validation errors.
 */
public class FieldsValidationErrorResponse {
    /**
     * The list of validation errors.
     */
    private final List<FieldValidationError> validationErrors;

    /**
     * Default constructor.
     */
    public FieldsValidationErrorResponse() {
        this.validationErrors = new ArrayList<>();
    }

    /**
     * Returns the list of validation errors.
     *
     * @return the list of validation errors
     */
    public List<FieldValidationError> getValidationErrors() {
        return validationErrors;
    }

    /**
     * Adds a validation error to the response.
     *
     * @param error the validation error to be added
     * @return the response itself
     */
    public FieldsValidationErrorResponse addValidationError(final FieldValidationError error) {
        this.validationErrors.add(error);
        return this;
    }
}
