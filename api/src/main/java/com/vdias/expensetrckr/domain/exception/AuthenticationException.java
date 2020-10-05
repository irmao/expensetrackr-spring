package com.vdias.expensetrckr.domain.exception;

/**
 * Application exception that should be threw when an authentication exception occurs.
 */
public class AuthenticationException extends ApplicationException {
    /**
     * Initializes a new instance of the {@link AuthenticationException} class.
     *
     * @param reason the exception reason
     */
    public AuthenticationException(final ApplicationExceptionReason reason) {
        super(reason);
    }

    /**
     * Initializes a new instance of the {@link AuthenticationException} class.
     *
     * @param reason the exception reason
     * @param cause  the throwable that caused this exception to be raised
     */
    public AuthenticationException(final ApplicationExceptionReason reason, final Throwable cause) {
        super(reason, cause);
    }
}
