package com.demo.calculator.operator.impl;

import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.exception.InvalidOperatorException;
import com.demo.calculator.expression.Expression;
import com.demo.calculator.expression.ExpressionResultBase;
import com.demo.calculator.expression.ExpressionStack;
import com.demo.calculator.expression.impl.RealNumberExpressionResult;
import com.demo.calculator.token.Token;
import com.demo.calculator.operator.OperatorBase;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RealNumberMathOperator extends OperatorBase {
    private final OPType opType;

    @Override
    public int NumOfOperate() {
        return opType.GetOperateNum();
    }

    enum OPType {
        PLUS("+", 2),
        SUB("-", 2),
        MUL("*", 2),
        DIV("/", 2),
        SQRT("sqrt", 1);

        private final int operateNum;
        private String keyStr;
        private OPType(String keyStr, int operateNum) {
            this.keyStr = keyStr;
            this.operateNum = operateNum;
        }
        public String getKeyStr() {
            return keyStr;
        }

        public int GetOperateNum() {
            return this.operateNum;
        }
    };

    private static final List<OPType> allOpList = Arrays.asList(
            OPType.PLUS, OPType.SUB, OPType.MUL, OPType.DIV, OPType.SQRT
    );

    private RealNumberMathOperator(OPType opType, Token token) {
        super(token);
        this.opType = opType;
    }

    /**
     * try to create a new RealNumberMathOperator from s[offset, offset+len]
     * @param token input token
     * @return RealNumberMathOperator if succ, else return a null pointer
     */
    public static RealNumberMathOperator fromToken(Token token)
    {
        for (OPType opType: allOpList) {
            if (token.keyStrEqual(opType.getKeyStr().getBytes())) {
                return new RealNumberMathOperator(opType, token);
            }
        }
        return null;
    }

    @Override
    public List<Expression> CreateNewExpression(List<Expression> operateList) {
        return Arrays.asList(new Expression(this, operateList));
    }

    @Override
    public ExpressionResultBase operateOn(List<Expression> operateList) {
        if (operateList.size() != this.opType.operateNum) {
            throw new InvalidOperatorException("operateList.size(): " + operateList.size() +
                    " expected operated num: " + this.opType.operateNum);
        }

        List<RealNumberExpressionResult> resultList = new ArrayList<>();
        for (Expression expression: operateList) {
            ExpressionResultBase expressionResult = expression.evaluate();
            if (expressionResult instanceof RealNumberExpressionResult) {
                resultList.add((RealNumberExpressionResult) expressionResult);
            }
        }
        BigDecimal operateLeft = resultList.get(0).getValue();
        BigDecimal result;

        try {
            if (opType == OPType.PLUS) {
                result = operateLeft.add(resultList.get(1).getValue());
            } else if (opType == OPType.SUB) {
                result = operateLeft.subtract(resultList.get(1).getValue());
            } else if (opType == OPType.MUL) {
                result = operateLeft.multiply(resultList.get(1).getValue());
            } else if (opType == OPType.DIV) {
                result = operateLeft.divide(resultList.get(1).getValue());
            } else if (opType == OPType.SQRT) {
                result = operateLeft.sqrt(MathContext.DECIMAL128); // TODO add configure.
            } else {
                throw new InvalidOperatorException(DebugInfo());
            }
        } catch (ArithmeticException e) {
            throw new ExpressionEvaluationException(DebugInfo(), e);
        }

        return new RealNumberExpressionResult(result);
    }
}
