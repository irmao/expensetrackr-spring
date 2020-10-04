package com.vdias.expensetrckr.api.controller.advice;

import com.vdias.expensetrckr.api.error.ApiError;
import com.vdias.expensetrckr.api.error.FieldValidationError;
import com.vdias.expensetrckr.api.error.FieldsValidationErrorResponse;
import com.vdias.expensetrckr.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

/**
 * Controller Advice to handle exceptions threw by the Controller methods.
 */
@ControllerAdvice
public class ErrorHandlingControllerAdvice {
    /**
     * Captures the {@link ConstraintViolationException} threw instances and converts them into a ValidationErrorResponse
     * object with the list of violations. Returns a BAD REQUEST response with those violations in the body.
     * The exception is threw by SpringBoot when validations from field annotations are violated.
     *
     * @param exception the exception containing information to be converted into an error response
     * @return the body of the error response, containing the list of errors.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    FieldsValidationErrorResponse onConstraintViolationException(final ConstraintViolationException exception) {
        FieldsValidationErrorResponse response = new FieldsValidationErrorResponse();

        exception.getConstraintViolations().forEach(violation ->
                response.addValidationError(new FieldValidationError(violation.getPropertyPath().toString(), violation.getMessage())));

        return response;
    }

    /**
     * Captures the {@link MethodArgumentNotValidException} threw instances and converts them into a ValidationErrorResponse
     * object with the list of violations. Returns a BAD REQUEST response with those violations in the body.
     * The exception is threw by SpringBoot when validations from method parameters are violated.
     *
     * @param exception the exception containing information to be converted into an error response
     * @return the body of the error response, containing the list of errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    FieldsValidationErrorResponse onMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        FieldsValidationErrorResponse response = new FieldsValidationErrorResponse();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                response.addValidationError(new FieldValidationError(error.getField(), error.getDefaultMessage())));

        return response;
    }

    /**
     * Captures the {@link EntityNotFoundException} threw instances and converts them into a {@link ApiError} response
     * with the exception cause for the message in body and a NOT FOUND status code.
     *
     * @param exception the exception containing information to be converted into an error response
     * @return the body of the error response, containing the error message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ApiError onEntityNotFoundException(final EntityNotFoundException exception) {
        return new ApiError(exception.getReason().toString());
    }
}
