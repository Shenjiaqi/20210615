package com.demo.calculator.expression;

import com.demo.calculator.exception.ExpressionEvaluationException;

public class ExpressionStack {

    int stackSize = 0; // record current stack size.
    private Node currentNode = null; // record top stack node.

    public ExpressionStack() {

    }

    private ExpressionStack(ExpressionStack oth) {
        this.stackSize = oth.stackSize;
        this.currentNode = oth.currentNode;
    }

    /**
     * Use list of node represent stack
     */
    class Node {
        Node previousNode;
        Expression expression;
        protected Node(Node previousNode, Expression expression) {
            this.previousNode = previousNode;
            this.expression = expression;
        }
    }

    /**
     * @return Current expression num on stack.
     */
    public int size() {
        return stackSize;
    }

    public Expression top() {
        if (size() == 0)
            throw new ExpressionEvaluationException("no more expression on stack");
        return currentNode.expression;
    }

    /**
     * Get top expression out of stack
     * @return null if no expression left on the stack.
     */
    public Expression pop() {
        if (size() == 0) {
            throw new ExpressionEvaluationException("no more expression on stack");
        }
        --stackSize;
        Node n = currentNode;
        currentNode = currentNode.previousNode;
        return n.expression;
    }

    /**
     * Push a new expresson on top of stack.
     * @param expression
     */
    public void push(Expression expression) {
        ++stackSize;
        Node n = new Node(currentNode, expression);
        currentNode = n;
    }

    /**
     * Clone a new expressionStack same as this one.
     * One can clone a new expressionStack, manipulate new one without
     * destroy old with
     * O(1) complex.
     * @return new created expressionStack
     */
    public ExpressionStack clone() {
        return new ExpressionStack(this);
    }
}
