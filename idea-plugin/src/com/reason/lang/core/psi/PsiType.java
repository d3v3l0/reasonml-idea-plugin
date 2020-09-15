package com.reason.lang.core.psi;

import java.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import com.reason.lang.core.stub.PsiTypeStub;

public interface PsiType extends PsiQualifiedElement, NavigatablePsiElement, PsiStructuredElement, StubBasedPsiElement<PsiTypeStub> {

    @Nullable PsiElement getBinding();

    @NotNull Collection<PsiVariantDeclaration> getVariants();

    boolean isAbstract();
}
