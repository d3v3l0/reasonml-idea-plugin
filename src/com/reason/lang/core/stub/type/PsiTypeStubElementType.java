package com.reason.lang.core.stub.type;

import com.intellij.lang.*;
import com.intellij.psi.stubs.*;
import com.intellij.util.io.*;
import com.reason.ide.search.index.*;
import com.reason.lang.core.psi.*;
import com.reason.lang.core.psi.impl.*;
import com.reason.lang.core.stub.*;
import com.reason.lang.core.type.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class PsiTypeStubElementType extends ORStubElementType<PsiTypeStub, PsiType> {
    public static final int VERSION = 11;

    public PsiTypeStubElementType(@NotNull Language language) {
        super("C_TYPE_DECLARATION", language);
    }

    public @NotNull PsiTypeImpl createPsi(@NotNull PsiTypeStub stub) {
        return new PsiTypeImpl(ORTypesUtil.getInstance(getLanguage()), stub, this);
    }

    public @NotNull PsiTypeImpl createPsi(@NotNull ASTNode node) {
        return new PsiTypeImpl(ORTypesUtil.getInstance(getLanguage()), node);
    }

    public @NotNull PsiTypeStub createStub(@NotNull PsiType psi, StubElement parentStub) {
        return new PsiTypeStub(parentStub, this, psi.getName(), psi.getPath(), psi.isAbstract(), psi.isJsObject(), psi.isRecord());
    }

    public void serialize(@NotNull PsiTypeStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        SerializerUtil.writePath(dataStream, stub.getPath());
        dataStream.writeBoolean(stub.isAbstract());
        dataStream.writeBoolean(stub.isJsObject());
        dataStream.writeBoolean(stub.isRecord());
    }

    @NotNull
    public PsiTypeStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        StringRef name = dataStream.readName();
        String[] path = SerializerUtil.readPath(dataStream);
        boolean isAbstract = dataStream.readBoolean();
        boolean isJsObject = dataStream.readBoolean();
        boolean isRecord = dataStream.readBoolean();
        return new PsiTypeStub(parentStub, this, name, path, isAbstract, isJsObject, isRecord);
    }

    public void indexStub(@NotNull PsiTypeStub stub, @NotNull IndexSink sink) {
        String name = stub.getName();
        if (name != null) {
            sink.occurrence(IndexKeys.TYPES, name);
        }

        String fqn = stub.getQualifiedName();
        sink.occurrence(IndexKeys.TYPES_FQN, fqn.hashCode());
    }

    @NotNull
    public String getExternalId() {
        return getLanguage().getID() + "." + super.toString();
    }
}
