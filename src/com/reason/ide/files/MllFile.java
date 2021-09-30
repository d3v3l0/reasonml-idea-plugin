package com.reason.ide.files;

import com.intellij.openapi.fileTypes.*;
import com.intellij.psi.*;
import com.reason.lang.extra.*;
import org.jetbrains.annotations.*;

public class MllFile extends FileBase {
    public MllFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, OclMllLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return MllFileType.INSTANCE;
    }

    @Override
    public @NotNull String toString() {
        return MllFileType.INSTANCE.getDescription();
    }
}
