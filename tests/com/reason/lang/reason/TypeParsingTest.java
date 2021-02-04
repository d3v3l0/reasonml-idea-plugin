package com.reason.lang.reason;

import com.intellij.psi.util.*;
import com.reason.lang.core.psi.*;
import com.reason.lang.core.psi.impl.*;

import java.util.*;

@SuppressWarnings("ConstantConditions")
public class TypeParsingTest extends RmlParsingTestCase {
    public void test_abstractType() {
        PsiType e = first(typeExpressions(parseCode("type t;")));
        assertEquals("t", e.getName());
        assertTrue(e.isAbstract());
    }

    public void test_RecursiveType() {
        PsiType e = first(typeExpressions(parseCode("type tree('a) = | Leaf('a) | Tree(tree('a), tree('a));")));
        assertEquals("tree", e.getName());
        assertEmpty(PsiTreeUtil.findChildrenOfType(e, PsiFunctionCallParams.class));
    }

    public void test_TypeBindingWithVariant() {
        PsiType e = first(typeExpressions(parseCode("type t = | Tick;")));
        assertNotNull(e.getBinding());
    }

    public void test_TypeBindingWithRecord() {
        PsiType e = first(typeExpressions(parseCode("type t = {count: int,\n [@bs.optional] key: string => unit\n};")));

        PsiRecord record = (PsiRecord) e.getBinding().getFirstChild();
        Collection<PsiRecordField> fields = record.getFields();
        assertEquals(2, fields.size());
    }

    public void test_TypeSpecialProps() {
        PsiType e = first(typeExpressions(
                parseCode(
                        "type props = { "
                                + "string: string, "
                                + "ref: Js.nullable(Dom.element) => unit, "
                                + "method: string };")));

        PsiRecord record = (PsiRecord) e.getBinding().getFirstChild();
        Collection<PsiRecordField> fields = record.getFields();
        assertEquals(3, fields.size());
    }

    public void test_bindingWithRecordAs() {
        PsiType e = first(typeExpressions(parseCode("type branch_info('branch_type) = { kind: [> | `Master] as 'branch_type, pos: id, };")));

        PsiRecord record = (PsiRecord) e.getBinding().getFirstChild();
        List<PsiRecordField> fields = new ArrayList<>(record.getFields());

        assertEquals(2, fields.size());
        assertEquals("kind", fields.get(0).getName());
        assertEquals("pos", fields.get(1).getName());
    }

    public void test_parameterized() {
        PsiType e = first(typeExpressions(parseCode("type declaration_arity('a, 'b) = | RegularArity('a);")));

        assertEquals("declaration_arity", e.getName());
        assertEquals("| RegularArity('a)", e.getBinding().getText());
    }

    public void test_scope() {
        PsiExternal e = first(externalExpressions(parseCode("external createElement : (reactClass, ~props: Js.t({..})=?, array(reactElement)) => reactElement =  \"createElement\"")));

        List<PsiSignatureItem> items = e.getSignature().getItems();

        assertSize(4, items);
        assertEquals("reactClass", items.get(0).getText());
        assertEquals("~props: Js.t({..})=?", items.get(1).getText());
        assertTrue(items.get(1).isNamedItem());
        PsiNamedParam namedParam = items.get(1).getNamedParam();
        assertEquals("props", namedParam.getName());
        assertEquals("Js.t({..})", namedParam.getSignature().getText());
        assertTrue(namedParam.isOptional());
        assertEquals("?", namedParam.getDefaultValue());
        assertEquals("array(reactElement)", items.get(2).getText());
        assertEquals("reactElement", items.get(3).getText());
    }

    public void test_JsObject() {
        PsiType e = first(typeExpressions(parseCode("type t = {. a: string };")));

        assertTrue(e.isJsObject());
        PsiObjectField f = PsiTreeUtil.findChildOfType(e.getBinding(), PsiObjectField.class);
        assertEquals("a", f.getName());
        assertEquals("string", f.getSignature().getText());
    }

    public void test_apply_params() {
        PsiType e = first(typeExpressions(parseCode("type t('value) = Belt.Map.t(key, 'value, Comparator.identity);")));

        assertEmpty(PsiTreeUtil.findChildrenOfType(e, PsiFunctionCallParams.class));
    }
}
