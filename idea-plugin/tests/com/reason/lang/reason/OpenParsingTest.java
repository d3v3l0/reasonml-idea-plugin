package com.reason.lang.reason;

import com.reason.lang.core.psi.PsiOpen;

public class OpenParsingTest extends RmlParsingTestCase {
    public void testOne() {
        PsiOpen e = first(openExpressions(parseCode("open Belt;")));

        assertEquals("Belt", e.getQualifiedName());
    }

    public void testPath() {
        PsiOpen e = first(openExpressions(parseCode("open Belt.Array;")));

        assertEquals("Belt.Array", e.getQualifiedName());
    }

    public void testFunctor() {
        PsiOpen e = first(openExpressions(parseCode("open A.Make({ type t; })")));

        assertTrue(e.useFunctor());
        assertEquals("A.Make", e.getQualifiedName());
    }
}
