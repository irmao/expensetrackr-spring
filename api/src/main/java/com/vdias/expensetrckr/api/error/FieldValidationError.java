package com.vdias.expensetrckr.api.error;

/**
 * Represents a validation error on a field.
 */
public class FieldValidationError {
    /**
     * The field name.
     */
    private final String fieldName;

    /**
     * The error message.
     */
    private final String message;

    /**
     * Builds a new instance of the {@link FieldValidationError} object with the given information.
     *
     * @param pFieldName the field name
     * @param pMessage   the error message
     */
    public FieldValidationError(final String pFieldName, final String pMessage) {
        this.fieldName = pFieldName;
        this.message = pMessage;
    }

    /**
     * Returns the field name.
     *
     * @return the field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Returns the validation error message.
     *
     * @return the validation error message
     */
    public String getMessage() {
        return message;
    }
}
