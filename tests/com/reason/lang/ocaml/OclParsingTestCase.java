package com.reason.lang.ocaml;

import com.intellij.lang.LanguageASTFactory;
import com.intellij.psi.stubs.StubElementTypeHolderEP;
import com.reason.lang.BaseParsingTestCase;
import com.reason.lang.core.stub.OclStubBasedElementTypes;
import com.reason.lang.core.type.ORTypes;

public abstract class OclParsingTestCase extends BaseParsingTestCase {
  public ORTypes m_types = OclTypes.INSTANCE;

  public OclParsingTestCase() {
    super("", "ml", new OclParserDefinition());
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    StubElementTypeHolderEP stubElementTypeHolderEP = new StubElementTypeHolderEP();
    stubElementTypeHolderEP.holderClass = OclStubBasedElementTypes.class.getName();
    registerExtension(StubElementTypeHolderEP.EP_NAME, stubElementTypeHolderEP);
    LanguageASTFactory.INSTANCE.addExplicitExtension(OclLanguage.INSTANCE, new OclASTFactory());
  }
}
