package com.demo.calculator.tool;

import com.demo.calculator.Calculator;
import com.demo.calculator.CalculatorFactory;
import com.demo.calculator.exception.ExpressionEvaluationException;
import com.demo.calculator.exception.InsufficientParameterException;
import com.demo.calculator.exception.InvalidOperatorException;
import com.demo.calculator.expression.ExpressionStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A command line tool wrapper for RPNCalculator.
 */

public class CommandLineTool {
    private static void printHelp() {
        printHelpShort();
        printExample();
    }

    private static void printExample() {
        System.out.println("Some examples: ");
        System.out.println("Example 1.");
        System.out.println("> 5 2");
        System.out.println("> stack: 5 2");

        System.out.println("Example 2.");
        System.out.println("> 2 sqrt");
        System.out.println("> stack: 1.4142135623");
        System.out.println("> clear 9 sqrt");
        System.out.println("> stack: 3");
    }

    private static void printHelpShort() {
        System.out.println("This is a RPNCalculator.");
        System.out.println("Try to use it to do real number math!");
        System.out.println("Here is supported oeprator: +, -, *, /, sqrt, undo, clear.");
    }

    public static void main(String[] args) {
        printHelp();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Calculator calculator = CalculatorFactory.RPNCalculator();

        boolean printInput = false; // whether print input string before evaluation.
        if (System.getenv("PRINT_INPUT") != null)
            printInput = true;

        while (true) {

            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                break;
            }

            if (line == null) {
                // EOF
                System.out.println("Bye.");
                break;
            }

            if (line.isEmpty())
                continue;

            if (line.getBytes()[0] == '#') {
                // leading # means a comment
                continue;
            }

            if (printInput)
                System.out.println(line);

            try {
                calculator.calculate(line, 0);
            } catch (InsufficientParameterException e) {
                System.out.println(e.getMessage());
                printHelpShort();
            } catch (ExpressionEvaluationException e) {
                System.out.println(e.getMessage() + " evaluation failed.");
                printHelpShort();
            } catch (InvalidOperatorException e) {
                System.out.println(e.getMessage());
            } finally {
                printExpressionStack(calculator.getExpressionStack());
            }
        }
    }

    private static void printExpressionStack(ExpressionStack r) {
        System.out.print("stack: ");
        List<String> resultStrStack = new ArrayList<>();
        while (r.size() > 0) {
            resultStrStack.add(r.pop().evaluate().str());
        }
        Collections.reverse(resultStrStack);
        for (String result: resultStrStack)
            System.out.print(result + " ");
        System.out.println();
    }
}
