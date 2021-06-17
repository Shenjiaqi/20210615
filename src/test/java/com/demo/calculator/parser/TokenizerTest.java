package com.demo.calculator.parser;

import com.demo.calculator.exception.InvalidOperatorException;
import com.demo.calculator.operator.OperatorBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    Tokenizer tokenizer = new Tokenizer();

    @Test
    void getToken() {
        String inputStr = "10222 3 * 2.0 3 - 1 sqrt undo";
        List<OperatorBase> r = new ArrayList<>();
        tokenizer.getToken(inputStr.getBytes(), 10, r);
        Assertions.assertEquals(inputStr.split(" ").length, r.size());
    }

    @Test
    void getTokenEmptyInput() {
        List<OperatorBase> r = new ArrayList<>();
        tokenizer.getToken("".getBytes(), 10, r);
        Assertions.assertTrue(r.isEmpty());
    }

    @Test
    void getTokenInvalidInput() {
        Assertions.assertThrows(InvalidOperatorException.class, () -> {
            tokenizer.getToken("0 1 blah".getBytes(), 10, new ArrayList<>());
        });
    }
}