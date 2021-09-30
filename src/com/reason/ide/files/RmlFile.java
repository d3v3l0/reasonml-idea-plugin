package com.reason.ide.files;

import com.intellij.openapi.fileTypes.*;
import com.intellij.psi.*;
import com.reason.lang.reason.*;
import org.jetbrains.annotations.*;

public class RmlFile extends FileBase {
    public RmlFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, RmlLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return RmlFileType.INSTANCE;
    }

    @Override
    public @NotNull String toString() {
        return getName();
    }
}
