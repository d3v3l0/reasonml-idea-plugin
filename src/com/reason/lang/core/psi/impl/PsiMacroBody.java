package com.reason.lang.core.psi.impl;

import com.intellij.json.psi.impl.*;
import com.intellij.lang.*;
import com.intellij.openapi.util.*;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.*;
import com.intellij.psi.tree.*;
import com.reason.lang.core.*;
import com.reason.lang.core.type.*;
import org.jetbrains.annotations.*;

public class PsiMacroBody extends CompositeTypePsiElement<ORTypes> implements PsiLanguageInjectionHost {
    protected PsiMacroBody(@NotNull ORTypes types, @NotNull IElementType elementType) {
        super(types, elementType);
    }

    @Override
    public boolean isValidHost() {
        return true;
    }

    @Override
    public @NotNull PsiLanguageInjectionHost updateText(@NotNull String text) {
        ASTNode valueNode = getNode().getFirstChildNode();
        if (valueNode instanceof LeafElement) {
            ((LeafElement) valueNode).replaceWithText(text);
        }
        return this;
    }

    @Override
    public @NotNull LiteralTextEscaper<? extends PsiLanguageInjectionHost> createLiteralTextEscaper() {
        return new JSStringLiteralEscaper<PsiLanguageInjectionHost>(this) {
            @Override
            protected boolean isRegExpLiteral() {
                return false;
            }
        };
    }

    public @Nullable TextRange getMacroTextRange() {
        IElementType elementType = getNode().getFirstChildNode().getElementType();
        if (elementType == m_types.STRING_VALUE || elementType == m_types.ML_STRING_VALUE) {
            int max = getTextLength() - 1;
            if (1 <= max) {
                return new TextRange(1, max);
            }
        } else {
            int max = getTextLength() - 2;
            if (2 <= max) {
                return new TextRange(2, max);
            }
        }

        return null;
    }

    @Override
    public @NotNull String toString() {
        return "PsiMacroBody";
    }
}
