package com.reason.ide.console;

import com.intellij.icons.*;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.*;
import org.jetbrains.annotations.*;

public class RescriptBuildAction extends CompilerAction {
    public RescriptBuildAction() {
        super("Build", "Build", AllIcons.Actions.Compile);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project != null) {
            doAction(project, CliType.Rescript.MAKE);
        }
    }
}
