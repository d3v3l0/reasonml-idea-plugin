package com.reason.lang.ocaml;

import com.intellij.psi.*;
import com.intellij.psi.tree.*;
import com.intellij.psi.util.*;
import com.reason.lang.core.psi.PsiParameter;
import com.reason.lang.core.psi.*;
import com.reason.lang.core.psi.impl.*;

import java.util.*;
import java.util.stream.*;

@SuppressWarnings("ConstantConditions")
public class FunctorParsingTest extends OclParsingTestCase {
    public void test_basic() {
        PsiNamedElement e = first(expressions(parseCode("module Make (M:Def) : S = struct end")));

        PsiFunctor f = (PsiFunctor) e;
        assertEquals("struct end", f.getBody().getText());
        assertEquals("S", f.getReturnType().getText());
        List<IElementType> uTypes =
                PsiTreeUtil.findChildrenOfType(e, PsiUpperSymbol.class)
                        .stream()
                        .map(psi -> psi.getFirstChild().getNode().getElementType())
                        .collect(Collectors.toList());
        assertDoesntContain(uTypes, m_types.VARIANT_NAME);
    }

    public void test_struct() {
        PsiNamedElement e = first(expressions(parseCode("module Make (struct type t end) : S = struct end")));

        PsiFunctor f = (PsiFunctor) e;
        assertEquals("struct end", f.getBody().getText());
        assertEquals("S", f.getReturnType().getText());
        List<IElementType> uTypes =
                PsiTreeUtil.findChildrenOfType(e, PsiUpperSymbol.class)
                        .stream()
                        .map(psi -> psi.getFirstChild().getNode().getElementType())
                        .collect(Collectors.toList());
        assertDoesntContain(uTypes, m_types.VARIANT_NAME);
    }

    public void test_implicit_result() {
        PsiNamedElement e = first(expressions(parseCode("module Make (M:Def) = struct end")));

        PsiFunctor f = (PsiFunctor) e;
        assertEquals("struct end", f.getBody().getText());
    }

    public void test_withConstraints() {
        Collection<PsiNamedElement> expressions =
                expressions(
                        parseCode(
                                "module Make (M: Input) : S with type +'a t = 'a M.t and type b = M.b = struct end"));

        assertEquals(1, expressions.size());
        PsiFunctor f = (PsiFunctor) first(expressions);

        assertEquals("M: Input", first(f.getParameters()).getText());
        assertEquals("S", f.getReturnType().getText());

        List<PsiConstraint> constraints = new ArrayList<>(f.getConstraints());
        assertEquals(2, constraints.size());
        assertEquals("type +'a t = 'a M.t", constraints.get(0).getText());
        assertEquals("type b = M.b", constraints.get(1).getText());
        assertEquals("struct end", f.getBody().getText());
    }

    public void test_signature() {
        Collection<PsiFunctor> functors =
                functorExpressions(
                        parseCode( //
                                "module GlobalBindings (M : sig\n"
                                        + //
                                        "val relation_classes : string list\n"
                                        + //
                                        "val morphisms : string list\n"
                                        + //
                                        "val arrow : evars -> evars * constr\n"
                                        + //
                                        "end) = struct  open M  end"));

        assertEquals(1, functors.size());
        PsiFunctor functor = first(functors);
        assertEquals("GlobalBindings", functor.getName());
        assertEquals("Dummy.GlobalBindings", functor.getQualifiedName());
        Collection<PsiParameter> parameters = functor.getParameters();
        assertSize(1, parameters);
        assertEquals("M", first(parameters).getName());
        assertNotNull(functor.getBody());
    }

    public void test_functorInstanciation() {
        PsiInnerModule module = (PsiInnerModule) first(moduleExpressions(parseCode("module Printing = Make (struct let encode = encode_record end)")));

        assertNull(module.getBody());
        PsiFunctorCall call = PsiTreeUtil.findChildOfType(module, PsiFunctorCall.class);
        assertEquals("Make (struct let encode = encode_record end)", call.getText());

        Collection<PsiParameter> parameters = call.getParameters();
        assertSize(1, parameters);
        assertEquals("Dummy.Printing.Make[0]", first(parameters).getQualifiedName());
    }

    public void test_functorInstanciationChaining() {
        PsiFile file = parseCode("module KeyTable = Hashtbl.Make(KeyHash)\ntype infos");
        List<PsiNamedElement> expressions = new ArrayList<>(expressions(file));

        assertEquals(2, expressions.size());

        PsiInnerModule module = (PsiInnerModule) expressions.get(0);
        assertNull(module.getBody());
        PsiFunctorCall call = PsiTreeUtil.findChildOfType(module, PsiFunctorCall.class);
        assertNotNull(call);
        assertEquals("Hashtbl.Make(KeyHash)", call.getText());
    }
}
