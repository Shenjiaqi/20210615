package com.demo.calculator.operator.impl;

import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.expression.Expression;
import com.demo.calculator.expression.ExpressionResultBase;
import com.demo.calculator.token.Token;
import com.demo.calculator.token.TokenLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class RealNumberMathOperatorTest {
    Map<String, RealNumberMathOperator> operatorMap = new HashMap<>();
    List<String> opStrList = List.of("+", "-", "*", "/", "sqrt");

    @BeforeEach
    void fromToken() {
        for (String opStr: opStrList) {
            Token token = new Token(opStr.getBytes(),
                    new TokenLocation(0, 0, opStr.length()));
            operatorMap.put(opStr, RealNumberMathOperator.fromToken(token));
            Assertions.assertNotNull(operatorMap.get(opStr));
        }
    }

    /**
     * except for sqrt, all op should consume two operate
     */
    @Test
    void numOfOperate() {
        for (String opStr: opStrList) {
            int opNum = operatorMap.get(opStr).NumOfOperate();
            if (opStr.equals("sqrt"))
                Assertions.assertEquals(1, opNum);
            else Assertions.assertEquals(2, opNum);
        }
    }

    /**
     * create a const value expression
     * @param val
     * @return always return a list of one item.
     */
    private List<Expression> constValueExpression(int val) {
        String valStr = "" + val;
        Token token = new Token(valStr.getBytes(), new TokenLocation(0, 0, valStr.length()));
        ConstValueOperator operate = ConstValueOperator.fromToken(token);
        return operate.CreateNewExpression(Collections.emptyList());
    }

    @Test
    void invalidOperationSqrtOnNeg() {
        List<Expression> negConstVal = constValueExpression(-1);
        Assertions.assertThrows(ExpressionEvaluationException.class, () -> {
            operatorMap.get("sqrt").operateOn(negConstVal);
        });
    }

    @Test
    void invalidOperationDivByZero() {
        List<Expression> operateList = new ArrayList<>();
        operateList.addAll(constValueExpression(1));
        operateList.addAll(constValueExpression(0));
        Assertions.assertThrows(ExpressionEvaluationException.class, () -> {
            operatorMap.get("/").operateOn(operateList);
        });
    }

    /**
     * Test all real number math on 3 and 10,
     * Assert result value and precision as expected.
     */
    @Test
    void createNewExpressionAndOperateOn() {
        // prepare expression as input
        List<Expression> twoExpressionList = new ArrayList<>();
        twoExpressionList.addAll(constValueExpression(3));
        twoExpressionList.addAll(constValueExpression(10));
        List<Expression> oneExpressionList = Arrays.asList(twoExpressionList.get(0));

        // try all math
        for (String opStr: opStrList) {
            RealNumberMathOperator operator = operatorMap.get(opStr);
            List<Expression> expressionList = twoExpressionList;
            if (operator.NumOfOperate() == 1)
                expressionList = oneExpressionList;
            ExpressionResultBase r = operator.operateOn(expressionList);

            // check result
            if (opStr.equals("+")) Assertions.assertEquals("" + (3 + 10), r.str());
            if (opStr.equals("-")) Assertions.assertEquals("" + (3 - 10), r.str());
            if (opStr.equals("*")) Assertions.assertEquals("" + (3 * 10), r.str());
            if (opStr.equals("/")) Assertions.assertEquals("" + (3 / 10.0), r.str());
            if (opStr.equals("sqrt")) {
                String expected = "" + (Math.sqrt(3.0));
                String got = r.str().substring(0, expected.length());
                // cause we used high precision implementation.
                // so we only check first characters of expected value.
                Assertions.assertEquals(expected, got);
            }
        }
    }
}