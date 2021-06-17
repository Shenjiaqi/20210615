package com.demo.calculator;

import com.demo.calculator.evaluator.Evaluator;
import com.demo.calculator.expression.ExpressionStack;
import com.demo.calculator.parser.Tokenizer;
import com.demo.calculator.parser.impl.RPNParser;

public class CalculatorFactory {
    /**
     * Create a RPN calculator.
     * executor use the naive one (with out multithreading)
     * @return
     */
    public static Calculator RPNCalculator() {
        Calculator calculator = new Calculator(new Tokenizer(),
                                                new RPNParser(),
                                                new Evaluator(),
                                                new ExpressionStack());
        return calculator;
    }
}
