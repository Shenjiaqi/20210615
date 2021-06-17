package com.demo.calculator.exception;

/**
 * Thrown when operator expects more operate than that on the stack.
 */
public class InsufficientParameterException extends IllegalArgumentException {
    public InsufficientParameterException(String message) {
        super(message);
    }
}
