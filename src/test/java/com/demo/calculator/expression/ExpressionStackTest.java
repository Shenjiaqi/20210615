package com.demo.calculator.expression;

import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.operator.impl.DummyExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExpressionStackTest extends ExpressionStack {

    void testPushAndPop(int pushNum, int popNum) {
        for (int i = 0; i < pushNum; ++i)
            push(new DummyExpression("" + i));

        for (int i = 0; i < popNum; ++i)
            pop();
    }

    @Test
    void testClone() {
        testPushAndPop(10, 5);
        Assertions.assertEquals(5, size());

        ExpressionStack oldExpressionStack = clone();

        // pop all from this
        testPushAndPop(0, 5);
        Assertions.assertEquals(0, size());
        Assertions.assertEquals(5, oldExpressionStack.size());
    }

    @Test
    void testThrowOnEmpty() {
        Assertions.assertThrows(ExpressionEvaluationException.class, () -> {
            testPushAndPop(0, 1000);
        });
    }
}