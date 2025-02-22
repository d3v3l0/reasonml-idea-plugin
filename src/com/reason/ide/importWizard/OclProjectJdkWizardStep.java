package com.reason.ide.importWizard;

import com.intellij.ide.util.*;
import com.intellij.ide.util.projectWizard.*;
import com.intellij.ide.wizard.*;
import com.intellij.openapi.fileChooser.*;
import com.intellij.openapi.options.*;
import com.intellij.openapi.progress.*;
import com.intellij.openapi.project.*;
import com.intellij.openapi.projectRoots.*;
import com.intellij.openapi.projectRoots.impl.*;
import com.intellij.openapi.roots.ui.configuration.*;
import com.intellij.openapi.roots.ui.configuration.projectRoot.*;
import com.intellij.openapi.ui.*;
import com.intellij.openapi.util.*;
import com.intellij.openapi.vfs.*;
import jpsplugin.com.reason.*;
import jpsplugin.com.reason.sdk.*;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

import static com.intellij.openapi.roots.ui.configuration.JdkComboBox.*;

public class OclProjectJdkWizardStep extends ModuleWizardStep {

    private static final String SDK_HOME = "reasonml.sdk.home";
    private static final String[] SDKS =
            new String[]{
                    "4.02.3", "4.03.0", "4.04.2", "4.05.0", "4.06.1", "4.07.1", "4.08.1", "4.09.0", "4.10.0"
            };
    private static final Log LOG = Log.create("import.sdk");

    private JPanel c_pnlRoot;
    private JRadioButton c_rdSelectExisting;
    private JRadioButton c_rdDownloadSdk;
    private JComboBox<String> c_selDownload;
    private @Nullable JdkComboBox c_selExistingSdk;
    private TextFieldWithBrowseButton c_sdkHome;

    private final WizardContext m_context;

    OclProjectJdkWizardStep(final WizardContext context) {
        m_context = context;
    }

    private void createUIComponents() {
        ProjectSdksModel model = new ProjectSdksModel();
        Project project = ProjectManager.getInstance().getDefaultProject();

        model.reset(project);
        Condition<SdkTypeId> filter = sdkTypeId -> OCamlSdkType.ID.equals(sdkTypeId.getName());
        c_selExistingSdk = new JdkComboBox(project, model, filter, getSdkFilter(filter), filter, null);
    }

    @Override
    public void _init() {
        for (String sdk : SDKS) {
            c_selDownload.addItem(sdk);
        }

        if (c_selExistingSdk != null && c_selExistingSdk.getItemCount() == 0) {
            c_rdSelectExisting.setEnabled(false);
            c_selExistingSdk.setEnabled(false);
            c_rdDownloadSdk.setSelected(true);
        }

        String value = PropertiesComponent.getInstance().getValue(SDK_HOME);
        if (value == null) {
            VirtualFile userHomeDir = VfsUtil.getUserHomeDir();
            value = userHomeDir == null ? "" : userHomeDir.getCanonicalPath() + "/odk";
        }
        c_sdkHome.setText(value);

        c_sdkHome.addBrowseFolderListener(
                "Choose Sdk Home Directory: ",
                null,
                m_context.getProject(),
                FileChooserDescriptorFactory.createSingleFolderDescriptor());
    }

    @Override
    public JComponent getComponent() {
        return c_pnlRoot;
    }

    @Override
    public boolean validate() throws ConfigurationException {
        VirtualFileSystem fileSystem = LocalFileSystem.getInstance();

        VirtualFile sdkHome = fileSystem.findFileByPath(c_sdkHome.getText().trim());
        if (sdkHome == null) {
            throw new ConfigurationException("Can't find sdk home directory, make sure it exists");
        }
        if (!sdkHome.isDirectory()) {
            throw new ConfigurationException("Sdk home is not a directory");
        }
        if (!sdkHome.isWritable()) {
            throw new ConfigurationException("Sdk home is not writable");
        }

        return super.validate();
    }

    @Override
    public void updateDataModel() {
        String sdkHome = c_sdkHome.getText().trim();
        if (!sdkHome.isEmpty()) {
            PropertiesComponent.getInstance().setValue(SDK_HOME, sdkHome);
        }
    }

    @Override
    public void onWizardFinished() throws CommitStepException {
        Sdk odk = null;

        if (c_rdSelectExisting.isSelected()) {
            JdkComboBox.JdkComboBoxItem selectedItem =
                    c_selExistingSdk == null ? null : c_selExistingSdk.getSelectedItem();
            odk = selectedItem == null ? null : selectedItem.getJdk();
        } else if (c_rdDownloadSdk.isSelected()) {
            String selectedSdk = (String) c_selDownload.getSelectedItem();
            String sdkHomeValue = PropertiesComponent.getInstance().getValue(SDK_HOME);
            if (sdkHomeValue != null) {
                VirtualFileSystem fileSystem = LocalFileSystem.getInstance();
                VirtualFile sdkHome = fileSystem.findFileByPath(sdkHomeValue);

                if (selectedSdk != null && sdkHome != null) {
                    int pos = selectedSdk.lastIndexOf('.');
                    String patch = selectedSdk.substring(pos + 1);
                    String majorMinor = selectedSdk.substring(0, pos);
                    pos = majorMinor.lastIndexOf('.');
                    String major = majorMinor.substring(0, pos);
                    String minor = majorMinor.substring(pos + 1);

                    // Download SDK from distribution site
                    LOG.debug("Download SDK", selectedSdk);
                    ProgressManager.getInstance()
                            .run(SdkDownloader.modalTask(major, minor, patch, sdkHome, m_context.getProject()));

                    // Create SDK
                    LOG.debug("Create SDK", selectedSdk);
                    File targetSdkLocation = new File(sdkHome.getCanonicalPath(), "ocaml-" + selectedSdk);
                    odk = SdkConfigurationUtil.createAndAddSDK(targetSdkLocation.getAbsolutePath(), new OCamlSdkType());
                    if (odk != null) {
                        SdkModificator odkModificator = odk.getSdkModificator();

                        // must be set after home path, otherwise setting home path clears the version string
                        odkModificator.setVersionString(selectedSdk);
                        odkModificator.setName("OCaml (sources only) " + major);
                        try {
                            addSdkSources(odkModificator, targetSdkLocation);
                        } catch (IOException e) {
                            throw new CommitStepException(e.getMessage());
                        }

                        odkModificator.commitChanges();
                    }
                }
            }
        }

        // update selected sdk in builder
        ProjectBuilder builder = m_context.getProjectBuilder();
        if (odk != null && builder instanceof DuneProjectImportBuilder) {
            ((DuneProjectImportBuilder) builder).setModuleSdk(odk);
        }
    }

    private void addSdkSources(@NotNull SdkModificator odkModificator, @NotNull File targetSdkLocation) throws IOException {
        VirtualFileSystem fileSystem = LocalFileSystem.getInstance();

        Files.walkFileTree(targetSdkLocation.toPath(), new SimpleFileVisitor<>() {
            @Override
            public @NotNull FileVisitResult visitFile(@NotNull Path path, BasicFileAttributes basicFileAttributes) {
                VirtualFile file = fileSystem.findFileByPath(path.toString());
                if (file != null) {
                    odkModificator.addRoot(file, OCamlSourcesOrderRootType.getInstance());
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
