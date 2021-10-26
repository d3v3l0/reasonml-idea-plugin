package com.reason.ide.hints;

import com.reason.ide.*;

public class RmlParameterInfoHandlerTest extends ORBasePlatformTestCase {
    public void test_basic() {
        configureCode("A.rei", "let add : (int, int) => int;");
        configureCode("B.re", "A.add(<caret>)");

        UIInfoContext context = getParameterInfoUI(new RmlParameterInfoHandler());

        assertEquals("(int, int) => int", context.text);
        assertEquals(0, context.currentParam);
    }

    public void test_intf_impl() {
        configureCode("A.rei", "let add : (int, int) => int;");
        configureCode("A.re", "let add = (x, y) => x + y;");
        configureCode("B.re", "A.add(<caret>)");

        UIInfoContext context = getParameterInfoUI(new RmlParameterInfoHandler());

        assertEquals("(int, int) => int", context.text);
        assertEquals(0, context.currentParam);
    }

    public void test_empty() {
        configureCode("A.rei", "let fn : unit => string;");
        configureCode("B.re", "A.fn(<caret>)");

        UIInfoContext context = getParameterInfoUI(new RmlParameterInfoHandler());

        assertEquals("unit => string", context.text);
        assertEquals(0, context.currentParam);
    }

    public void test_item() {
        configureCode("A.rei", "let add : (int, int) => int;");
        configureCode("B.re", "A.add(1, <caret>)");

        UIInfoContext context = getParameterInfoUI(new RmlParameterInfoHandler());

        assertEquals("(int, int) => int", context.text);
        assertEquals(1, context.currentParam);
    }

    public void test_alias() {
        configureCode("A.rei", "let add : (int, int) => int;");
        configureCode("B.re", "let b = A.add; b(<caret>)");

        UIInfoContext context = getParameterInfoUI(new RmlParameterInfoHandler());

        assertEquals("(int, int) => int", context.text);
        assertEquals(0, context.currentParam);
    }

    public void test_pipe_first() {
        configureCode("A.rei", "let add : (option(int), int) => int;");
        configureCode("B.re", "None->A.add(<caret>)");

        UIInfoContext context = getParameterInfoUI(new RmlParameterInfoHandler());

        assertEquals("(option(int), int) => int", context.text);
        assertEquals(1, context.currentParam);
    }
}
