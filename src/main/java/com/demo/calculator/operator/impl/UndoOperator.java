package com.demo.calculator.operator.impl;

import com.demo.calculator.expression.Expression;
import com.demo.calculator.operator.OperatorBase;
import com.demo.calculator.token.Token;

import java.util.List;

public class UndoOperator extends OperatorBase {
    private static byte[] key = "undo".getBytes();

    private UndoOperator(Token token) {
        super(token);
    }

    public static UndoOperator fromToken(Token token)
    {
        if (token.keyStrEqual(key)) {
            return new UndoOperator(token);
        }
        return null;
    }

    @Override
    public int NumOfOperate() {
        return 1;
    }

    /**
     * return operated of top expression.
     * @param operateList
     * @return
     */
    @Override
    public List<Expression> CreateNewExpression(List<Expression> operateList) {
        // TODO expect operateList.size() == 1
        return operateList.get(0).getOperateList();
    }
}
