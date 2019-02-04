package com.reason.ide.search;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiQualifiedNamedElement;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import com.reason.Log;
import com.reason.build.bs.Bucklescript;
import com.reason.build.bs.BucklescriptManager;
import com.reason.ide.files.*;
import com.reason.lang.core.ORFileType;
import com.reason.lang.core.ORUtil;
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

    private final Log LOG = new Log("finder");

    @NotNull
    public static PsiFinder getInstance() {
        return INSTANCE;
    }

    @Nullable
    public PsiModule findComponent(@NotNull String fqn, @NotNull Project project, @NotNull GlobalSearchScope scope) {
        ModuleComponentIndex index = ModuleComponentIndex.getInstance();

        Collection<PsiModule> modules = index.get(fqn, project, scope);
        if (modules.isEmpty()) {
            return null;
        }

        Bucklescript bucklescript = BucklescriptManager.getInstance(project);
        PsiModule module = modules.iterator().next();
        return bucklescript.isDependency(module.getContainingFile().getVirtualFile()) ? module : null;
    }

    @NotNull
    public Collection<PsiModule> findComponents(@NotNull Project project, @NotNull GlobalSearchScope scope) {
        ModuleComponentIndex index = ModuleComponentIndex.getInstance();
        Bucklescript bucklescript = BucklescriptManager.getInstance(project);

        return index.getAllKeys(project).
                stream().
                map(key -> index.getUnique(key, project, scope)).
                filter(module -> module != null && bucklescript.isDependency(module.getContainingFile().getVirtualFile())).
                collect(Collectors.toList());
    }

    @NotNull
    public Collection<PsiModule> findModules(@NotNull Project project, @NotNull String name, @NotNull GlobalSearchScope scope, @NotNull ORFileType fileType) {
        LOG.debug("Find modules, name", name);

        Map<String/*qn*/, PsiModule> inConfig = new THashMap<>();
        Bucklescript bucklescript = BucklescriptManager.getInstance(project);

        Collection<PsiModule> modules = ModuleIndex.getInstance().get(name, project, scope);
        if (modules.isEmpty()) {
            LOG.debug("  No modules found");
        } else {
            LOG.debug("  modules found", modules.size());
            for (PsiModule module : modules) {
                boolean keepFile;

                VirtualFile virtualFile = module.getContainingFile().getVirtualFile();
                FileType moduleFileType = virtualFile.getFileType();

                if (fileType == ORFileType.implementationOnly) {
                    keepFile = moduleFileType instanceof RmlFileType || moduleFileType instanceof OclFileType;
                } else if (fileType == ORFileType.interfaceOnly) {
                    keepFile = moduleFileType instanceof RmlInterfaceFileType || moduleFileType instanceof OclInterfaceFileType;
                } else {
                    // use interface if there is one, implementation otherwise ... always the case ????
                    // we need a better way (cache through VirtualFileListener ?) to find that info
                    if (moduleFileType instanceof RmlInterfaceFileType || moduleFileType instanceof OclInterfaceFileType) {
                        keepFile = true;
                    } else {
                        String nameWithoutExtension = virtualFile.getNameWithoutExtension();
                        String extension = moduleFileType instanceof RmlFileType ? RmlInterfaceFileType.INSTANCE.getDefaultExtension() :
                                OclInterfaceFileType.INSTANCE.getDefaultExtension();
                        Collection<VirtualFile> interfaceFiles = FilenameIndex
                                .getVirtualFilesByName(project, nameWithoutExtension + "." + extension, scope);
                        keepFile = interfaceFiles.isEmpty();
                    }
                }

                if (keepFile && bucklescript.isDependency(virtualFile)) {
                    LOG.debug("       keep", module);
                    inConfig.put(module.getQualifiedName(), module);
                } else {
                    LOG.debug("    abandon", module);
                }
            }
        }

        return inConfig.values();
    }

    @NotNull
    public Collection<PsiModule> findModules(@NotNull Project project, @NotNull String name, @NotNull ORFileType fileType) {
        return findModules(project, name, GlobalSearchScope.allScope(project), fileType);
    }

    @Nullable
    public PsiModule findModule(@NotNull Project project, @NotNull String name, @NotNull ORFileType fileType) {
        Collection<PsiModule> modules = findModules(project, name, fileType);
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("Find " + debugName + " name", name);
        }

        Map<String/*qn*/, T> inConfig = new THashMap<>();
        Bucklescript bucklescript = BucklescriptManager.getInstance(project);
        GlobalSearchScope scope = allScope(project);

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
                boolean keepFile;

                FileBase containingFile = (FileBase) item.getContainingFile();
                VirtualFile virtualFile = containingFile.getVirtualFile();
                FileType moduleFileType = virtualFile.getFileType();

                if (fileType == ORFileType.implementationOnly) {
                    keepFile = moduleFileType instanceof RmlFileType || moduleFileType instanceof OclFileType;
                } else if (fileType == ORFileType.interfaceOnly) {
                    keepFile = moduleFileType instanceof RmlInterfaceFileType || moduleFileType instanceof OclInterfaceFileType;
                } else {
                    // use interface if there is one, implementation otherwise ... always the case ????
                    // we need a better way (cache through VirtualFileListener ?) to find that info
                    if (moduleFileType instanceof RmlInterfaceFileType || moduleFileType instanceof OclInterfaceFileType) {
                        keepFile = true;
                    } else {
                        String nameWithoutExtension = virtualFile.getNameWithoutExtension();
                        String extension = moduleFileType instanceof RmlFileType ? RmlInterfaceFileType.INSTANCE.getDefaultExtension() :
                                OclInterfaceFileType.INSTANCE.getDefaultExtension();
                        Collection<VirtualFile> interfaceFiles = FilenameIndex
                                .getVirtualFilesByName(project, nameWithoutExtension + "." + extension, scope);
                        keepFile = interfaceFiles.isEmpty();
                    }
                }

                if (keepFile && bucklescript.isDependency(virtualFile)) {
                    LOG.debug("    keep (in config)", item);
                    inConfig.put(item.getQualifiedName(), item);
                }
            }
        }

        return inConfig.values();
    }

    @Nullable
    public FileBase findFileModule(@NotNull Project project, @NotNull String name, @NotNull GlobalSearchScope scope) {
        FileBase file = findFileModuleByExt(project, name, OclInterfaceFileType.INSTANCE.getDefaultExtension(), scope);
        if (file == null) {
            file = findFileModuleByExt(project, name, RmlInterfaceFileType.INSTANCE.getDefaultExtension(), scope);
            if (file == null) {
                file = findFileModuleByExt(project, name, OclFileType.INSTANCE.getDefaultExtension(), scope);
                if (file == null) {
                    file = findFileModuleByExt(project, name, RmlFileType.INSTANCE.getDefaultExtension(), scope);
                }
            }
        }

        //Collection<VirtualFile> files = FileModuleIndexService.getService().getInterfaceFilesWithName(name, scope);
        //if (files.isEmpty()) {
        //    files = FileModuleIndexService.getService().getImplementationFilesWithName(name, scope);
        //}
        //
        //Bucklescript bucklescript = BucklescriptManager.getInstance(project);
        //VirtualFile file2 = null;
        //for (VirtualFile itfile : files) {
        //    if (bucklescript.isDependency(itfile)) {
        //        file2 = itfile;
        //        break;
        //    }
        //}

        return file;
    }

    @Nullable
    private FileBase findFileModuleByExt(@NotNull Project project, @NotNull String name, @NotNull String ext, GlobalSearchScope scope) {
        LOG.debug("Find file module", name, ext);

        FileBase result = null;
        Bucklescript bucklescript = BucklescriptManager.getInstance(project);

        PsiFile[] filesByName = FilenameIndex.getFilesByName(project, name + "." + ext, scope);
        if (0 < filesByName.length) {
            LOG.debug("  found", filesByName);
            for (PsiFile file : filesByName) {
                if (file instanceof FileBase && bucklescript.isDependency(file.getVirtualFile())) {
                    result = (FileBase) file;
                    LOG.debug("  resolved to", file);
                    break;
                }
            }
        }

        if (result == null) {
            // retry with lower case name
            filesByName = FilenameIndex.getFilesByName(project, ORUtil.moduleNameToFileName(name) + "." + ext, scope);
            for (PsiFile file : filesByName) {
                if (file instanceof FileBase && bucklescript.isDependency(file.getVirtualFile())) {
                    result = (FileBase) file;
                    LOG.debug("  resolved to", file);
                    break;
                }
            }
        }

        return result;
    }

    @NotNull
    public Collection<FileBase> findFileModules(@NotNull Project project, @NotNull ORFileType fileType) {
        // All file names are unique in a project, we use the file name in the key
        // Need a better algo to prioritise the paths and not overwrite the correct resolved files
        Map<String, FileBase> result = new THashMap<>();
        Bucklescript bucklescript = BucklescriptManager.getInstance(project);

        Map<String, FileBase> files = new THashMap<>();
        Collection<VirtualFile> rmiFiles;
        Collection<VirtualFile> rmlFiles;
        Collection<VirtualFile> ociFiles;
        Collection<VirtualFile> oclFiles;

        PsiManager psiManager = PsiManager.getInstance(project);
        GlobalSearchScope searchScope = GlobalSearchScope.projectScope(project);

        // List all interface files
        if (fileType != ORFileType.implementationOnly) {
            rmiFiles = FilenameIndex.getAllFilesByExt(project, RmlInterfaceFileType.INSTANCE.getDefaultExtension(), searchScope);
            ociFiles = FilenameIndex.getAllFilesByExt(project, OclInterfaceFileType.INSTANCE.getDefaultExtension(), searchScope);

            for (VirtualFile virtualFile : rmiFiles) {
                if (bucklescript.isDependency(virtualFile)) {
                    files.put(virtualFile.getName(), (FileBase) psiManager.findFile(virtualFile));
                }
            }

            for (VirtualFile virtualFile : ociFiles) {
                if (bucklescript.isDependency(virtualFile)) {

                    files.put(virtualFile.getName(), (FileBase) psiManager.findFile(virtualFile));
                }
            }

            result.putAll(files);
        }

        // List all implementation files
        if (fileType != ORFileType.interfaceOnly) {
            rmlFiles = FilenameIndex.getAllFilesByExt(project, RmlFileType.INSTANCE.getDefaultExtension());
            oclFiles = FilenameIndex.getAllFilesByExt(project, OclFileType.INSTANCE.getDefaultExtension());

            for (VirtualFile virtualFile : rmlFiles) {
                boolean keep = true;
                PsiFile file = psiManager.findFile(virtualFile);

                if (fileType != ORFileType.implementationOnly) {
                    String interfaceName = virtualFile.getNameWithoutExtension() + "." + RmlInterfaceFileType.INSTANCE.getDefaultExtension();
                    if (files.containsKey(interfaceName)) {
                        keep = false;
                    }
                }

                if (keep && file instanceof FileBase && bucklescript.isDependency(virtualFile)) {
                    result.put(virtualFile.getName(), (FileBase) file);
                }
            }

            for (VirtualFile virtualFile : oclFiles) {
                boolean keep = true;
                PsiFile file = psiManager.findFile(virtualFile);

                if (fileType != ORFileType.implementationOnly) {
                    String interfaceName = virtualFile.getNameWithoutExtension() + "." + OclInterfaceFileType.INSTANCE.getDefaultExtension();
                    if (files.containsKey(interfaceName)) {
                        keep = false;
                    }
                }

                if (keep && file instanceof FileBase && bucklescript.isDependency(virtualFile)) {
                    result.put(file.getName(), (FileBase) file);
                }
            }
        }

        return result.values();
    }

    @Nullable
    public PsiQualifiedNamedElement findModuleAlias(@NotNull Project project, @Nullable String moduleQname) {
        if (moduleQname == null) {
            return null;
        }

        GlobalSearchScope scope = allScope(project);
        Collection<PsiModule> modules = ModuleFqnIndex.getInstance().get(moduleQname.hashCode(), project, scope);

        if (!modules.isEmpty()) {
            PsiModule moduleReference = modules.iterator().next();
            String alias = moduleReference.getAlias();

            if (alias != null) {
                FileBase fileModule = findFileModule(project, alias, scope);
                if (fileModule != null) {
                    return fileModule;
                }

                modules = ModuleFqnIndex.getInstance().get(alias.hashCode(), project, scope);
                if (!modules.isEmpty()) {
                    PsiModule next = modules.iterator().next();
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

        FileBase fileModule = findFileModule(project, names[0], GlobalSearchScope.allScope(project));
        if (fileModule != null) {
            if (1 < names.length) {
                PsiQualifiedNamedElement currentModule = fileModule;
                for (int i = 1; i < names.length; i++) {
                    String innerModuleName = names[i];
                    currentModule = currentModule instanceof FileBase ? PsiFileHelper.getModuleExpression((PsiFile) currentModule, innerModuleName) : ((PsiModule) currentModule).getModule(innerModuleName);
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
