package com.vdias.expensetrckr.api.dto;

/**
 * Represents a validation error on a field.
 */
public class ValidationError {
    /**
     * The field name.
     */
    private final String fieldName;

    /**
     * The error message.
     */
    private final String message;

    /**
     * Builds a new instance of the {@link ValidationError} object with the given information.
     *
     * @param pFieldName the field name
     * @param pMessage   the error message
     */
    public ValidationError(final String pFieldName, final String pMessage) {
        this.fieldName = pFieldName;
        this.message = pMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}
