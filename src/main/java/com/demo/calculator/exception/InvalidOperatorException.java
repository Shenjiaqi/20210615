package com.demo.calculator.exception;

/**
 * Thrown when operation not supported.
 */
public class InvalidOperatorException extends IllegalArgumentException {

    public InvalidOperatorException(String message) {
        super(message);
    }

    public InvalidOperatorException(String message, Exception e) {
        super(message, e);
    }
}
