package com.demo.calculator.operator.impl;

import com.demo.calculator.expression.Expression;
import com.demo.calculator.expression.ExpressionResultBase;
import com.demo.calculator.expression.ExpressionStack;
import com.demo.calculator.expression.impl.RealNumberExpressionResult;
import com.demo.calculator.token.Token;
import com.demo.calculator.operator.OperatorBase;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ConstValueOperator extends OperatorBase {
    private final BigDecimal val; // internal value

    private ConstValueOperator(BigDecimal val, Token token) {
        super(token);
        this.val = val;
    }

    /**
     * try to create a ConstValueOperator from s[offset, offset + len - 1]
     * @param token
     * @return ConstValueOperator if succ, else return a null pointer
     */
    public static ConstValueOperator fromToken(Token token)
    {
        try {
            BigDecimal value = new BigDecimal(token.keyStr());
            return new ConstValueOperator(value, token);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public int NumOfOperate() {
        return 0;
    }

    @Override
    public List<Expression> CreateNewExpression(List<Expression> operateList) {
        // Expect operationList is empty
        return List.of(new Expression(this, Collections.emptyList()));
    }

    @Override
    public ExpressionResultBase operateOn(List<Expression> operateList) {
        return new RealNumberExpressionResult(val);
    }
}
