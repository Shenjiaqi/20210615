package com.demo.calculator.token;

/**
 * Record token info, used when error prompt.
 */
public class TokenLocation {
    public TokenLocation(int lineNumber, int columnOffset, int tokenLen) {
        this.lineNumber = lineNumber;
        this.columnOffset = columnOffset;
        this.tokenLen = tokenLen;
    }

    public String toString() {
        String s = "" + this.lineNumber + ":" + this.columnOffset + "," + (this.columnOffset + this.tokenLen - 1);
        return s;
    }

    int lineNumber; // line number of token in doc.
    int columnOffset; // begin offset(begin from 0) of token.
    int tokenLen; // length of token.
}
