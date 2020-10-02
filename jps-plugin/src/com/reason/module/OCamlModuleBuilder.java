package com.reason.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.SourcePathsBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.*;
import java.util.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Builder to create a new OCaml module. */
public class OCamlModuleBuilder extends ModuleBuilder implements SourcePathsBuilder {

  @Nullable private List<Pair<String, String>> m_sourcePaths;

  @NotNull
  @Override
  public ModuleType getModuleType() {
    return OCamlModuleType.getInstance();
  }

  @Override
  public void setupRootModel(@NotNull ModifiableRootModel rootModel) {
    rootModel.inheritSdk();

    ContentEntry contentEntry = doAddContentEntry(rootModel);
    if (contentEntry != null) {
      List<Pair<String, String>> sourcePaths = getSourcePaths();

      if (sourcePaths != null) {
        for (final Pair<String, String> sourcePath : sourcePaths) {
          String first = sourcePath.first;
          boolean created = new File(first).mkdirs();
          if (created) {
            VirtualFile sourceRoot =
                LocalFileSystem.getInstance()
                    .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(first));
            if (sourceRoot != null) {
              contentEntry.addSourceFolder(sourceRoot, false, sourcePath.second);
            }
          }
        }
      }
    }
  }

  @Nullable
  @Override
  public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
    // return new OCamlModuleWizardStep(context);
    return null;
  }

  @Nullable
  @Override
  public List<Pair<String, String>> getSourcePaths() {
    if (m_sourcePaths == null) {
      List<Pair<String, String>> paths = new ArrayList<>();
      @NonNls String path = getContentEntryPath() + File.separator + "src";
      boolean created = new File(path).mkdirs();
      if (created) {
        paths.add(Pair.create(path, ""));
        return paths;
      }
    }
    return m_sourcePaths;
  }

  @Override
  public void setSourcePaths(@Nullable List<Pair<String, String>> sourcePaths) {
    m_sourcePaths = sourcePaths != null ? new ArrayList<>(sourcePaths) : null;
  }

  @Override
  public void addSourcePath(Pair<String, String> sourcePathInfo) {
    if (m_sourcePaths == null) {
      m_sourcePaths = new ArrayList<>();
    }
    m_sourcePaths.add(sourcePathInfo);
  }
}
