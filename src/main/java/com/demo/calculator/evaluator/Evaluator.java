package com.demo.calculator.evaluator;

import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.expression.Expression;
import com.demo.calculator.expression.ExpressionResultBase;

import java.util.List;

public class Evaluator {
    public void evaluate(List<Expression> expressionToEvaluate) {
        /**
         * TODO:
         *
         * Need to define class ExecutionPlan
         * An ExecutionPlan is a DAG(directed acyclic graph) correspond to
         * multiple steps to evaluate a list of expression.
         *
         * An EvaluatorOptimizer take an execution plan and generate an
         * optimized plan. Which may extract and combine identical expression,
         * or combine multiply evaluation steps into one such as MAC(multiply and Add calculation instruction)
         *
         * What makes it hard is when an ExecutionPlan is optimized,
         * some node of expression may be converted to a logical identical execition plan.
         * If an error occurred, the error message may cause confusing.
         * 1. convert to execution plan A
         * 2. use optimizer to optimized plan A, and get optimized plan A*
         * 3. use executor to execute plan A*
         **/

        // Following is a naive implementation which do not generate execution plan
        // only recursive walk through expression and do evaluate.
        ExpressionEvaluationException lastException;
        for (Expression expression: expressionToEvaluate) {
            ExpressionResultBase r = expression.evaluate();
            if (r.getEvaluationException() != null)
            {
                throw r.getEvaluationException();
            }
        }
    }
}
