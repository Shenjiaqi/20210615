package com.demo.calculator.operator;

import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.expression.Expression;
import com.demo.calculator.expression.ExpressionResultBase;
import com.demo.calculator.expression.ExpressionStack;
import com.demo.calculator.token.Token;

import java.util.List;

public class OperatorBase {
    public OperatorBase(Token token) {
        this.token = token;
    }
    /**
     * Operator must know how much operate it need.
     * @return number of operate this operator expects.
     * if @return >= 0, means need exactly @return operate.
     * if @return < 0, means need pop operate until @return - 1 left.
     */
    public int NumOfOperate() {
        return 0;
    }

    public String GetName() {
        return this.token.keyStr();
    }

    public String DebugInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("operator ")
                .append(GetName())
                .append(" (position: ")
                .append(token.tokenLocation.toString())
                .append(")");
        return stringBuffer.toString();
    }

    protected Token token;

    /**
     * Given a list of operated, return a new list of expression
     * Most operator, return 1 expression.
     * @param operateList
     * @return
     */
    public List<Expression> CreateNewExpression(List<Expression> operateList) {
        throw new UnsupportedOperationException();
    }

    /**
     * Given a list of operator
     * calculate result.
     *
     * @param operateList
     * @return
     */
    public ExpressionResultBase operateOn(List<Expression> operateList) throws ExpressionEvaluationException {
        throw new ExpressionEvaluationException("operate base cannot used to evaluate.");
    }
}
