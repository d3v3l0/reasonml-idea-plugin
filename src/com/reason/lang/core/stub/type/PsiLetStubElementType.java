package com.reason.lang.core.stub.type;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import com.reason.ide.search.index.IndexKeys;
import com.reason.lang.core.psi.PsiLet;
import com.reason.lang.core.psi.impl.PsiLetImpl;
import com.reason.lang.core.psi.impl.PsiLowerIdentifier;
import com.reason.lang.core.stub.PsiLetStub;
import com.reason.lang.core.type.ORTypesUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiLetStubElementType extends ORStubElementType<PsiLetStub, PsiLet> {

  public PsiLetStubElementType(@Nullable Language language) {
    super("C_LET_DECLARATION", language);
  }

  @NotNull
  public PsiLetImpl createPsi(@NotNull PsiLetStub stub) {
    return new PsiLetImpl(ORTypesUtil.getInstance(getLanguage()), stub, this);
  }

  @Override
  public @NotNull PsiElement createPsi(ASTNode node) {
    return new PsiLetImpl(ORTypesUtil.getInstance(getLanguage()), node);
  }

  @NotNull
  public PsiLetStub createStub(@NotNull PsiLet psi, StubElement parentStub) {
    List<String> deconstructedNames = new ArrayList<>();
    if (psi.isDeconsruction()) {
      List<PsiElement> elements = psi.getDeconstructedElements();
      for (PsiElement element : elements) {
        if (element instanceof PsiLowerIdentifier) {
          deconstructedNames.add(element.getText());
        }
      }
    }
    return new PsiLetStub(
        parentStub,
        this,
        psi.getName(),
        psi.getQualifiedName(),
        psi.getAlias(),
        psi.isFunction(),
        deconstructedNames);
  }

  public void serialize(@NotNull PsiLetStub stub, @NotNull StubOutputStream dataStream)
      throws IOException {
    dataStream.writeName(stub.getName());
    dataStream.writeUTFFast(stub.getQualifiedName());
    dataStream.writeBoolean(stub.isFunction());

    List<String> deconstructionNames = stub.getDeconstructionNames();
    dataStream.writeByte(deconstructionNames.size());
    if (!deconstructionNames.isEmpty()) {
      for (String name : deconstructionNames) {
        dataStream.writeUTFFast(name);
      }
    }

    String alias = stub.getAlias();
    dataStream.writeBoolean(alias != null);
    if (alias != null) {
      dataStream.writeUTFFast(stub.getAlias());
    }
  }

  @NotNull
  public PsiLetStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub)
      throws IOException {
    StringRef name = dataStream.readName();
    String qname = dataStream.readUTFFast();
    boolean isFunction = dataStream.readBoolean();

    List<String> deconstructionNames = new ArrayList<>();
    byte namesCount = dataStream.readByte();
    if (namesCount > 0) {
      for (int i = 0; i < namesCount; i++) {
        deconstructionNames.add(dataStream.readUTFFast());
      }
    }

    String alias = null;
    boolean isAlias = dataStream.readBoolean();
    if (isAlias) {
      alias = dataStream.readUTFFast();
    }

    return new PsiLetStub(parentStub, this, name, qname, alias, isFunction, deconstructionNames);
  }

  public void indexStub(@NotNull PsiLetStub stub, @NotNull IndexSink sink) {
    List<String> deconstructionNames = stub.getDeconstructionNames();
    if (deconstructionNames.isEmpty()) {
      String name = stub.getName();
      if (name != null) {
        sink.occurrence(IndexKeys.LETS, name);
      }
    } else {
      for (String name : deconstructionNames) {
        sink.occurrence(IndexKeys.LETS, name);
      }
    }

    String fqn = stub.getQualifiedName();
    if (fqn != null) {
      sink.occurrence(IndexKeys.LETS_FQN, fqn.hashCode());
    }
  }

  @NotNull
  public String getExternalId() {
    return getLanguage().getID() + "." + super.toString();
  }
}
