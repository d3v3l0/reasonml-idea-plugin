package com.reason.ide.search;

import com.intellij.lang.HelpID;
import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.reason.lang.core.psi.*;
import com.reason.lang.ocaml.OclLexer;
import com.reason.lang.ocaml.OclTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OclFindUsagesProvider implements com.intellij.lang.findUsages.FindUsagesProvider {
    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        OclTypes types = OclTypes.INSTANCE;
        return new DefaultWordsScanner(
                new OclLexer(),
                TokenSet.create(types.C_UPPER_SYMBOL, types.C_LOWER_SYMBOL, types.C_VARIANT),
                TokenSet.EMPTY,
                TokenSet.EMPTY);
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement element) {
        return element instanceof PsiUpperSymbol || element instanceof PsiLowerSymbol;
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull PsiElement psiElement) {
        return HelpID.FIND_OTHER_USAGES;
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement element) {
        if (element instanceof PsiUpperSymbol)  {
            return ((PsiUpperSymbol) element).isVariant() ? "variant" : "module";
        }
        if (element instanceof PsiInnerModule) {
            return "module";
        }
        if (element instanceof PsiLowerSymbol) {
            return "symbol";
        }
        if (element instanceof PsiLet) {
            return "let";
        }
        if (element instanceof PsiVal) {
            return "val";
        }
        if (element instanceof PsiExternal) {
            return "external";
        }
        if (element instanceof PsiType) {
            return "type";
        }

        return "unknown type";
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof PsiNamedElement) {
            String name = ((PsiNamedElement) element).getName();
            return name == null ? "" : name;
        }

        return "desc name of element ";
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if (element instanceof PsiNamedElement) {
            String name = ((PsiNamedElement) element).getName();
            return name == null ? "" : name;
        }

        return "";
    }
}
