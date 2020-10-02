package com.reason.lang.reason;

import com.reason.lang.core.psi.PsiExternal;
import com.reason.lang.core.psi.PsiSignature;
import com.reason.lang.core.psi.impl.PsiExternalImpl;
import com.reason.lang.core.psi.impl.PsiLowerIdentifier;

@SuppressWarnings("ConstantConditions")
public class ExternalParsingTest extends RmlParsingTestCase {
  public void test_signature() {
    PsiExternal e = externalExpression(parseCode("external props : (string) => string;"), "props");

    PsiSignature signature = e.getPsiSignature();
    assertEquals("(string) => string", signature.getText());
    assertTrue(e.isFunction());
  }

  public void test_withString() {
    PsiExternal e =
        firstOfType(
            parseCode(
                "external reactIntlJsReactClass: ReasonReact.reactClass = \"FormattedMessage\""),
            PsiExternal.class);

    assertEquals("ReasonReact.reactClass", e.getORSignature().asString(myLanguage));
    assertFalse(e.isFunction());
    assertEquals("FormattedMessage", e.getExternalName());
  }

  public void test_withEmptyString() {
    PsiExternal e =
        firstOfType(
            parseCode("external reactIntlJsReactClass: ReasonReact.reactClass = \"\""),
            PsiExternal.class);

    assertEquals("ReasonReact.reactClass", e.getORSignature().asString(myLanguage));
    assertFalse(e.isFunction());
    assertEquals("", e.getExternalName());
  }

  public void test_string() {
    PsiExternal e =
        firstOfType(
            parseCode("external string : string => reactElement = \"%identity\""),
            PsiExternal.class);

    assertEquals("string", e.getName());
    assertInstanceOf(((PsiExternalImpl) e).getNameIdentifier(), PsiLowerIdentifier.class);
    assertEquals("string => reactElement", e.getPsiSignature().getText());
    assertEquals("%identity", e.getExternalName());
  }

  public void test_array() {
    PsiExternal e =
        firstOfType(
            parseCode("external array : array(reactElement) => reactElement = \"%identity\""),
            PsiExternal.class);

    assertEquals("array", e.getName());
    assertEquals("array(reactElement) => reactElement", e.getPsiSignature().getText());
    assertEquals("%identity", e.getExternalName());
  }
}
