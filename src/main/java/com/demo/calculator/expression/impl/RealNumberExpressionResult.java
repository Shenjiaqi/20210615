package com.demo.calculator.expression.impl;

import com.demo.calculator.expression.ExpressionResultBase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Used to represents result of RealNumber result.
 */
public class RealNumberExpressionResult extends ExpressionResultBase {
    BigDecimal value;

    public RealNumberExpressionResult(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String str() {
        return value.toString(); // TODO make precisoin configurable.
    }
}
