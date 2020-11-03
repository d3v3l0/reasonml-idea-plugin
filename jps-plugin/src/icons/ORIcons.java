package icons;

import com.intellij.icons.AllIcons;

import javax.swing.*;

import static com.intellij.openapi.util.IconLoader.getIcon;

/*
 * https://www.jetbrains.org/intellij/sdk/docs/reference_guide/work_with_icons_and_images.html
 * https://jetbrains.design/intellij/principles/icons/
 * https://jetbrains.design/intellij/resources/icons_list/
 *
 * Node, action, filetype : 16x16
 * Tool window            : 13x13
 * Editor gutter          : 12x12
 * Font                   : Gotham
 */
public class ORIcons {
    // TOOL WINDOW

    public static final Icon BUCKLESCRIPT_TOOL = getIcon("/icons/bucklescriptTool.svg");
    public static final Icon DUNE_TOOL = getIcon("/icons/duneTool.svg");
    public static final Icon ESY_TOOL = getIcon("/icons/esyTool.svg");

    // GUTTER

    public static final Icon IMPLEMENTED = AllIcons.Gutter.ImplementedMethod;
    public static final Icon IMPLEMENTING = AllIcons.Gutter.ImplementingMethod;

    // OTHER

    public static final Icon OCAML = getIcon("/icons/ocamlLogo.svg");
    public static final Icon DUNE = getIcon("/icons/duneLogo.svg");
    public static final Icon ESY = getIcon("/icons/esy.svg");

    public static final Icon BS_FILE = getIcon("/icons/bsFile.svg");
    public static final Icon ESY_FILE = getIcon("/icons/esyFile.svg");
    public static final Icon RML_FILE = getIcon("/icons/reFile.svg");
    public static final Icon RML_INTERFACE_FILE = getIcon("/icons/reiFile.svg");
    public static final Icon NS_FILE = getIcon("/icons/resFile.svg");
    public static final Icon NS_INTERFACE_FILE = getIcon("/icons/resiFile.svg");
    public static final Icon OCL_FILE = getIcon("/icons/mlFile.svg");
    public static final Icon OCL_INTERFACE_FILE = getIcon("/icons/mliFile.png");
    public static final Icon DUNE_FILE = getIcon("/icons/duneFile.svg");

    public static final Icon RML_FILE_MODULE = getIcon("/icons/reasonRed.svg");
    public static final Icon RML_FILE_MODULE_INTERFACE = getIcon("/icons/reasonBlue.svg");
    public static final Icon OCL_FILE_MODULE = getIcon("/icons/ocamlLogo.svg");
    public static final Icon OCL_FILE_MODULE_INTERFACE = getIcon("/icons/ocamlBlue.png");

    public static final Icon RML_BLUE = getIcon("/icons/reasonBlue.svg");
    public static final Icon RML_YELLOW = getIcon("/icons/reasonYellow.svg");

    public static final Icon OCL_MODULE = getIcon("/icons/ocamlModule.svg");
    public static final Icon OCL_SDK = getIcon("/icons/ocamlSdk.svg");
    public static final Icon OCL_BLUE_FILE = getIcon("/icons/ocamlBlue.png");
    public static final Icon OCL_GREEN_FILE = getIcon("/icons/ocamlGreen.png");

    public static final Icon TYPE = getIcon("/icons/type.svg");
    public static final Icon VARIANT = getIcon("/icons/variant.svg");
    public static final Icon ANNOTATION = AllIcons.Nodes.Annotationtype;

    public static final Icon INNER_MODULE = getIcon("/icons/innerModule.svg");
    public static final Icon INNER_MODULE_INTF = getIcon("/icons/innerModuleIntf.svg");
    public static final Icon MODULE_TYPE = getIcon("/icons/javaModuleType.svg");
    public static final Icon FUNCTOR = AllIcons.Nodes.Artifact;
    public static final Icon LET = AllIcons.Nodes.Variable;
    public static final Icon VAL = AllIcons.Nodes.Variable;
    public static final Icon ATTRIBUTE = AllIcons.Nodes.Property;
    public static final Icon FUNCTION = AllIcons.Nodes.Function;
    public static final Icon METHOD = AllIcons.Nodes.Method;
    public static final Icon CLASS = AllIcons.Nodes.Class;
    public static final Icon EXCEPTION = AllIcons.Nodes.ExceptionClass;
    public static final Icon EXTERNAL = AllIcons.Nodes.Enum;
    public static final Icon OBJECT = AllIcons.Json.Object;

    public static final Icon VIRTUAL_NAMESPACE = AllIcons.Actions.GroupByPackage;
    public static final Icon OPEN = AllIcons.Actions.GroupByModule;
    public static final Icon INCLUDE = AllIcons.Actions.GroupByModule;

    public static final Icon OVERLAY_MANDATORY = AllIcons.Ide.ErrorPoint;
    public static final Icon OVERLAY_EXECUTE = AllIcons.Nodes.RunnableMark;

    // REPL

    public static final Icon REPL = AllIcons.Nodes.Console;
}
