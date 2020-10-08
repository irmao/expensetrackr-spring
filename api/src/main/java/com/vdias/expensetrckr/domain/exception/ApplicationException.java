package com.vdias.expensetrckr.domain.exception;

/**
 * Base class for the possible exceptions caused by business rules violations in the application.
 */
public abstract class ApplicationException extends RuntimeException {
    /**
     * The reason for the exception.
     */
    private final ApplicationExceptionReason reason;

    /**
     * Initializes a new instance of the {@link ApplicationException} class.
     *
     * @param pReason the reason of the exception.
     */
    protected ApplicationException(final ApplicationExceptionReason pReason) {
        this.reason = pReason;
    }

    /**
     * Initializes a new instance of the {@link ApplicationException} class.
     *
     * @param pReason the reason of the exception.
     * @param cause   throwable cause.
     */
    protected ApplicationException(final ApplicationExceptionReason pReason, final Throwable cause) {
        super(cause);
        this.reason = pReason;
    }

    /**
     * Returns the reason.
     *
     * @return the reason.
     */
    public ApplicationExceptionReason getReason() {
        return reason;
    }
}
