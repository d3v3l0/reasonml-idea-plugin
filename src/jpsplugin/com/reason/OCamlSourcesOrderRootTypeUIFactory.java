package jpsplugin.com.reason;

import com.intellij.icons.*;
import com.intellij.openapi.fileChooser.*;
import com.intellij.openapi.fileTypes.*;
import com.intellij.openapi.progress.*;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.projectRoots.ui.*;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.libraries.*;
import com.intellij.openapi.roots.libraries.ui.*;
import com.intellij.openapi.roots.libraries.ui.impl.*;
import com.intellij.openapi.roots.ui.*;
import com.intellij.openapi.vfs.*;
import com.intellij.util.containers.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class OCamlSourcesOrderRootTypeUIFactory implements OrderRootTypeUIFactory {
    @Override
    public SdkPathEditor createPathEditor(final Sdk sdk) {
        FileChooserDescriptor descriptor =
                new FileChooserDescriptor(true, true, false, false, false, true);
        return new OCamlSourcesOrderRootTypeUIFactory.SourcesPathEditor(descriptor);
    }

    @Override
    public @NotNull Icon getIcon() {
        return AllIcons.Nodes.Package;
    }

    @Override
    public @NotNull String getNodeText() {
        return "Sources";
    }

    private static class SourcesPathEditor extends SdkPathEditor {
        SourcesPathEditor(@NotNull FileChooserDescriptor descriptor) {
            super("Sourcepath", OCamlSourcesOrderRootType.getInstance(), descriptor);
        }

        @Override
        protected @NotNull VirtualFile[] adjustAddedFileSet(final Component component, final VirtualFile[] files) {
            java.util.List<OrderRoot> orderRoots =
                    RootDetectionUtil.detectRoots(
                            Arrays.asList(files),
                            component,
                            null,
                            new OCamlRootsDetector(),
                            new OrderRootType[]{OCamlSourcesOrderRootType.getInstance()});

            List<VirtualFile> result = new ArrayList<>();
            for (OrderRoot root : orderRoots) {
                result.add(root.getFile());
            }

            return VfsUtil.toVirtualFileArray(result);
        }

        private static class OCamlRootsDetector extends LibraryRootsDetector {
            @Override
            public @NotNull Collection<DetectedLibraryRoot> detectRoots(@NotNull VirtualFile rootCandidate, @NotNull ProgressIndicator progressIndicator) {
                List<DetectedLibraryRoot> result = new ArrayList<>();
                OrderRootType OCAML_SOURCES = OCamlSourcesOrderRootType.getInstance();
                Collection<VirtualFile> files = suggestOCamlRoots(rootCandidate, progressIndicator);
                for (VirtualFile file : files) {
                    result.add(new DetectedLibraryRoot(file, OCAML_SOURCES, false));
                }
                return result;
            }

            @NotNull
            List<VirtualFile> suggestOCamlRoots(
                    @NotNull VirtualFile dir, @NotNull final ProgressIndicator progressIndicator) {
                if (!dir.isDirectory()) {
                    return ContainerUtil.emptyList();
                }

                final FileTypeManager typeManager = FileTypeManager.getInstance();
                final ArrayList<VirtualFile> foundDirectories = new ArrayList<>();
                try {
                    VfsUtilCore.visitChildrenRecursively(
                            dir,
                            new VirtualFileVisitor<VirtualFile>() {
                                @Override
                                public @NotNull Result visitFileEx(@NotNull VirtualFile file) {
                                    progressIndicator.checkCanceled();

                                    if (!file.isDirectory()) {
                                        FileType type = typeManager.getFileTypeByFileName(file.getName());

                                        if (type.getDefaultExtension().equals("ml")) {
                                            VirtualFile root = file.getParent();
                                            if (root != null) {
                                                foundDirectories.add(root);
                                                return skipTo(root);
                                            }
                                        }
                                    }

                                    return CONTINUE;
                                }
                            });
                } catch (ProcessCanceledException ignore) {
                }

                return foundDirectories;
            }

            @Override
            public String getRootTypeName(@NotNull LibraryRootType rootType) {
                if (OCamlSourcesOrderRootType.getInstance().equals(rootType.getType())
                        && !rootType.isJarDirectory()) {
                    return "sources";
                }
                return null;
            }
        }
    }
}
