package com.demo.calculator.parser;

import com.demo.calculator.exception.InvalidOperatorException;
import com.demo.calculator.token.Token;
import com.demo.calculator.token.TokenLocation;
import com.demo.calculator.operator.OperatorBase;
import com.demo.calculator.operator.OperatorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A naive implementation.
 * we can use lex or flex to generate a good one.
 */
public class Tokenizer {
    /**
     * Generate tokens from input byte.
     * throw exception if an error occured, caller can get already generated operators from tokens
     * @param inputByte input string.
     * @param lineNumberOffset line of this input, used to show debug info.
     * @param tokens return already converted operators.
     * @throws InvalidOperatorException if unknown token exists in input string.
     */
    public void getToken(byte[] inputByte, int lineNumberOffset, List<OperatorBase> tokens) throws InvalidOperatorException {
        tokens.clear();
        for (int i = 0; i < inputByte.length; ) {
            // skip empty character
            if (isSeperator(inputByte[i])) {
                // pass new line
                if (isNewLine(inputByte[i])) {
                    ++lineNumberOffset;
                }
                ++i;
                continue;
            }

            // collect all non-empty
            int j = i + 1;
            while (j < inputByte.length && !isSeperator(inputByte[j])) {
                ++j;
            }

            TokenLocation tokenLocation = new TokenLocation(lineNumberOffset, i, j - i);
            tokens.add(OperatorFactory.fromToken(new Token(inputByte, tokenLocation)));

            i = j;
        }
    }

    private boolean isNewLine(byte c)
    {
        return c == '\n';
    }

    private boolean isSeperator(byte c)
    {
        return c == ' ' || c == '\t' || isNewLine(c);
    }
}
