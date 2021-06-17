package com.demo.calculator.operator;

import com.demo.calculator.token.Token;
import com.demo.calculator.exception.InvalidOperatorException;
import com.demo.calculator.operator.impl.ClearOperator;
import com.demo.calculator.operator.impl.ConstValueOperator;
import com.demo.calculator.operator.impl.RealNumberMathOperator;
import com.demo.calculator.operator.impl.UndoOperator;

public class OperatorFactory {
    public static OperatorBase fromToken(Token token) throws InvalidOperatorException {
        OperatorBase operator = ConstValueOperator.fromToken(token);
        if (operator != null)
            return operator;

        operator = RealNumberMathOperator.fromToken(token);
        if (operator != null)
            return operator;

        operator = UndoOperator.fromToken(token);
        if (operator != null)
            return operator;

        operator = ClearOperator.fromToken(token);
        if (operator != null)
            return operator;

        throw new InvalidOperatorException("operator <" + token.keyStr() +
                "> (position: <" + token.tokenLocation.toString() + ">): Invalid operator");
    }
}
