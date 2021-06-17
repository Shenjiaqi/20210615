package com.demo.calculator.token;

import java.util.Arrays;

public class Token {
    public Token(byte[] keyStr, TokenLocation tokenLocation) {
        this.keyStr = keyStr;
        this.tokenLocation = tokenLocation;
        if (tokenLocation.columnOffset >= keyStr.length ||
            tokenLocation.columnOffset + tokenLocation.tokenLen > keyStr.length)
            throw new IndexOutOfBoundsException(tokenLocation.toString() + "cannot fit in " + keyStr);
    }

    public byte[] keyStr; // original input str
    public TokenLocation tokenLocation; // location into str

    /**
     * check token str equal to key
     * @param key key to compare
     * @return true if equals
     */
    public boolean keyStrEqual(byte[] key) {
        return Arrays.equals(keyStr, tokenLocation.columnOffset,
                    tokenLocation.columnOffset + tokenLocation.tokenLen,
                            key, 0, key.length);
    }

    public String keyStr() {
        return new String(keyStr, tokenLocation.columnOffset, tokenLocation.tokenLen);
    }

}
