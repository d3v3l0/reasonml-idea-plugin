package com.reason.ide.files;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CmiFileType implements FileType {
    public static final CmiFileType INSTANCE = new CmiFileType();

    @NotNull
    @Override
    public String getName() {
        return "cmi";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "cmi";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "cmi";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public boolean isBinary() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile file, @NotNull byte[] content) {
        return null;
    }
}
