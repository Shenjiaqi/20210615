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

class UndoOperatorTest {
    private UndoOperator undoOperator;
    private Token token = new Token(" undo blah ".getBytes(),
            new TokenLocation(1, 1, 4));

    @BeforeEach
    void fromToken() {
        undoOperator = UndoOperator.fromToken(token);
        Assertions.assertNotNull(undoOperator);
    }

    @Test
    void fromTokenWithWrongKeyWord() {
        Token wrongToken = new Token("undd".getBytes(),
                new TokenLocation(1, 0, 4));
        Assertions.assertNull(ClearOperator.fromToken(wrongToken));
    }
    @Test
    void numOfOperate() {
        Assertions.assertEquals(1, undoOperator.NumOfOperate());
    }

    @Test
    void createNewExpression() {
        // check empty dummy expression should return empty new expression
        List<Expression> oneExpressionsList = Arrays.asList(new DummyExpression());

        Assertions.assertTrue(undoOperator.CreateNewExpression(oneExpressionsList).isEmpty());

        // return operate of expression as new exprssion
        // order of operate is preserved
        DummyExpression d = new DummyExpression();
        int operateNum = 20;
        for (int i = 0; i < operateNum; ++i)
            d.addOperate(new DummyExpression("" + i));
        oneExpressionsList = Arrays.asList(d);
        List<Expression> r = undoOperator.CreateNewExpression(oneExpressionsList);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(operateNum, r.size());
        for (int i = 0; i < operateNum; ++i)
        {
            Assertions.assertEquals("" + i, ((DummyExpression)r.get(i)).name);
        }

    }

    // operateOn should never called
    @Test
    void operateOn() {
        Assertions.assertThrows(ExpressionEvaluationException.class, () -> {
            undoOperator.operateOn(List.of(new DummyExpression()));
        });
    }
}