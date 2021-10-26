package com.reason.ide.hints;

import com.intellij.lang.*;
import com.intellij.lang.parameterInfo.*;
import com.intellij.openapi.util.*;
import com.intellij.psi.*;
import com.intellij.psi.util.*;
import com.reason.lang.core.psi.*;
import com.reason.lang.core.psi.impl.*;
import com.reason.lang.core.type.*;
import jpsplugin.com.reason.*;
import org.jetbrains.annotations.*;

public abstract class ORParameterInfoHandler implements ParameterInfoHandler<PsiFunctionCallParams, ORParameterInfoHandler.ArgumentsDescription> {
    protected static final Log LOG = Log.create("param");
    protected final ORTypes myTypes;

    protected ORParameterInfoHandler(@NotNull ORTypes types) {
        myTypes = types;
    }

    abstract @Nullable ArgumentsDescription[] calculateParameterInfo(@Nullable PsiFunctionCallParams paramsOwner);

    abstract @Nullable PsiFunctionCallParams findFunctionParams(@NotNull PsiFile file, int offset);

    @Override
    public @Nullable PsiFunctionCallParams findElementForParameterInfo(@NotNull CreateParameterInfoContext context) {
        PsiFunctionCallParams paramsOwner = findFunctionParams(context.getFile(), context.getOffset());
        ArgumentsDescription[] parameterInfo = calculateParameterInfo(paramsOwner);
        if (parameterInfo != null) {
            context.setItemsToShow(parameterInfo);
            //context.setHighlightedElement(paramsOwner, ?);
        }
        return paramsOwner;
    }

    @Override
    public @Nullable PsiFunctionCallParams findElementForUpdatingParameterInfo(@NotNull UpdateParameterInfoContext context) {
        PsiFunctionCallParams paramsOwner = findFunctionParams(context.getFile(), context.getOffset());
        if (paramsOwner != null) {
            PsiElement currentOwner = context.getParameterOwner();
            if (currentOwner == null || currentOwner == paramsOwner) {
                return paramsOwner;
            }
        }

        return null;
    }

    @Override
    public void showParameterInfo(@NotNull PsiFunctionCallParams paramsOwner, @NotNull CreateParameterInfoContext context) {
        context.showHint(paramsOwner, paramsOwner.getTextOffset(), this);
    }

    @Override
    public void updateParameterInfo(@NotNull PsiFunctionCallParams paramsOwner, @NotNull UpdateParameterInfoContext context) {
        if (context.getParameterOwner() == null || paramsOwner.equals(context.getParameterOwner())) {
            int paramIndex = ParameterInfoUtils.getCurrentParameterIndex(paramsOwner.getNode(), context.getOffset(), myTypes.COMMA);

            // find arrow
            PsiLowerSymbol functionName = PsiTreeUtil.getPrevSiblingOfType(paramsOwner, PsiLowerSymbol.class);
            PsiElement previous = functionName == null ? null : skipPreviousPath(functionName);
            boolean pipeFirst = previous != null && previous.getNode().getElementType() == myTypes.RIGHT_ARROW;
            //boolean pipeLast = false;

            context.setParameterOwner(paramsOwner);
            context.setCurrentParameter(pipeFirst ? paramIndex + 1 : paramIndex);
        } else {
            context.removeHint();
        }
    }

    @Override
    public void updateUI(@Nullable RmlParameterInfoHandler.ArgumentsDescription arguments, @NotNull ParameterInfoUIContext context) {
        if (arguments == null) {
            context.setUIComponentEnabled(false);
            return;
        }

        int paramIndex = context.getCurrentParameterIndex();
        TextRange paramRange = arguments.getRange(paramIndex);

        context.setupUIComponentPresentation(
                arguments.getText(),
                paramRange.getStartOffset(),
                paramRange.getEndOffset(),
                !context.isUIComponentEnabled(),
                false,
                true,
                context.getDefaultParameterColor());
    }

    @Nullable PsiElement skipPreviousPath(@NotNull PsiElement element) {
        // previous sibling without considering the skipped elements
        PsiElement prevSibling = element.getPrevSibling();
        while (prevSibling != null) {
            ASTNode prevNode = prevSibling.getNode();
            if (prevNode == null) {
                break;
            }
            if (!(prevSibling instanceof PsiLowerSymbol) && !(prevSibling instanceof PsiUpperSymbol) && prevNode.getElementType() != myTypes.DOT) {
                break;
            }

            prevSibling = prevSibling.getPrevSibling();
        }
        return prevSibling;
    }

    @Nullable PsiSignature resolveSignature(@NotNull PsiSignatureElement signatureElement) {
        PsiSignature signature = signatureElement.getSignature();
        if (signature == null) {
            if ((signatureElement instanceof PsiLet) && ((PsiLet) signatureElement).getAlias() != null) {
                PsiElement resolvedRef = ((PsiLet) signatureElement).resolveAlias();
                PsiElement resolvedAlias = resolvedRef instanceof PsiLowerIdentifier ? resolvedRef.getParent() : null;
                if (resolvedAlias instanceof PsiSignatureElement) {
                    signature = ((PsiSignatureElement) resolvedAlias).getSignature();
                }
                // resolve cmt ???
            }
        }
        return signature;
    }

    public static class ArgumentsDescription {
        PsiQualifiedNamedElement myResolvedElement;
        PsiSignature mySignature;

        String[] myArguments; // ?

        public ArgumentsDescription(PsiQualifiedNamedElement resolvedElement, @NotNull PsiSignature signature) {
            myResolvedElement = resolvedElement;
            mySignature = signature;

            myArguments = signature.getItems().stream().map(PsiElement::getText).toArray(String[]::new);
        }

        String getText() {
            return mySignature.getText();
            // signature.asText(ORLanguageProperties.cast(resolvedElement.getLanguage())); pb with textrange
        }

        @NotNull TextRange getRange(int index) {
            if (index < 0 || index >= myArguments.length) {
                return TextRange.EMPTY_RANGE;
            }

            return mySignature.getItems().get(index).getTextRangeInParent();
        }
    }
}
