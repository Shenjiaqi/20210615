package com.demo.calculator.parser.impl;

import com.demo.calculator.expression.ExpressionStack;
import com.demo.calculator.expression.Expression;
import com.demo.calculator.exception.InsufficientParameterException;
import com.demo.calculator.operator.OperatorBase;
import com.demo.calculator.parser.IParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RPNParser implements IParser {
    @Override
    public void parse(List<OperatorBase> operatorList, ExpressionStack state) {
        for (OperatorBase operator: operatorList) {
            // get number of operate needed
            int operateNeeded = operator.NumOfOperate();
            if (state.size() < operateNeeded)
                throw new InsufficientParameterException(operator.DebugInfo() + ": insufficient parameters.");

            // pop expression as operate.
            List<Expression> operateList = popOperateFromExpressionState(state, operateNeeded);

            // push new expression.
            List<Expression> newExpressionList = operator.CreateNewExpression(operateList);
            for (Expression newExpression: newExpressionList)
                state.push(newExpression);
        }
    }

    private List<Expression> popOperateFromExpressionState(ExpressionStack state, int operateNeeded) {
        List<Expression> operateList = new ArrayList<>();

        if (operateNeeded > 0)
            for (int i = 0; i < operateNeeded; ++i) {
                operateList.add(state.pop());
            }
        else if (operateNeeded < 0)
            // pop until no more than |operateNeened| operate left.
            while (state.size() + operateNeeded >= 0) {
                operateList.add(state.pop());
            }

        // make sure order is preserved.
        // smaller index in operateList is lower expression on the stack
        Collections.reverse(operateList);
        return operateList;
    }
}
