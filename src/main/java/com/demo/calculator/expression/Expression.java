package com.demo.calculator.expression;

import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.operator.OperatorBase;

import java.util.List;

public class Expression {
    private final OperatorBase operator; // operator of this expression.
    protected List<Expression> operateList; // ordered list of operate.
    protected boolean evaluated = false; // record if already evaluated.
    protected ExpressionResultBase expressionResult; // record result if already evaluated.

    public Expression(OperatorBase operator, List<Expression> operateList) {
        this.operator = operator;
        this.operateList = operateList;
    }

    public List<Expression> getOperateList() {
        return this.operateList;
    }

    /**
     * Evaluate current expression.
     * This function should not define here.
     * For implementation simplicity.
     * @return an ExpressionResult evaluated.
     */
    public ExpressionResultBase evaluate() throws ExpressionEvaluationException {
        if (!evaluated) {
            evaluated = true;

            for (Expression expression: getOperateList()) {
                expression.evaluate();
            }

            try {
                expressionResult = operator.operateOn(getOperateList());
            } catch (ExpressionEvaluationException e) {
                expressionResult = new ExpressionResultBase();
                expressionResult.setEvaluationException(e);
            }
        }

        if (expressionResult.getEvaluationException() != null)
            throw expressionResult.getEvaluationException();

        return expressionResult;
    }

    public boolean evaluated() {
        return evaluated;
    }
}
