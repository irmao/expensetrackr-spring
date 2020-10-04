package com.vdias.expensetrckr.api.error;

/**
 * Class that represents an API Error with an error message.
 */
public class ApiError {
    /**
     * The error message.
     */
    private final String message;

    /**
     * Initializes a new instance of the {@link ApiError} class.
     *
     * @param pMessage the error message
     */
    public ApiError(final String pMessage) {
        this.message = pMessage;
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return message;
    }
}
