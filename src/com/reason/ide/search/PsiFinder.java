package com.reason.ide.search;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiQualifiedNamedElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import com.reason.Log;
import com.reason.build.bs.Bucklescript;
import com.reason.build.bs.BucklescriptManager;
import com.reason.ide.files.FileBase;
import com.reason.lang.core.ORFileType;
import com.reason.lang.core.PsiFileHelper;
import com.reason.lang.core.psi.*;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static com.intellij.psi.search.GlobalSearchScope.allScope;

public final class PsiFinder {

    private static final PsiFinder INSTANCE = new PsiFinder();
    private static final Log LOG = Log.create("finder");

    @NotNull
    public static PsiFinder getInstance() {
        return INSTANCE;
    }

    @Nullable
    public PsiInnerModule findComponent(@NotNull String fqn, @NotNull Project project, @NotNull GlobalSearchScope scope) {
        ModuleComponentIndex index = ModuleComponentIndex.getInstance();

        Collection<PsiInnerModule> modules = index.get(fqn, project, scope);
        if (modules.isEmpty()) {
            return null;
        }

        Bucklescript bucklescript = BucklescriptManager.getInstance(project);
        PsiInnerModule module = modules.iterator().next();
        return bucklescript.isDependency(module.getContainingFile().getVirtualFile()) ? module : null;
    }

    @NotNull
    public Collection<PsiInnerModule> findComponents(@NotNull Project project, @NotNull GlobalSearchScope scope) {
        ModuleComponentIndex index = ModuleComponentIndex.getInstance();
        Bucklescript bucklescript = BucklescriptManager.getInstance(project);

        return index.getAllKeys(project).
                stream().
                map(key -> index.getUnique(key, project, scope)).
                filter(module -> module != null && bucklescript.isDependency(module.getContainingFile().getVirtualFile())).
                collect(Collectors.toList());
    }

    @NotNull
    public Collection<PsiInnerModule> findModules(@NotNull Project project, @NotNull String name, @NotNull GlobalSearchScope scope, @NotNull ORFileType fileType) {
        Map<String/*qn*/, PsiInnerModule> inConfig = new THashMap<>();

        LOG.debug("Find modules, name", name);

        FileModuleIndexService fileModuleIndex = FileModuleIndexService.getService();

        Collection<PsiInnerModule> modules = ModuleIndex.getInstance().get(name, project, scope);
        if (modules.isEmpty()) {
            LOG.debug("  No modules found");
        } else {
            LOG.debug("  modules found", modules.size());
            for (PsiInnerModule module : modules) {
                String filename = ((FileBase) module.getContainingFile()).asModuleName();
                VirtualFile file;

                if (fileType == ORFileType.implementationOnly) {
                    Collection<VirtualFile> implementations = fileModuleIndex.getImplementationFilesWithName(filename, scope);
                    file = implementations.isEmpty() ? null : implementations.iterator().next();
                } else if (fileType == ORFileType.interfaceOnly) {
                    Collection<VirtualFile> interfaces = fileModuleIndex.getInterfaceFilesWithName(filename, scope);
                    file = interfaces.isEmpty() ? null : interfaces.iterator().next();
                } else {
                    file = fileModuleIndex.getFileWithName(filename, scope);
                }

                if (file == null) {
                    LOG.debug("    abandon", module);
                } else {
                    LOG.debug("       keep", module);
                    inConfig.put(module.getQualifiedName(), module);
                }
            }
        }

        return inConfig.values();
    }

    @NotNull
    public Collection<PsiInnerModule> findModules(@NotNull Project project, @NotNull String name, @NotNull ORFileType fileType) {
        return findModules(project, name, GlobalSearchScope.allScope(project), fileType);
    }

    @Nullable
    public PsiInnerModule findModule(@NotNull Project project, @NotNull String name, @NotNull ORFileType fileType) {
        Collection<PsiInnerModule> modules = findModules(project, name, fileType);
        if (!modules.isEmpty()) {
            return modules.iterator().next();
        }

        return null;
    }

    @NotNull
    public Collection<PsiLet> findLets(@NotNull Project project, @NotNull String name, @NotNull ORFileType fileType) {
        return findLowerSymbols("lets", project, name, fileType, IndexKeys.LETS, PsiLet.class);
    }

    @NotNull
    public Collection<PsiVal> findVals(@NotNull Project project, @NotNull String name, @NotNull ORFileType fileType) {
        return findLowerSymbols("vals", project, name, fileType, IndexKeys.VALS, PsiVal.class);
    }

    @NotNull
    public Collection<PsiType> findTypes(@NotNull Project project, @NotNull String name, @NotNull ORFileType fileType) {
        return findLowerSymbols("types", project, name, fileType, IndexKeys.TYPES, PsiType.class);
    }

    @NotNull
    public Collection<PsiExternal> findExternals(@NotNull Project project, @NotNull String name, @NotNull ORFileType fileType) {
        return findLowerSymbols("externals", project, name, fileType, IndexKeys.EXTERNALS, PsiExternal.class);
    }

    private <T extends PsiQualifiedNamedElement> Collection<T> findLowerSymbols(@NotNull String debugName, @NotNull Project project, @NotNull String name, @NotNull ORFileType fileType, @NotNull StubIndexKey<String, T> indexKey, @NotNull Class<T> clazz) {
        Map<String/*qn*/, T> inConfig = new THashMap<>();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Find " + debugName + " name", name);
        }

        GlobalSearchScope scope = allScope(project);
        FileModuleIndexService fileModuleIndex = FileModuleIndexService.getService();

        Collection<T> items = StubIndex.getElements(indexKey, name, project, scope, clazz);
        if (items.isEmpty()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("  No " + debugName + " found");
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("  " + debugName + " found", items.size(), items);
            }
            for (T item : items) {
                String filename = ((FileBase) item.getContainingFile()).asModuleName();
                VirtualFile file;

                if (fileType == ORFileType.implementationOnly) {
                    Collection<VirtualFile> implementations = fileModuleIndex.getImplementationFilesWithName(filename, scope);
                    file = implementations.isEmpty() ? null : implementations.iterator().next();
                } else if (fileType == ORFileType.interfaceOnly) {
                    Collection<VirtualFile> interfaces = fileModuleIndex.getInterfaceFilesWithName(filename, scope);
                    file = interfaces.isEmpty() ? null : interfaces.iterator().next();
                } else {
                    file = fileModuleIndex.getFileWithName(filename, scope);
                }

                if (file != null) {
                    LOG.debug("    keep (in config)", item);
                    inConfig.put(item.getQualifiedName(), item);
                }
            }
        }

        return inConfig.values();
    }

    @Nullable
    public PsiQualifiedNamedElement findModuleAlias(@NotNull Project project, @Nullable String moduleQname) {
        if (moduleQname == null) {
            return null;
        }

        GlobalSearchScope scope = allScope(project);
        Collection<PsiInnerModule> modules = ModuleFqnIndex.getInstance().get(moduleQname.hashCode(), project, scope);

        if (!modules.isEmpty()) {
            PsiInnerModule moduleReference = modules.iterator().next();
            String alias = moduleReference.getAlias();

            if (alias != null) {
                VirtualFile vFile = FileModuleIndexService.getService().getFileWithName(alias, scope);
                if (vFile != null) {
                    return (FileBase) PsiManager.getInstance(project).findFile(vFile);
                }

                modules = ModuleFqnIndex.getInstance().get(alias.hashCode(), project, scope);
                if (!modules.isEmpty()) {
                    PsiInnerModule next = modules.iterator().next();
                    if (next != null) {
                        PsiQualifiedNamedElement nextModuleAlias = findModuleAlias(project, next.getQualifiedName());
                        return nextModuleAlias == null ? next : nextModuleAlias;
                    }
                }
            }
        }

        return null;
    }

    @Nullable
    public PsiQualifiedNamedElement findModuleFromQn(@NotNull Project project, @NotNull String moduleQName) {
        // extract first token of path
        String[] names = moduleQName.split("\\.");

        VirtualFile vFile = FileModuleIndexService.getService().getFileWithName(names[0], allScope(project));
        if (vFile != null) {
            FileBase fileModule = (FileBase) PsiManager.getInstance(project).findFile(vFile);
            if (1 < names.length) {
                PsiQualifiedNamedElement currentModule = fileModule;
                for (int i = 1; i < names.length; i++) {
                    String innerModuleName = names[i];
                    currentModule = currentModule instanceof FileBase ? PsiFileHelper.getModuleExpression((PsiFile) currentModule, innerModuleName) : ((PsiInnerModule) currentModule).getModule(innerModuleName);
                    if (currentModule == null) {
                        return null;
                    }
                }
                return currentModule;
            }

            return fileModule;
        }

        return null;
    }
}
