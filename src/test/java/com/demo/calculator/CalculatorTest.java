package com.demo.calculator;

import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.exception.InsufficientParameterException;
import com.demo.calculator.expression.ExpressionResultBase;
import com.demo.calculator.expression.ExpressionStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    Calculator calculator;

    @BeforeEach
    void createCalculator() {
        calculator = CalculatorFactory.RPNCalculator();
    }

    private void assertExpressionStackTopValue(String expectedValue,
                                               ExpressionStack r)
    {
        Assertions.assertTrue(r.size() > 0);
        Assertions.assertEquals(expectedValue, r.top().evaluate().str());
    }

    @Test
    void testRPNMath() {
        // (3)
        assertExpressionStackTopValue("3", calculator.calculate("1 2 +", 0));

        // (12)
        assertExpressionStackTopValue("12", calculator.calculate("4 * ", 2));

        // (12 3)
        assertExpressionStackTopValue("3", calculator.calculate(" 9  3 / ", 3));

        // (4)
        assertExpressionStackTopValue("4", calculator.calculate("  / ", 4));

        // (2)
        assertExpressionStackTopValue("2", calculator.calculate("  sqrt ", 5));
    }

    @Test
    void testInsufficientParameter() {
        // (2)
        assertExpressionStackTopValue("2", calculator.calculate("1 2 *", 0));

        // assert throw
        Assertions.assertThrows(InsufficientParameterException.class, () -> {
            calculator.calculate("2 2 * * *", 1);
        });

        // 8 still on the stack
        assertExpressionStackTopValue("8", calculator.calculate("0 + ", 0));
    }


    /**
     * div zeor or sqrt(-1) cause exception.
     * check stack after exception occurred,
     * calculator should drop all change of last calculation.
     */
    @Test
    void testEvaluationException() {
        // (1 0)
        assertExpressionStackTopValue("0", calculator.calculate("1 0 / undo", 0));

        // last (1 0 /) cause exception, all previous result abandoned
        Assertions.assertThrows(ExpressionEvaluationException.class, () -> {
            calculator.calculate("1 1 + 2 2 + 3 3 + 1 0 /", 1);
        });

        // (1 0)
        assertExpressionStackTopValue("1", calculator.calculate("+", 0));
    }

}