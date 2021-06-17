package com.demo.calculator.expression;

import com.demo.calculator.exception.ExpressionEvaluationException;

public class ExpressionResultBase {
    private ExpressionEvaluationException evaluationException;

    public String str() {
        throw new UnsupportedOperationException();
    };

    public void setEvaluationException(ExpressionEvaluationException e) {
        evaluationException = e;
    }

    public ExpressionEvaluationException getEvaluationException() {
        return evaluationException;
    }
}
