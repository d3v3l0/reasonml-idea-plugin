package com.reason.ide.hints;

import com.reason.ide.*;

public class ResParameterInfoHandlerTest extends ORBasePlatformTestCase {
    public void test_basic() {
        configureCode("A.resi", "let add : (int, int) => int");
        configureCode("B.res", "A.add(<caret>)");

        UIInfoContext context = getParameterInfoUI(new ResParameterInfoHandler());

        assertEquals("(int, int) => int", context.text);
        assertEquals(0, context.currentParam);
    }

    public void test_intf_impl() {
        configureCode("A.resi", "let add : (int, int) => int");
        configureCode("A.res", "let add = (x, y) => x + y");
        configureCode("B.res", "A.add(<caret>)");

        UIInfoContext context = getParameterInfoUI(new ResParameterInfoHandler());

        assertEquals("(int, int) => int", context.text);
        assertEquals(0, context.currentParam);
    }

    public void test_empty() {
        configureCode("A.resi", "let fn : unit => string");
        configureCode("B.res", "A.fn(<caret>)");

        UIInfoContext context = getParameterInfoUI(new ResParameterInfoHandler());

        assertEquals("unit => string", context.text);
        assertEquals(0, context.currentParam);
    }

    public void test_item() {
        configureCode("A.resi", "let add : (int, int) => int");
        configureCode("B.res", "A.add(1, <caret>)");

        UIInfoContext context = getParameterInfoUI(new ResParameterInfoHandler());

        assertEquals("(int, int) => int", context.text);
        assertEquals(1, context.currentParam);
    }

    public void test_pipe_first() {
        configureCode("A.resi", "let add : (option(int), int) => int");
        configureCode("B.res", "None->A.add(<caret>)");

        UIInfoContext context = getParameterInfoUI(new ResParameterInfoHandler());

        assertEquals("(option(int), int) => int", context.text);
        assertEquals(1, context.currentParam);
    }
}
