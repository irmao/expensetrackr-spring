package com.vdias.expensetrckr.domain.exception;

/**
 * Application exception that should be threw when entities are not found.
 */
public class EntityNotFoundException extends ApplicationException {
    /**
     * Initializes a new instance of the {@link EntityNotFoundException} class.
     *
     * @param reason the exception reason
     */
    public EntityNotFoundException(final ApplicationExceptionReason reason) {
        super(reason);
    }

    /**
     * Initializes a new instance of the {@link EntityNotFoundException} class.
     *
     * @param reason the exception reason
     * @param cause  the throwable that caused this exception to be raised
     */
    public EntityNotFoundException(final ApplicationExceptionReason reason, final Throwable cause) {
        super(reason, cause);
    }
}
