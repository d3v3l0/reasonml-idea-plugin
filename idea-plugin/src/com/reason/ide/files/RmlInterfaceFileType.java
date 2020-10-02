package com.reason.ide.files;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.reason.lang.reason.RmlLanguage;
import icons.ORIcons;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RmlInterfaceFileType extends LanguageFileType {
  public static final RmlInterfaceFileType INSTANCE = new RmlInterfaceFileType();

  private RmlInterfaceFileType() {
    super(RmlLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "REASON_INTF";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Reason language interface file";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "rei";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return ORIcons.RML_INTERFACE_FILE;
  }

  @NotNull
  @Override
  public String toString() {
    return getName();
  }
}
