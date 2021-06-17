package com.demo.calculator.parser.impl;

import com.demo.calculator.exception.InsufficientParameterException;
import com.demo.calculator.exception.InvalidOperatorException;
import com.demo.calculator.expression.ExpressionStack;
import com.demo.calculator.operator.OperatorBase;
import com.demo.calculator.operator.OperatorFactory;
import com.demo.calculator.token.Token;
import com.demo.calculator.token.TokenLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RPNParserTest {
    RPNParser parser = new RPNParser();
    private ExpressionStack expressionStack;

    private ExpressionStack emptyExpressionStack() {
        return new ExpressionStack();
    }

    private OperatorBase fromTokenWith0OffSet(String tokenStr) {
        return OperatorFactory.fromToken(new Token(tokenStr.getBytes(),
                new TokenLocation(0, 0, tokenStr.length())));
    }

    private List<OperatorBase> fromTokenListWith0Offset(List<String> tokenStrList) {
        List<OperatorBase> operatorList = new ArrayList<>();
        for (String tokenStr: tokenStrList) {
            operatorList.add(fromTokenWith0OffSet(tokenStr));
        }
        return operatorList;
    }

    @BeforeEach
    void prepareExpressionStack() {
        expressionStack = emptyExpressionStack();
    }

    @Test
    void parseRealNumberMath() {
        // 1 is undo
        parser.parse(fromTokenListWith0Offset(Arrays.asList(
                "1", "undo", "clear", "2", "3", "+", "9", "1", "-"
        )), expressionStack);
        // ((2) (3) +) ((9) (1) -)
        Assertions.assertEquals(2, expressionStack.size());

        // ( ((2) (3) +) ((9) (1) -) /)
        parser.parse(Arrays.asList(fromTokenWith0OffSet("/")), expressionStack);
        Assertions.assertEquals(1, expressionStack.size());
    }

    @Test
    void noEnoughParameter() {
        Assertions.assertThrows(InsufficientParameterException.class, () -> {
            parser.parse(fromTokenListWith0Offset(Arrays.asList("2", "1", "3", "clear", "sqrt")), emptyExpressionStack()
            );
        });

        Assertions.assertThrows(InsufficientParameterException.class, () -> {
            parser.parse(fromTokenListWith0Offset(Arrays.asList("2", "+")), emptyExpressionStack()
            );
        });

        Assertions.assertThrows(InsufficientParameterException.class, () -> {
            parser.parse(fromTokenListWith0Offset(Arrays.asList("undo")), emptyExpressionStack()
            );
        });
    }

    @Test
    void multipleUndo() {
        parser.parse(fromTokenListWith0Offset(Arrays.asList("2", "1", "+", "5", "6", "*", "/")), expressionStack
        );
        // ( (2 1 +) (5 6 *) /)
        Assertions.assertEquals(1, expressionStack.size());

        parser.parse(fromTokenListWith0Offset(Arrays.asList("undo")), expressionStack
        );
        // (2 1 +) (5 6 *)
        Assertions.assertEquals(2, expressionStack.size());

        parser.parse(fromTokenListWith0Offset(Arrays.asList("undo")), expressionStack
        );
        // (2 1 *) 5 6
        Assertions.assertEquals(3, expressionStack.size());

        parser.parse(fromTokenListWith0Offset(Arrays.asList("undo", "undo", "undo")), expressionStack
        );
        // 2 1
        Assertions.assertEquals(2, expressionStack.size());
    }

    @Test
    void InvalidOperator() {
        Assertions.assertThrows(InvalidOperatorException.class, () -> {
            parser.parse(fromTokenListWith0Offset(Arrays.asList("2", "#")), expressionStack);
        });
    }
}