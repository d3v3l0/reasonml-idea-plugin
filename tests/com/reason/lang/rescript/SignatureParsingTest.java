package com.reason.lang.rescript;

import com.reason.lang.core.*;
import com.reason.lang.core.psi.*;
import com.reason.lang.core.psi.impl.*;

import java.util.*;

@SuppressWarnings("ConstantConditions")
public class SignatureParsingTest extends ResParsingTestCase {
    public void test_let() {
        PsiLet let = first(letExpressions(parseCode("let x: int = 1")));

        PsiSignature signature = let.getSignature();
        assertEquals("int", signature.asText(getLangProps()));
        assertFalse(signature.getItems().get(0).isOptional());
    }

    public void test_trimming() {
        PsiLet let = first(letExpressions(parseCode("let statelessComponent:\n  string =>\n  componentSpec<\n    stateless,\n    stateless,\n    noRetainedProps,\n    noRetainedProps,\n    actionless,\n  >\n")));

        PsiSignature signature = let.getSignature();
        assertEquals("string => componentSpec<stateless, stateless, noRetainedProps, noRetainedProps, actionless>", signature.asText(getLangProps()));
    }

    public void test_parsing_named_params() {
        PsiLet let = first(letExpressions(parseCode("let padding: (~v:length, ~h:length) => rule")));

        PsiSignature signature = let.getSignature();
        assertEquals(3, signature.getItems().size());
        assertEquals("(~v:length, ~h:length) => rule", signature.asText(getLangProps()));
        assertFalse(signature.getItems().get(0).isOptional());
        assertEquals("v", signature.getItems().get(0).getNamedParam().getName());
        assertFalse(signature.getItems().get(1).isOptional());
        assertEquals("h", signature.getItems().get(1).getNamedParam().getName());
    }

    public void test_optional_fun() {
        PsiLet let = first(letExpressions(parseCode("let x:int => option<string> => string = (a,b) => c")));

        List<PsiSignatureItem> items = let.getSignature().getItems();
        assertEquals("int", items.get(0).getText());
        assertFalse(items.get(0).isOptional());
        assertEquals("option<string>", items.get(1).getText());
        assertFalse(items.get(1).isOptional());
        assertEquals("string", items.get(2).getText());
        assertSize(3, items);
    }

    public void test_optional_fun_parameters() {
        PsiLet let = first(letExpressions(parseCode("let x = (a:int, b:option<string>, ~c:bool=false, ~d:float=?) => 3")));

        PsiFunction function = (PsiFunction) let.getBinding().getFirstChild();
        List<PsiParameter> parameters = new ArrayList<>(function.getParameters());

        assertFalse(parameters.get(0).getSignature().getItems().get(0).isOptional());
        assertFalse(parameters.get(1).getSignature().getItems().get(0).isOptional());
        assertEquals("bool", parameters.get(2).getSignature().asText(getLangProps()));
        assertTrue(parameters.get(2).isOptional());
        assertEquals("false", parameters.get(2).getDefaultValue().getText());
        assertEquals("float", parameters.get(3).getSignature().asText(getLangProps()));
        assertTrue(parameters.get(3).isOptional());
        assertEquals("?", parameters.get(3).getDefaultValue().getText());
    }

    public void test_unit_fun_parameter() {
        PsiLet e = first(letExpressions(parseCode("let x = (~color=\"red\", ~radius=1, ()) => 1")));

        PsiFunction function = (PsiFunction) e.getBinding().getFirstChild();
        List<PsiParameter> parameters = new ArrayList<>(function.getParameters());

        assertSize(3, parameters);
    }

    public void test_jsObject() {
        PsiType psiType = first(typeExpressions(parseCode("type props = {@optional dangerouslySetInnerHTML: {\"__html\": string}}")));

        PsiRecord record = (PsiRecord) psiType.getBinding().getFirstChild();
        List<PsiRecordField> fields = new ArrayList<>(record.getFields());

        assertEquals(1, fields.size());
        assertEquals("{\"__html\": string}", fields.get(0).getSignature().asText(getLangProps()));
    }

    public void test_external_fun() {
        PsiExternal e = first(externalExpressions(parseCode("external refToJsObj: reactRef => {..} = \"%identity\";")));

        PsiSignature signature = e.getSignature();
        assertSize(2, ORUtil.findImmediateChildrenOfClass(e.getSignature(), PsiSignatureItem.class));
        assertEquals("reactRef => {..}", signature.asText(getLangProps()));
    }

    public void test_external_fun_2() {
        PsiExternal e = first(externalExpressions(parseCode("external requestAnimationFrame: (unit => string) => animationFrameID = \"\"")));

        PsiSignature signature = e.getSignature();
        List<PsiSignatureItem> signatureItems = signature.getItems();
        assertEquals("unit", signatureItems.get(0).getText());
        assertEquals("string", signatureItems.get(1).getText());
        assertEquals("animationFrameID", signatureItems.get(2).getText());
        assertSize(3, signatureItems);
    }

    public void test_option() {
        PsiExternal e = first(externalExpressions(parseCode("external e: option<show> = \"\"")));

        PsiSignatureItem sigItem = ORUtil.findImmediateChildrenOfClass(e.getSignature(), PsiSignatureItem.class).iterator().next();
        assertEquals("option<show>", sigItem.asText(getLangProps()));
    }

    public void test_default_optional() {
        PsiLet let = first(letExpressions(parseCode("let createAction: (string, payload, ~meta: 'meta=?, unit) => opaqueFsa")));
        PsiSignature signature = let.getSignature();
        // assertEquals("(string, payload, ~meta: 'meta=?, unit) => opaqueFsa",
        // signature.asString(getLangProps()));
    }

    // TODO later
    //public void test_react() {
    //    PsiExternal e = first(externalExpressions(parseCode("external useState: (@uncurry (unit => 'state)) => ('state, (. 'state => 'state) => unit) = \"useState\"")));

    //assertEquals("useState", e.getExternalName());
    //assertEquals("(@uncurry (unit => 'state)) => ('state, (. 'state => 'state) => unit)", e.getSignature().getText());
    //assertEmpty(PsiTreeUtil.findChildrenOfType(e, PsiFunction.class));
    //List<PsiSignatureItem> signatureItems = e.getSignature().getItems();
    //assertEquals("@uncurry (unit => 'state)", signatureItems.get(0).getText());
    // ? assertEquals("('state, (. 'state => 'state) => unit)", signatureItems.get(1).getText());
    //}
}
