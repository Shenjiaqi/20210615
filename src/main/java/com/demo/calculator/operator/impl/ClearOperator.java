package com.demo.calculator.operator.impl;

import com.demo.calculator.expression.Expression;
import com.demo.calculator.operator.OperatorBase;
import com.demo.calculator.token.Token;

import java.util.Collections;
import java.util.List;

/**
 * "clear" operator clear all operator on the calculator stack.
 */
public class ClearOperator extends OperatorBase {
    private static byte[] key = "clear".getBytes();

    private ClearOperator(Token token) {
        super(token);
    }

    public static ClearOperator fromToken(Token token)
    {
        if (token.keyStrEqual(key)) {
            return new ClearOperator(token);
        }
        return null;
    }

    @Override
    public int NumOfOperate() {
        return -1;
    }

    /**
     * Remove all expression on the stack.
     * @param operateList
     * @return
     */
    @Override
    public List<Expression> CreateNewExpression(List<Expression> operateList) {
        return Collections.emptyList();
    }

}
