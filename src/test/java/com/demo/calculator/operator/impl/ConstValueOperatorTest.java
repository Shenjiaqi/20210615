package com.demo.calculator.operator.impl;

import com.demo.calculator.expression.Expression;
import com.demo.calculator.expression.ExpressionResultBase;
import com.demo.calculator.token.Token;
import com.demo.calculator.token.TokenLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ConstValueOperatorTest {

    private ConstValueOperator constValue7, constValue0Point7, constValue7x14, constValueNeg0Point7x14;
    private Token constValue7Token = new Token("7 ".getBytes(),
            new TokenLocation(1, 0, 1));
    private Token constValue0Point7Token = new Token(" .7 ".getBytes(),
            new TokenLocation(1, 1, 2));
    private Token constValueNeg0Point7x14Token = new Token(" -.77777777777777 ".getBytes(),
            new TokenLocation(1, 1, 16));
    private Token constValue7x14Token = new Token(" 77777777777777 ".getBytes(),
            new TokenLocation(1, 1, 14));

    @BeforeEach
    void fromToken() {
        constValue7 = ConstValueOperator.fromToken(constValue7Token);
        constValue0Point7 = ConstValueOperator.fromToken(constValue0Point7Token);
        constValue7x14 = ConstValueOperator.fromToken(constValue7x14Token);
        constValueNeg0Point7x14 = ConstValueOperator.fromToken(constValueNeg0Point7x14Token);

        Assertions.assertNotNull(constValue7);
        Assertions.assertNotNull(constValue0Point7);
        Assertions.assertNotNull(constValue7x14);
        Assertions.assertNotNull(constValueNeg0Point7x14);
    }

    @Test
    void fromInvalidToken() {
        // test invalid input
        List<String> invalidTokenStrList = Arrays.asList(
                " ",
                "0.0.0",
                "--1",
                "-3-",
                "1ee3"
        );
        for (String invalidTokeStr: invalidTokenStrList) {
            Token invalidToken = new Token(invalidTokeStr.getBytes(),
                    new TokenLocation(1, 0, invalidTokeStr.length()));
            ConstValueOperator dummy = ConstValueOperator.fromToken(invalidToken);
            Assertions.assertNull(dummy);
        }
    }

    @Test
    void numOfOperate() {
        Assertions.assertEquals(0, constValue7.NumOfOperate());
        Assertions.assertEquals(0, constValue0Point7.NumOfOperate());
        Assertions.assertEquals(0, constValue7x14.NumOfOperate());
        Assertions.assertEquals(0, constValueNeg0Point7x14.NumOfOperate());
    }

    // always return an expression corresponds to given value
    @Test
    void createNewExpression() {
        List<Expression> emptyExpressionsList = Arrays.asList(new DummyExpression());
        List<Expression> oneExpressionsList = Arrays.asList(new DummyExpression());
        List<Expression> twoExpressionsList = Arrays.asList(new DummyExpression(),
                new DummyExpression());
        Assertions.assertEquals(1, constValue7.CreateNewExpression(emptyExpressionsList).size());
        Assertions.assertEquals(1, constValue0Point7.CreateNewExpression(oneExpressionsList).size());
        Assertions.assertEquals(1, constValue7x14.CreateNewExpression(oneExpressionsList).size());
        Assertions.assertEquals(1, constValueNeg0Point7x14.CreateNewExpression(twoExpressionsList).size());
    }

    @Test
    void operateOn() {
        ExpressionResultBase result = constValue7.operateOn(Collections.emptyList());
        Assertions.assertEquals("7", result.str());
        result = constValue0Point7.operateOn(Collections.emptyList());
        Assertions.assertEquals("0.7", result.str());
        result = constValue7x14.operateOn(Collections.emptyList());
        Assertions.assertEquals("77777777777777", result.str());
        result = constValueNeg0Point7x14.operateOn(Collections.emptyList());
        Assertions.assertEquals("-0.77777777777777", result.str());
    }
}