package com.demo.calculator.token;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenLocationTest {

    @Test
    void testToString() {
        TokenLocation tokenLocation = new TokenLocation(10, 1, 13);
        Assertions.assertEquals("10:1,13", tokenLocation.toString());
    }
}