package com.reason.lang.ocaml;

import com.intellij.psi.*;
import com.intellij.psi.util.*;
import com.reason.ide.files.*;
import com.reason.lang.core.psi.*;
import com.reason.lang.core.psi.impl.*;

import java.util.*;

import static com.reason.lang.core.ExpressionFilterConstants.*;

@SuppressWarnings("ConstantConditions")
public class LetParsingTest extends OclParsingTestCase {
    public void test_constant() {
        FileBase file = parseCode("let x = 1 let y = 2");
        List<PsiLet> lets = new ArrayList<>(letExpressions(file));

        assertEquals(2, lets.size());
        assertEquals("x", lets.get(0).getName());
        assertFalse(lets.get(0).isFunction());
        assertNotNull(first(PsiTreeUtil.findChildrenOfType(lets.get(0), PsiLetBinding.class)));
    }

    public void test_binding() {
        PsiLet let = first(letExpressions(parseCode("let obj = [%bs.obj { a = \"b\" }];")));

        assertFalse(let.isFunction());
        assertNotNull(first(PsiTreeUtil.findChildrenOfType(let, PsiLetBinding.class)));
    }

    public void test_bindingWithFunction() {
        PsiLet let = first(letExpressions(parseCode("let add x y = x + y")));
        assertNotNull(first(PsiTreeUtil.findChildrenOfType(let, PsiLetBinding.class)));
        assertEquals("x y = x + y", let.getBinding().getText());
    }

    public void testScopeWithSome() {
        PsiLet let =
                first(
                        letExpressions(
                                parseCode(
                                        "let l = (p) => { switch (a) { | Some(a) => a; (); | None => () }; Some(z); };")));

        PsiLetBinding binding = first(PsiTreeUtil.findChildrenOfType(let, PsiLetBinding.class));
        assertNotNull(binding);
    }

    public void testScopeWithLIdent() {
        PsiLet e = first(letExpressions(parseCode("let fn p = Js.log p; returnObj")));

        assertTrue(e.isFunction());
        assertEquals("fn", e.getName());
    }

    public void test_record() {
        PsiLet let = first(letExpressions(parseCode("let r = { one = 1; two = 2 }")));

        PsiLetBinding binding = first(PsiTreeUtil.findChildrenOfType(let, PsiLetBinding.class));
        assertNotNull(binding);
        PsiRecord record = PsiTreeUtil.findChildOfType(binding, PsiRecord.class);
        assertNotNull(record);
        Collection<PsiRecordField> fields = record.getFields();
        assertSize(2, fields);
        Iterator<PsiRecordField> itFields = fields.iterator();
        assertEquals("one = 1", itFields.next().getText());
        assertEquals("two = 2", itFields.next().getText());
    }

    public void test_rec() {
        PsiLet let = first(letExpressions(parseCode("let rec lx x = x + 1")));

        assertTrue(let.isFunction());
        assertEquals("lx", let.getName());
    }

    public void test_inDoLoop() {
        FileBase file = parseCode("let x l = for i = 0 to l - 1 do let x = 1 done");
        PsiLet let = first(letExpressions(file));

        assertTrue(let.isFunction());
        assertEquals("l = for i = 0 to l - 1 do let x = 1 done", let.getBinding().getText());
    }

    public void test_withSemiSeparator() {
        FileBase file = parseCode("let rec read_num = Printf.printf; let l = 1");
        Collection<PsiLet> lets = letExpressions(file);

        assertEquals(1, lets.size());
    }

    public void test_likeLocalOpen() {
        PsiOpen open = first(openExpressions(parseCode("let open Univ")));

        assertEquals("let open Univ", open.getText());
    }

    public void test_likeModule() {
        FileBase file = parseCode("let module Repr = (val repr : S)");
        PsiModule module = first(moduleExpressions(file));

        assertEquals(1, childrenCount(file));
        assertEquals("Repr", module.getName());
    }

    public void test_chaining() {
        FileBase file = parseCode("let visit_vo f = let segments = [| a; b; |] in let repr = x");
        Collection<PsiLet> lets = letExpressions(file);

        assertEquals(1, lets.size());
    }

    public void test_case1() {
        FileBase file =
                parseCode(
                        "let format_open {o_loc; o_name; o_items; _} = "
                                + "Printf.printf \"O|%s|%s|%s\\n\" (format_location o_loc) o_name (Util.join_list \", \" !o_items)");
        PsiLet e = first(letExpressions(file));

        PsiLetBinding binding = e.getBinding();
        assertInstanceOf(binding.getFirstChild(), PsiFunction.class);
        PsiFunction function = (PsiFunction) binding.getFirstChild();
        assertEquals("{o_loc; o_name; o_items; _}", first(function.getParameters()).getText());
        assertEquals(
                "Printf.printf \"O|%s|%s|%s\\n\" (format_location o_loc) o_name (Util.join_list \", \" !o_items)",
                function.getBody().getText());
    }

    public void test_qualifiedName() {
        PsiLet root = first(letExpressions(parseCode("let root = x")));
        PsiLet inner =
                PsiTreeUtil.findChildOfType(
                        first(letExpressions(parseCode("let root = let inner = x in inner"))), PsiLet.class);
        PsiModule mod = first(moduleExpressions(parseCode("module M = struct let m = 1 end")));

        assertEquals("Dummy.root", root.getQualifiedName());
        assertEquals("Dummy.root.inner", inner.getQualifiedName());
        assertEquals(
                "Dummy.M.m",
                ((PsiLet) mod.getExpressions(ExpressionScope.all, NO_FILTER).iterator().next())
                        .getQualifiedName());
    }

    public void test_deconstruction() {
        PsiLet e = first(letExpressions(parseCode("let (a, b) = x")));

        assertTrue(e.isDeconstruction());
        List<PsiElement> names = e.getDeconstructedElements();
        assertSize(2, names);
        assertEquals("a", names.get(0).getText());
        assertInstanceOf(names.get(0), PsiLowerIdentifier.class);
        assertEquals("b", names.get(1).getText());
        assertInstanceOf(names.get(1), PsiLowerIdentifier.class);
    }

    public void test_deconstruction_parenless() {
        PsiLet e = first(letExpressions(parseCode("let a, b = x")));

        assertTrue(e.isDeconstruction());
        List<PsiElement> names = e.getDeconstructedElements();
        assertSize(2, names);
        assertEquals("a", names.get(0).getText());
        assertInstanceOf(names.get(0), PsiLowerIdentifier.class);
        assertEquals("b", names.get(1).getText());
        assertInstanceOf(names.get(1), PsiLowerIdentifier.class);
    }

    public void test_List() {
        PsiLet e = first(letExpressions(parseCode("let tokens = [ \"bullet\"; \"string\"; \"unicode_id_part\"; ]")));

        assertEquals("[ \"bullet\"; \"string\"; \"unicode_id_part\"; ]", e.getBinding().getText());
    }

    public void test_optional_param() {
        PsiLet e = first(letExpressions(parseCode("let prod_to_str ?(plist=false) prod = String.concat \" \" (prod_to_str_r plist prod)")));

        assertTrue(e.isFunction());
        assertEquals("?(plist=false) prod = String.concat \" \" (prod_to_str_r plist prod)", e.getBinding().getText());
    }

    public void test_comparison() {
        PsiLet e = firstOfType(parseCode("let lt x y = (x lxor 0x4) < (y lxor 0x4)\ntype t"), PsiLet.class);
        PsiFunctionBody b = e.getFunction().getBody();

        assertEquals("(x lxor 0x4) < (y lxor 0x4)", b.getText());
    }

    public void test_semi() {
        PsiLet e =
                firstOfType(
                        parseCode("let instantiate = for i = 0 to len - 1 do done; get_data p"), PsiLet.class);
        PsiLetBinding b = e.getBinding();

        assertEquals("for i = 0 to len - 1 do done; get_data p", b.getText());
    }

    public void test_name_unit() {
        PsiLet e = firstOfType(parseCode("let () = 1 + 2"), PsiLet.class);

        assertNotNull(PsiTreeUtil.findChildOfType(e, PsiUnit.class));
        assertNull(e.getName());
    }

    // should it be parsed like a function ?
    // https://github.com/reasonml-editor/reasonml-idea-plugin/issues/309
    public void test_infix_operator() {
        PsiLet e = firstOfType(parseCode("let (|?) m (key, cb) = m |> Ext_json.test key cb"), PsiLet.class);

        assertEquals("(|?)", e.getName());
        assertNull(PsiTreeUtil.findChildOfType(e, PsiPatternMatch.class));
        assertEquals("m |> Ext_json.test key cb", e.getBinding().getText());
    }

    // https://github.com/reasonml-editor/reasonml-idea-plugin/issues/105
    public void test_GH_105() {
        FileBase file = parseCode("let string = \"x\"");
        PsiLet e = first(letExpressions(file));

        assertFalse(e.isFunction());
        assertEquals("string", e.getName());
    }

    public void test_GH_105a() {
        FileBase file = parseCode("let string s = \"x\"");
        PsiLet e = first(letExpressions(file));

        assertTrue(e.isFunction());
        assertEquals("string", e.getName());
    }

    public void test_GH_105b() {
        FileBase file = parseCode("let int = 1");
        PsiLet e = first(letExpressions(file));

        assertFalse(e.isFunction());
        assertEquals("int", e.getName());
    }

    public void test_GH_105c() {
        FileBase file = parseCode("let bool = 1");
        PsiLet e = first(letExpressions(file));

        assertFalse(e.isFunction());
        assertEquals("bool", e.getName());
    }

    // https://github.com/reasonml-editor/reasonml-idea-plugin/issues/116
    public void test_GH_116() {
        FileBase file =
                parseCode("let ((), proofview, _, _) = Proofview.apply (Global.env ()) tac pr.proofview");
        PsiLet e = first(letExpressions(file));

        assertSize(2, e.getDeconstructedElements());
        assertFalse(e.isFunction());
        assertEquals("Proofview.apply (Global.env ()) tac pr.proofview", e.getBinding().getText());
    }

    // https://github.com/reasonml-editor/reasonml-idea-plugin/issues/121
    public void test_GH_121() {
        Collection<PsiLet> lets =
                letExpressions(parseCode("let rec f x y = match x with | [] -> return y\n let x =  1"));

        assertSize(2, lets);
    }

    // https://github.com/reasonml-editor/reasonml-idea-plugin/issues/270
    // https://caml.inria.fr/pub/docs/manual-ocaml/polymorphism.html#ss:explicit-polymorphism
    public void test_GH_270() {
        PsiLet e =
                first(
                        letExpressions(
                                parseCode(
                                        "let rec parser_of_tree : type s tr r. s ty_entry -> int -> int -> (s, tr, r) ty_tree -> r parser_t =\n"
                                                + "  fun entry nlevn alevn -> ()")));

        assertEquals("parser_of_tree", e.getName());
        assertTrue(e.isFunction());
        assertEquals(
                "s ty_entry -> int -> int -> (s, tr, r) ty_tree -> r parser_t",
                e.getSignature().getText());
    }

    // https://github.com/reasonml-editor/reasonml-idea-plugin/issues/278
    public void test_GH_278() {
        PsiLet e = first(letExpressions(parseCode("let (//) = Ext_path.combine")));

        assertEquals("(//)", e.getName());
        assertNull(PsiTreeUtil.findChildOfType(e, PsiComment.class));
    }
}
