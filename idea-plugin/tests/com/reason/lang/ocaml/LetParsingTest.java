package com.reason.lang.ocaml;

import static com.reason.lang.core.ExpressionFilterConstants.NO_FILTER;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.reason.ide.files.FileBase;
import com.reason.lang.core.psi.*;
import com.reason.lang.core.psi.impl.PsiLowerIdentifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class LetParsingTest extends OclParsingTestCase {
  public void test_constant() {
    PsiFile file = parseCode("let x = 1 let y = 2");
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
    PsiFile file = parseCode("let x l = for i = 0 to l - 1 do let x = 1 done");
    PsiLet let = first(letExpressions(file));

    assertTrue(let.isFunction());
    assertEquals("l = for i = 0 to l - 1 do let x = 1 done", let.getBinding().getText());
  }

  public void test_withSemiSeparator() {
    PsiFile file = parseCode("let rec read_num = Printf.printf; let l = 1");
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
    PsiFile file = parseCode("let visit_vo f = let segments = [| a; b; |] in let repr = x");
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
    PsiLet e = first(letExpressions(parseCode("let (a, b) = x;")));

    assertTrue(e.isDeconsruction());
    List<PsiElement> names = e.getDeconstructedElements();
    assertSize(2, names);
    assertEquals("a", names.get(0).getText());
    assertInstanceOf(names.get(0), PsiLowerIdentifier.class);
    assertEquals("b", names.get(1).getText());
    assertInstanceOf(names.get(1), PsiLowerIdentifier.class);
  }

  public void test_List() {
    PsiLet e =
        first(
            letExpressions(
                parseCode("let tokens = [ \"bullet\"; \"string\"; \"unicode_id_part\"; ]")));

    assertEquals("[ \"bullet\"; \"string\"; \"unicode_id_part\"; ]", e.getBinding().getText());
  }

  public void test_optionalParam() {
    PsiLet e =
        first(
            letExpressions(
                parseCode(
                    "let prod_to_str ?(plist=false) prod = String.concat \" \" (prod_to_str_r plist prod)")));

    assertTrue(e.isFunction());
    assertEquals(
        "?(plist=false) prod = String.concat \" \" (prod_to_str_r plist prod)",
        e.getBinding().getText());
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
        e.getPsiSignature().getText());
  }
}
