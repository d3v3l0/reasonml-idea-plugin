package com.reason.ide.files;

import java.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.reason.lang.ModuleHelper;
import com.reason.lang.PsiFileHelper;
import com.reason.lang.core.ORUtil;
import com.reason.lang.core.psi.PsiQualifiedElement;

public abstract class FileBase extends PsiFileBase implements PsiQualifiedElement {

    @NotNull
    private final String m_moduleName;

    FileBase(@NotNull FileViewProvider viewProvider, @NotNull Language language) {
        super(viewProvider, language);
        m_moduleName = ORUtil.fileNameToModuleName(getName());
    }

    @NotNull
    public String getModuleName() {
        return m_moduleName;
    }

    @NotNull
    @Override
    public String getPath() {
        return getModuleName();
    }

    @NotNull
    @Override
    public String getQualifiedName() {
        return getModuleName();
    }

    public boolean isComponent() {
        if (FileHelper.isOCaml(getFileType())) {
            return false;
        }

        return ModuleHelper.isComponent(this);
    }

    @Override
    public PsiElement getNavigationElement() {
        /* ClassCastException ??
        if (isComponent()) {
            PsiLet make = getLetExpression("make");
            if (make != null) {
                return make;
            }
        }
        */
        return super.getNavigationElement();
    }

    @NotNull
    public String shortLocation(@NotNull Project project) {
        return FileHelper.shortLocation(project, getVirtualFile().getPath());
    }

    @NotNull
    public Collection<PsiNamedElement> getExpressions(@Nullable String name) {
        return PsiFileHelper.getExpressions(this, name);
    }

    @NotNull
    public <T extends PsiQualifiedElement> List<T> getExpressions(@Nullable String name, @NotNull Class<T> clazz) {
        List<T> result = new ArrayList<>();

        if (name != null) {
            Collection<T> children = PsiTreeUtil.findChildrenOfType(this, clazz);
            for (T child : children) {
                if (name.equals(child.getQualifiedName())) {
                    result.add(child);
                }
            }
        }

        return result;
    }

    public boolean isInterface() {
        return FileHelper.isInterface(getFileType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileBase fileBase = (FileBase) o;
        return m_moduleName.equals(fileBase.m_moduleName) && isInterface() == fileBase.isInterface();
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_moduleName, isInterface());
    }
}
