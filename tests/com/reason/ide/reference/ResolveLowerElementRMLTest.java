package com.reason.ide.reference;

import com.intellij.psi.*;
import com.reason.ide.*;
import com.reason.lang.core.psi.PsiParameter;
import com.reason.lang.core.psi.*;

public class ResolveLowerElementRMLTest extends ORBasePlatformTestCase {
    @Override
    protected String getTestDataPath() {
        return "testData/com/reason/ide/reference";
    }

    public void test_let_in_module_binding() {
        configureCode("A.re", "let foo = 2; module X = { let foo = 1; let z = foo<caret>; };");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.X.foo", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_let_inner_scope() {
        configureCode("A.re", "let x = 1; let a = { let x = 2; x<caret> + 10 };");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.a.x", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_inner_scope_in_function() {
        configureCode("A.re", "let x = 1; let fn = { let x = 2; fn1(x<caret>); };");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.fn.x", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_inner_scope_in_impl() {
        configureCode("A.rei", "let x:int;");
        configureCode("A.re", "let x = 1; let fn = { let foo = 2; fn1(foo<caret>); };");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.fn.foo", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
        assertEquals("A.re", e.getContainingFile().getName());
    }

    public void test_let_local_module_alias() {
        configureCode("A.rei", "let x:int;");
        configureCode("B.re", "let x = 1; module X = A; X.x<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.x", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_alias_path() {
        configureCode("A.re", "module W = { module X = { module Y = { module Z = { let z = 1; }; }; }; };");
        configureCode("B.re", "module C = A.W.X; module D = C.Y.Z; D.z<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.W.X.Y.Z.z", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_alias_x() {
        configureCode("A.re", "module Mode = { type t; };");
        configureCode("B.re", "module B1 = { module Mode = A.Mode; };");
        configureCode("C.re", "B.B1.Mode.t<caret>");        // B.B1.Mode.t -> A.Mode.t

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.Mode.t", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_open() {
        configureCode("B.re", "let x = 1;");
        configureCode("A.re", "let x = 2; open B; x<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("B.x", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_alias_open() {
        configureCode("B.re", "let x = 1;");
        configureCode("A.re", "let x = 2; module C = B; open C; x<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("B.x", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_let_local_open_parens() {
        configureCode("A.re", "module A1 = { let a = 1; };");
        configureCode("B.re", "let a = 2; let b = A.(A1.a<caret>);");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.A1.a", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_let_local_open_parens_2() {
        configureCode("A.re", "module A1 = { let a = 3; };");
        configureCode("B.re", "let a = A.A1.(a<caret>);");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.A1.a", ((PsiLet) e.getParent()).getQualifiedName());
    }

    public void test_type() {
        configureCode("A.re", "type t; type t' = t<caret>;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.t", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_type_with_path() {
        configureCode("A.re", "type t;");
        configureCode("B.re", "type t = A.t<caret>;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.t", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_type_with_path_2() {
        configureCode("A.re", "type t; type y = X.Y.t<caret>");

        assertThrows(AssertionError.class, "element not found in file A.re", () -> {
            PsiElement e = myFixture.getElementAtCaret();
        });
    }

    public void test_function() {
        configureCode("A.re", "module B = { let bb = 1; }; module C = { let cc = x => x; }; let z = C.cc(B.bb<caret>);");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.B.bb", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_function_open() {
        configureCode("B.re", "module C = { let make = x => x; let convert = x => x; }");
        configureCode("A.re", "open B; C.make([| C.convert<caret> |]);");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("B.C.convert", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_param_parenLess() {
        configureCode("A.re", "let add10 = x => x<caret> + 10;");

        PsiElement e = myFixture.getElementAtCaret();
        assertInstanceOf(e.getParent(), PsiParameter.class);
        assertEquals("A.add10[x]", ((PsiParameter) e.getParent()).getQualifiedName());
    }

    public void test_local_open_parens() {
        configureCode("A.re", "module A1 = { external a : int = \"\"; };");
        configureCode("B.re", "let b = A.(A1.a<caret>);");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.A1.a", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_local_open_parens_2() {
        configureCode("A.re", "module A1 = { external a : int = \"\"; };");
        configureCode("B.re", "let a = A.A1.(a<caret>);");

        PsiElement elementAtCaret = myFixture.getElementAtCaret();
        assertEquals("A.A1.a", ((PsiQualifiedNamedElement) elementAtCaret.getParent()).getQualifiedName());
    }

    public void test_local_open_parens_3() {
        configureCode("A.re", "module A1 = { type t = | Variant; let toString = x => x; };");
        configureCode("B.re", "A.A1.(Variant->toString<caret>);");

        PsiElement elementAtCaret = myFixture.getElementAtCaret();
        assertEquals("A.A1.toString", ((PsiQualifiedNamedElement) elementAtCaret.getParent()).getQualifiedName());
    }

    public void test_include() {
        configureCode("A.re", "module B = { type t; }; module C = B; include C; type x = t<caret>;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.B.t", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_include_2() {
        configureCode("Css_AtomicTypes.rei", "module Visibility: { type t = [ | `visible | `hidden | `collapse]; };");
        configureCode("Css_Legacy_Core.re", "module Types = Css_AtomicTypes;");
        configureCode("Css.re", "include Css_Legacy_Core;");
        configureCode("A.re", "type layoutRule; let visibility: [< Css.Types.Length.t | Css.Types.Visibility.t<caret> ] => layoutRule;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Css_AtomicTypes.Visibility.t", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_include_qualified() {
        configureCode("A.re", "module B = { module C = { type t; }; }; module D = B; include D.C;");
        configureCode("C.re", "type t = A.t<caret>;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.B.C.t", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_module_signature() {
        configureCode("A.re", "module B: { type t; let toString: t => string; }; module C: { type t; let toString: t<caret> => string; };");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.C.t", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_let_Local_open_pipe_first() {
        configureCode("A.re", "module A1 = { let add = x => x + 3; };");
        configureCode("B.re", "let x = A.A1.(x->add<caret>);");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.A1.add", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_external_local_open_pipe_first() {
        configureCode("A.re", "module A1 = { external add : int => int = \"\"; };");
        configureCode("B.re", "let x = A.A1.(x->add<caret>);");

        PsiElement elementAtCaret = myFixture.getElementAtCaret();
        assertEquals("A.A1.add", ((PsiQualifiedNamedElement) elementAtCaret.getParent()).getQualifiedName());
    }

    public void test_pipe_first() {
        configureCode("Css.mli", "val px: int => string;");
        configureCode("A.re", "Dimensions.spacing.small->Css.px<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Css.px", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_pipe_first_open() {
        configureCode("Css.mli", "val px: int => string;");
        configureCode("A.re", "let make = () => { open Css; Dimensions.spacing.small->px<caret>; }");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Css.px", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_pipe_first_open_2() {
        configureCode("Core.re", "module Async = { let get = x => x; };");
        configureCode("A.re", "open Core.Async; request->get<caret>(\"windows/settings\")");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Core.Async.get", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_pipe_first_open_with_path() {
        configureCode("Css.mli", "module Rule = { val px: int => string; };");
        configureCode("A.re", "let make = () => { open Css; Dimensions.spacing.small->Rule.px<caret>; }");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Css.Rule.px", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_multiple_module() {
        configureCode("Command.re", "module Settings = { module Action = { let convert = x => x; }; };");
        configureCode("A.re", "module C = Y; open Command; Settings.Action.convert<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Command.Settings.Action.convert", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_variant_constructor() {
        configureCode("B.re", "let convert = x => x;");
        configureCode("A.re", "X.Variant(B.convert<caret>())");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("B.convert", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_variant_constructor_tuple() {
        configureCode("B.re", "type t('a) = | Variant('a, 'b);");
        configureCode("A.re", "let x = 1; B.Variant(X.Y, x<caret>)");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.x", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_open_include() {
        configureCode("Css_Core.re", "let fontStyle = x => x;");
        configureCode("Css.re", "include Css_Core;");
        configureCode("A.re", "open Css; fontStyle<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Css_Core.fontStyle", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_open_include_deep() {
        configureCode("Css_Rule.re", "let fontStyle = x => x;");
        configureCode("Css_Core.re", "module Rules = { include Css_Rule; }");
        configureCode("Css.re", "include Css_Core;");
        configureCode("A.re", "open Css.Rules; fontStyle<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Css_Rule.fontStyle", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_resolution_1() {
        configureCode("Belt_MapString.mli", "val get: 'v t -> key -> 'v option");
        configureCode("Belt_Map.ml", "module String = Belt_MapString;");
        configureCode("Belt_Option.mli", "val flatMap : 'a option -> ('a -> 'b option) -> 'b option");
        configureCode("Belt.re", "module Option = Belt_Option; module Map = Belt_Map;");
        configureCode("A.re", "let x = (dict, locale) => locale->Belt.Option.flatMap<caret>(dict->Belt.Map.String.get);");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Belt_Option.flatMap", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_resolution_2() {
        configureCode("Belt_MapString.mli", "val get: 'v t -> key -> 'v option");
        configureCode("Belt_Map.ml", "module String = Belt_MapString;");
        configureCode("Belt_Option.mli", "val flatMap : 'a option -> ('a -> 'b option) -> 'b option");
        configureCode("Belt.re", "module Option = Belt_Option; module Map = Belt_Map;");
        configureCode("A.re", "let x = (dict, locale) => locale->Belt.Option.flatMap(dict->Belt.Map.String.get<caret>);");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Belt_MapString.get", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    /*
    public void test_functor_body() {
        configureCode("A.re", "module Make = (M:I) => { let a = 3; };");
        configureCode("B.re", "module Instance = A.Make({}); let b = Instance.a<caret>;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.Make.a", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_file_include_functor() {
        configureCode("A.re", "module Make = (M:I) => { let a = 3; }; include Make({})");
        configureCode("B.re", "let b = A.a<caret>;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.Make.a", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_functor_result_with_alias() {
        configureCode("A.re", "module type Result = { let a: int; };");
        configureCode("B.re", "module T = A; module Make = (M:Intf): T.Result => { let b = 3; };");
        configureCode("C.re", "module Instance = Make({}); let c = Instance.a<caret>;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.Result.a", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_path_functor() {
        configureCode("pervasives.mli", "external compare : 'a -> 'a -> int = \"%compare\"");
        configureCode("A.re", "module B = X.Functor({ let cmp = Pervasives.compare<caret>; })");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("Pervasives.compare", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }
    */

    //region record
    public void test_record() {
        configureCode("B.re", "let b = { a: 1, b: 2 }; b<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("B.b", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_record_l1() {
        configureCode("B.re", "let b = { a: 1, b: 2 }; b.b<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("B.b.b", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_record_l3() {
        configureCode("A.re", "let a = { b: { c: { d: 1 } } }; a.b.c.d<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.a.b.c.d", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }
    //endregion

    //region object
    public void test_object_l1() {
        configureCode("A.re", "let a = { \"b\": 1, \"c\": 2 }; a##b<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.a.b", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_object_l3() {
        configureCode("A.re", "let a = { \"b\": { \"c\": { \"d\": 1 } } }; a##b##c##d<caret>");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.a.b.c.d", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }
    //endregion

    public void test_GH_167_deconstruction() {
        configureCode("A.re", "let (count, setCount) = React.useState(() => 0); setCount<caret>(1);");

        PsiElement elementAtCaret = myFixture.getElementAtCaret();
        assertEquals(12, elementAtCaret.getTextOffset());
    }

    public void test_GH_303() {
        configureCode("B.re", "type t1 = {bar: string};");
        configureCode("A.re", "type t = {bar: string}; let bar = item => item.bar<caret>;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.t.bar", ((PsiQualifiedNamedElement) e.getParent()).getQualifiedName());
    }

    public void test_GH_303_2() {
        configureCode("B.re", "type t1 = {bar:string};");
        configureCode("A.re", "type t = {bar: string}; let bar<caret> = item => item.bar;");

        PsiElement e = myFixture.getElementAtCaret();
        assertEquals("A.bar", ((PsiLet) e.getParent()).getQualifiedName());
    }
}
