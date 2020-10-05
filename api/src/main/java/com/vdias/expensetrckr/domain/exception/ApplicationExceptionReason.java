package com.vdias.expensetrckr.domain.exception;

/**
 * Enum listing all the possible reasons for a custom exception.
 */
public enum ApplicationExceptionReason {
    EXPENSE_NOT_FOUND,
    USER_NOT_FOUND,
    BAD_CREDENTIALS,
    INACTIVE_USER
}
