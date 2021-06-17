package com.demo.calculator.operator.impl;

import com.demo.calculator.expression.Expression;
import com.demo.calculator.expression.ExpressionResultBase;
import com.demo.calculator.operator.OperatorBase;
import com.demo.calculator.token.Token;
import com.demo.calculator.token.TokenLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy class for test only
 */
public class DummyExpression extends Expression {
    public String name;

    public DummyExpression() {
        this("");
    }

    public DummyExpression(String name) {
        super(new DummyOperator(), new ArrayList<>());
        this.name = name;
    }

    public void addOperate(Expression expression) {
        operateList.add(expression);
    }

}
