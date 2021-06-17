package com.demo.calculator.exception;

/**
 * Thrown when evaulating expression.
 * * div by zero.
 * * sqrt neg number and expect a real number.
 */
public class ExpressionEvaluationException extends InvalidOperatorException {

    public ExpressionEvaluationException(String message) {
        super(message);
    }

    public ExpressionEvaluationException(String message, Exception e) {
        super(message, e);
    }
}
