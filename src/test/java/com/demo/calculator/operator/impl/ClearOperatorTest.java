package com.demo.calculator.operator.impl;

import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.expression.Expression;
import com.demo.calculator.token.Token;
import com.demo.calculator.token.TokenLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ClearOperatorTest {
    private ClearOperator clearOperator;
    private Token token = new Token("blah clear ".getBytes(),
                                    new TokenLocation(1, 5, 5));

    @BeforeEach
    void fromToken() {
        clearOperator = ClearOperator.fromToken(token);
        Assertions.assertNotNull(clearOperator);
    }

    @Test
    void fromTokenWithWrongKeyWord() {
        Token wrongToken = new Token("nuclear".getBytes(),
                new TokenLocation(1, 0, 5));
        Assertions.assertNull(ClearOperator.fromToken(wrongToken));
    }
    @Test
    void numOfOperate() {
        Assertions.assertEquals(-1, clearOperator.NumOfOperate());
    }

    // always return empty expression list
    @Test
    void createNewExpression() {
        List<Expression> emptyExpressionsList = Arrays.asList(new DummyExpression());
        List<Expression> oneExpressionsList = Arrays.asList(new DummyExpression());
        List<Expression> twoExpressionsList = Arrays.asList(new DummyExpression(),
                new DummyExpression());
        Assertions.assertTrue(clearOperator.CreateNewExpression(emptyExpressionsList).isEmpty());
        Assertions.assertTrue(clearOperator.CreateNewExpression(oneExpressionsList).isEmpty());
        Assertions.assertTrue(clearOperator.CreateNewExpression(twoExpressionsList).isEmpty());
    }

    // operateOn should never called
    @Test
    void operateOn() {
        Assertions.assertThrows(ExpressionEvaluationException.class, () -> {
            clearOperator.operateOn(List.of(new DummyExpression()));
        });
    }
}