package com.reason.ide;

import java.io.*;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.reason.ide.files.FileBase;
import com.reason.lang.core.ORUtil;
import com.reason.lang.core.psi.PsiQualifiedElement;
import com.reason.lang.core.psi.impl.PsiLowerIdentifier;
import com.reason.lang.core.psi.impl.PsiUpperIdentifier;

public abstract class ORBasePlatformTestCase extends BasePlatformTestCase {

    @NotNull
    @SuppressWarnings("UnusedReturnValue")
    protected FileBase configureCode(@NotNull String fileName, @NotNull String code) {
        PsiFile file = myFixture.configureByText(fileName, code);
        System.out.println("» " + fileName + " " + this.getClass());
        System.out.println(DebugUtil.psiToString(file, true, true));

        return (FileBase) file;
    }

    protected PsiElement getNameIdentifier(PsiQualifiedElement e) {
        return ORUtil.findImmediateFirstChildOfAnyClass(e, PsiUpperIdentifier.class, PsiLowerIdentifier.class);
    }

    protected PsiElement getFromCaret(PsiFile f) {
        return f.findElementAt(myFixture.getCaretOffset() - 1);
    }

    @NotNull
    protected String toJson(@NotNull String value) {
        return value.replaceAll("'", "\"").replaceAll("@", "\n");
    }

    protected String loadJson(@NotNull String filename) throws IOException {
        return FileUtil.loadFile(new File(getTestDataPath(), filename), CharsetToolkit.UTF8, true).trim();
    }
}
