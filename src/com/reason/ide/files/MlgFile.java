package com.reason.ide.files;

import com.intellij.openapi.fileTypes.*;
import com.intellij.psi.*;
import com.reason.lang.extra.*;
import org.jetbrains.annotations.*;

public class MlgFile extends FileBase {
    public MlgFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, OclMlgLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return MlgFileType.INSTANCE;
    }

    @Override
    public @NotNull String toString() {
        return MlgFileType.INSTANCE.getDescription();
    }
}
