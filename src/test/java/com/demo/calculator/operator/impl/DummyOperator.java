package com.demo.calculator.operator.impl;


import com.demo.calculator.expression.Expression;
import com.demo.calculator.expression.ExpressionResultBase;
import com.demo.calculator.operator.OperatorBase;
import com.demo.calculator.token.Token;
import com.demo.calculator.token.TokenLocation;

import java.util.List;

class DummyOperator extends OperatorBase {

    public DummyOperator() {
        super(new Token(" ".getBytes(), new TokenLocation(0, 0, 1)));
    }

    @Override
    public ExpressionResultBase operateOn(List<Expression> expressionList) {
        for (Expression expression: expressionList)
            expression.evaluate();
        return new ExpressionResultBase();
    }
}
