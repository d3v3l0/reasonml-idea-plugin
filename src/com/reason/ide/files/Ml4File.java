package com.reason.ide.files;

import com.intellij.openapi.fileTypes.*;
import com.intellij.psi.*;
import com.reason.lang.extra.*;
import org.jetbrains.annotations.*;

public class Ml4File extends FileBase {
    public Ml4File(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, OclP4Language.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return Ml4FileType.INSTANCE;
    }

    @Override
    public @NotNull String toString() {
        return Ml4FileType.INSTANCE.getDescription();
    }
}
