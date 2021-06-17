package com.demo.calculator.parser;

import com.demo.calculator.expression.ExpressionStack;
import com.demo.calculator.exception.InsufficientParameterException;
import com.demo.calculator.exception.InvalidOperatorException;
import com.demo.calculator.operator.OperatorBase;

import java.util.List;

public interface IParser {
    /**
     * Create a new Expression from state and input tokens
     * @param operatorList input operators
     * @param stack represents pending expression to be used.
     *              stack may be changed in the process of parse.
     *              old expression may be poped. A new one can be push to stack.
     *              caller should preserve old state if needed.
     *              If exception occurs, caller can still use already push expression in stack.
     * @return
     * @throws InvalidOperatorException throws if need more parameters needed to create an expression.
     *  For example:
     *  a RPN parser expects two pending parameters before multiply operator appears.
     *  "1 2 *" is ok, but "1 *" will throw an InvalidParameterException.
     */
    void parse(List<OperatorBase> operatorList, ExpressionStack stack) throws InsufficientParameterException;
}
