package com.reason.lang.core.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;

public class PsiJsObjectField extends ASTWrapperPsiElement {

    public PsiJsObjectField(ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "JsObjectField";
    }
}
