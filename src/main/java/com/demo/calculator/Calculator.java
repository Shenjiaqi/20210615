package com.demo.calculator;

import com.demo.calculator.evaluator.Evaluator;
import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.exception.InsufficientParameterException;
import com.demo.calculator.exception.InvalidOperatorException;
import com.demo.calculator.expression.Expression;
import com.demo.calculator.expression.ExpressionStack;
import com.demo.calculator.operator.OperatorBase;
import com.demo.calculator.parser.IParser;
import com.demo.calculator.parser.Tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Calculator {
    private final IParser parser;
    private final Tokenizer tokenizer;
    private final Evaluator evaluator;
    ExpressionStack expressionStack;

    public Calculator(Tokenizer tokenizer,
                      IParser parser,
                      Evaluator evaluator,
                      ExpressionStack expressionStack) {
        this.tokenizer = tokenizer;
        this.parser = parser;
        this.evaluator = evaluator;
        this.expressionStack = expressionStack;
    }

    /**
     *
     * @param inputString
     * @param lineInfo
     * @return
     */
    public ExpressionStack calculate(String inputString, int lineInfo) throws ExpressionEvaluationException,
            InsufficientParameterException, InvalidOperatorException {
        InvalidOperatorException invalidOperatorException = null;
        List<OperatorBase> tokens = new ArrayList<>();
        // tokenize
        try {
            tokenizer.getToken(inputString.getBytes(), lineInfo, tokens);
        } catch (InvalidOperatorException e) {
            invalidOperatorException = e;
        }

        // parse
        ExpressionStack newExpressionStack = this.expressionStack.clone();
        InsufficientParameterException insufficientParameterException = null;
        try {
            parser.parse(tokens, newExpressionStack);
        } catch (InsufficientParameterException e) {
            insufficientParameterException = e;
        }

        // pick not evaluated expression
        List<Expression> expressionToEvaluate = new ArrayList<>();
        while (newExpressionStack.size() > 0) {
            if (newExpressionStack.top().evaluated())
                break;
            expressionToEvaluate.add(newExpressionStack.pop());
        }
        // make sure order of expression pushed is same as popped
        Collections.reverse(expressionToEvaluate);
        evaluator.evaluate(expressionToEvaluate);
        for (Expression expression : expressionToEvaluate)
            if (expression.evaluated()) {
                ExpressionEvaluationException expressionEvaluationException = expression.evaluate().getEvaluationException();
                if (expressionEvaluationException != null) {
                    throw expressionEvaluationException;
                }
                newExpressionStack.push(expression);
            } else {
                throw new ExpressionEvaluationException("expression not evaluated");
            }

        // update current expression stack
        expressionStack = newExpressionStack;
        if (insufficientParameterException != null) {
            throw insufficientParameterException;
        } else if (invalidOperatorException != null)
            throw invalidOperatorException;

        return expressionStack.clone();
    }

    /**
     * Get current expression stack.
     * @return caller can call pop / push on returned value
     */
    public ExpressionStack getExpressionStack() {
        return expressionStack.clone();
    }
}
