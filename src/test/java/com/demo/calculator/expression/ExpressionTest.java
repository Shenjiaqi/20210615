package com.demo.calculator.expression;

import com.demo.calculator.evaluator.Evaluator;
import com.demo.calculator.operator.impl.DummyExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest extends Evaluator {

    /**
     * crate an expression tree, and call evaluate
     * expect all node in the tree are evaluated.
     */
    @Test
    void testEvaluate() {
        List<Expression> allExpressionList = new ArrayList<>();
        List<Expression> expressionList = new ArrayList<>();
        for (int i = 0; i < 100; ++i)
            expressionList.add(new DummyExpression("" + i));
        allExpressionList.addAll(expressionList);

        while (expressionList.size() > 1)
        {
            List<Expression> combinedExpressionList = new ArrayList<>();
            // combine two expression into one
            for (int i = 0; i < expressionList.size(); i += 2)
            {
                DummyExpression combinedExpression = new DummyExpression("");
                combinedExpression.addOperate(expressionList.get(i));
                if (i + 1 < expressionList.size())
                    combinedExpression.addOperate(expressionList.get(i+1));
                combinedExpressionList.add(combinedExpression);
            }
            expressionList = combinedExpressionList;
            allExpressionList.addAll(expressionList);
        }

        evaluate(expressionList);

        for (Expression expression: allExpressionList)
            Assertions.assertTrue(expression.evaluated());
    }
}