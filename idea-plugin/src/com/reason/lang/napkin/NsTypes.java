package com.reason.lang.napkin;

import com.reason.lang.core.stub.type.PsiExceptionStubElementType;
import com.reason.lang.core.stub.type.PsiExternalStubElementType;
import com.reason.lang.core.stub.type.PsiFakeModuleStubElementType;
import com.reason.lang.core.stub.type.PsiFunctorModuleStubElementType;
import com.reason.lang.core.stub.type.PsiInnerModuleStubElementType;
import com.reason.lang.core.stub.type.PsiLetStubElementType;
import com.reason.lang.core.stub.type.PsiParameterStubElementType;
import com.reason.lang.core.stub.type.PsiRecordFieldStubElementType;
import com.reason.lang.core.stub.type.PsiTypeStubElementType;
import com.reason.lang.core.stub.type.PsiValStubElementType;
import com.reason.lang.core.stub.type.PsiVariantStubElementType;
import com.reason.lang.core.type.ORCompositeElementType;
import com.reason.lang.core.type.ORTokenElementType;
import com.reason.lang.core.type.ORTypes;

public class NsTypes extends ORTypes {

  public static final NsTypes INSTANCE = new NsTypes();

  private NsTypes() {
    C_FAKE_MODULE = new PsiFakeModuleStubElementType("C_FAKE_MODULE", NsLanguage.INSTANCE);

    // Stubbed expressions

    C_EXCEPTION_DECLARATION =
        new PsiExceptionStubElementType("C_EXCEPTION_DECLARATION", NsLanguage.INSTANCE);
    C_TYPE_DECLARATION = new PsiTypeStubElementType("C_TYPE_DECLARATION", NsLanguage.INSTANCE);
    C_EXTERNAL_DECLARATION =
        new PsiExternalStubElementType("C_EXTERNAL_DECLARATION", NsLanguage.INSTANCE);
    C_LET_DECLARATION = new PsiLetStubElementType("C_LET_DECLARATION", NsLanguage.INSTANCE);
    C_MODULE_DECLARATION =
        new PsiInnerModuleStubElementType("C_MODULE_DECLARATION", NsLanguage.INSTANCE);
    C_VAL_DECLARATION = new PsiValStubElementType("C_VAL_DECLARATION", NsLanguage.INSTANCE);

    // Stubbed element types

    C_FUN_PARAM = new PsiParameterStubElementType("C_FUN_PARAM", NsLanguage.INSTANCE);
    C_FUNCTOR = new PsiFunctorModuleStubElementType("C_FUNCTOR", NsLanguage.INSTANCE);
    C_FUNCTOR_PARAM = new PsiParameterStubElementType("C_FUNCTOR_PARAM", NsLanguage.INSTANCE);
    C_RECORD_FIELD = new PsiRecordFieldStubElementType("C_RECORD_FIELD", NsLanguage.INSTANCE);
    C_VARIANT_DECLARATION = new PsiVariantStubElementType("C_VARIANT_DECL", NsLanguage.INSTANCE);

    // Composite element types

    C_ANNOTATION = new ORCompositeElementType("C_ANNOTATION_EXPR", NsLanguage.INSTANCE);
    C_MIXIN_FIELD = new ORCompositeElementType("C_MIXIN_FIELD", NsLanguage.INSTANCE);
    C_MODULE_PATH = new ORCompositeElementType("C_MODULE_PATH", NsLanguage.INSTANCE);
    C_ASSERT_STMT = new ORCompositeElementType("C_ASSERT_STMT", NsLanguage.INSTANCE);
    C_BINARY_CONDITION = new ORCompositeElementType("C_BIN_CONDITION", NsLanguage.INSTANCE);
    C_CLASS_DECLARATION = new ORCompositeElementType("C_CLASS_DECLARATION", NsLanguage.INSTANCE);
    C_CLASS_CONSTR = new ORCompositeElementType("C_CLASS_CONSTR", NsLanguage.INSTANCE);
    C_CLASS_PARAMS = new ORCompositeElementType("C_CLASS_PARAMS", NsLanguage.INSTANCE);
    C_CLASS_FIELD = new ORCompositeElementType("C_CLASS_FIELD", NsLanguage.INSTANCE);
    C_CLASS_METHOD = new ORCompositeElementType("C_CLASS_METHOD", NsLanguage.INSTANCE);
    C_CONSTRAINTS = new ORCompositeElementType("C_CONSTRAINTS", NsLanguage.INSTANCE);
    C_CONSTRAINT = new ORCompositeElementType("C_CONSTRAINT", NsLanguage.INSTANCE);
    C_DECONSTRUCTION = new ORCompositeElementType("C_DECONSTRUCTION", NsLanguage.INSTANCE);
    C_DIRECTIVE = new ORCompositeElementType("C_DIRECTIVE", NsLanguage.INSTANCE);
    C_DO_LOOP = new ORCompositeElementType("C_DO_LOOP", NsLanguage.INSTANCE);
    C_LOWER_IDENTIFIER = new ORCompositeElementType("C_LOWER_IDENTIFIER", NsLanguage.INSTANCE);
    C_UPPER_IDENTIFIER = new ORCompositeElementType("C_UPPER_IDENTIFIER", NsLanguage.INSTANCE);
    C_FUN_CALL_PARAMS = new ORCompositeElementType("C_FUN_CALL_PARAMS", NsLanguage.INSTANCE);
    C_FUN_EXPR = new ORCompositeElementType("C_FUN_EXPR", NsLanguage.INSTANCE);
    C_FUN_PARAMS = new ORCompositeElementType("C_FUN_PARAMS", NsLanguage.INSTANCE);
    C_FUN_PARAM_BINDING = new ORCompositeElementType("C_FUN_PARAM_BINDING", NsLanguage.INSTANCE);
    C_FUN_BODY = new ORCompositeElementType("C_FUN_BODY", NsLanguage.INSTANCE);
    C_FUNCTOR_BINDING = new ORCompositeElementType("C_FUNCTOR_BINDING", NsLanguage.INSTANCE);
    C_FUNCTOR_CALL = new ORCompositeElementType("C_FUNCTOR_CALL", NsLanguage.INSTANCE);
    C_FUNCTOR_PARAMS = new ORCompositeElementType("C_FUNCTOR_PARAMS", NsLanguage.INSTANCE);
    C_FUNCTOR_RESULT = new ORCompositeElementType("C_FUNCTOR_RESULT", NsLanguage.INSTANCE);
    C_IF = new ORCompositeElementType("C_IF", NsLanguage.INSTANCE);
    C_IF_THEN_SCOPE = new ORCompositeElementType("C_IF_THEN_SCOPE", NsLanguage.INSTANCE);
    C_INCLUDE = new ORCompositeElementType("C_INCLUDE", NsLanguage.INSTANCE);
    C_INTERPOLATION_EXPR = new ORCompositeElementType("C_INTERPOLATION_EXPR", NsLanguage.INSTANCE);
    C_INTERPOLATION_PART = new ORCompositeElementType("C_INTERPOLATION_PART", NsLanguage.INSTANCE);
    C_INTERPOLATION_REF = new ORCompositeElementType("C_INTERPOLATION_REF", NsLanguage.INSTANCE);
    C_JS_OBJECT = new ORCompositeElementType("C_JS_OBJECT", NsLanguage.INSTANCE);
    C_LET_ATTR = new ORCompositeElementType("C_LET_ATTR", NsLanguage.INSTANCE);
    C_LET_BINDING = new ORCompositeElementType("C_LET_BINDING", NsLanguage.INSTANCE);
    C_LOCAL_OPEN = new ORCompositeElementType("C_LOCAL_OPEN", NsLanguage.INSTANCE);
    C_TYPE_VARIABLE = new ORCompositeElementType("C_TYPE_VARIABLE", NsLanguage.INSTANCE);
    C_LOWER_SYMBOL = new ORCompositeElementType("C_LOWER_SYMBOL", NsLanguage.INSTANCE);
    C_MACRO_EXPR = new ORCompositeElementType("C_MACRO_EXPR", NsLanguage.INSTANCE);
    C_MACRO_NAME = new ORCompositeElementType("C_MACRO_NAME", NsLanguage.INSTANCE);
    C_MACRO_RAW_BODY = new ORCompositeElementType("C_MACRO_RAW_BODY", NsLanguage.INSTANCE);
    C_MODULE_TYPE = new ORCompositeElementType("C_MODULE_TYPE", NsLanguage.INSTANCE);
    C_ML_INTERPOLATOR = new ORCompositeElementType("C_ML_INTERPOLATOR", NsLanguage.INSTANCE);
    C_NAMED_PARAM = new ORCompositeElementType("C_NAMED_PARAM", NsLanguage.INSTANCE);
    C_OBJECT = new ORCompositeElementType("C_OBJECT", NsLanguage.INSTANCE);
    C_OBJECT_FIELD = new ORCompositeElementType("C_OBJECT_FIELD", NsLanguage.INSTANCE);
    C_OPTION = new ORCompositeElementType("C_OPTION", NsLanguage.INSTANCE);
    C_OPEN = new ORCompositeElementType("C_OPEN", NsLanguage.INSTANCE);
    C_PATTERN_MATCH_BODY = new ORCompositeElementType("C_PATTERN_MATCH_BODY", NsLanguage.INSTANCE);
    C_PATTERN_MATCH_EXPR = new ORCompositeElementType("C_PATTERN_MATCH_EXPR", NsLanguage.INSTANCE);
    C_RECORD_EXPR = new ORCompositeElementType("C_RECORD_EXPR", NsLanguage.INSTANCE);
    C_RAW = new ORCompositeElementType("C_RAW", NsLanguage.INSTANCE);
    C_SIG_EXPR = new ORCompositeElementType("C_SIG_EXPR", NsLanguage.INSTANCE);
    C_SIG_ITEM = new ORCompositeElementType("C_SIG_ITEM", NsLanguage.INSTANCE);
    C_SCOPED_EXPR = new ORCompositeElementType("C_SCOPED_EXPR", NsLanguage.INSTANCE);
    C_STRUCT_EXPR = new ORCompositeElementType("C_STRUCT_EXPR", NsLanguage.INSTANCE);
    C_SWITCH_EXPR = new ORCompositeElementType("C_SWITCH_EXPR", NsLanguage.INSTANCE);
    C_TAG = new ORCompositeElementType("C_TAG", NsLanguage.INSTANCE);
    C_TAG_PROP_VALUE = new ORCompositeElementType("C_TAG_PROP_VALUE", NsLanguage.INSTANCE);
    C_TAG_BODY = new ORCompositeElementType("C_TAG_BODY", NsLanguage.INSTANCE);
    C_TAG_START = new ORCompositeElementType("C_TAG_START", NsLanguage.INSTANCE);
    C_TAG_CLOSE = new ORCompositeElementType("C_TAG_CLOSE", NsLanguage.INSTANCE);
    C_TAG_PROPERTY = new ORCompositeElementType("C_TAG_PROPERTY", NsLanguage.INSTANCE);
    C_TERNARY = new ORCompositeElementType("C_TERNARY", NsLanguage.INSTANCE);
    C_TRY_EXPR = new ORCompositeElementType("C_TRY_EXPR", NsLanguage.INSTANCE);
    C_TRY_BODY = new ORCompositeElementType("C_TRY_BODY", NsLanguage.INSTANCE);
    C_TRY_HANDLER = new ORCompositeElementType("C_TRY_HANDLER", NsLanguage.INSTANCE);
    C_TRY_HANDLERS = new ORCompositeElementType("C_TRY_HANDLERS", NsLanguage.INSTANCE);
    C_TYPE_BINDING = new ORCompositeElementType("C_TYPE_BINDING", NsLanguage.INSTANCE);
    C_UNIT = new ORCompositeElementType("C_UNIT", NsLanguage.INSTANCE);
    C_UNKNOWN_EXPR = new ORCompositeElementType("C_UNKNOWN_EXPR", NsLanguage.INSTANCE);
    C_UPPER_SYMBOL = new ORCompositeElementType("C_UPPER_SYMBOL", NsLanguage.INSTANCE);
    C_VARIANT = new ORCompositeElementType("C_VARIANT", NsLanguage.INSTANCE);
    C_VARIANT_CONSTRUCTOR =
        new ORCompositeElementType("C_VARIANT_CONSTRUCTOR", NsLanguage.INSTANCE);
    C_WHILE = new ORCompositeElementType("C_WHILE", NsLanguage.INSTANCE);

    // Token element types

    AND = new ORTokenElementType("AND", NsLanguage.INSTANCE);
    ANDAND = new ORTokenElementType("ANDAND", NsLanguage.INSTANCE);
    ARROBASE = new ORTokenElementType("ARROBASE", NsLanguage.INSTANCE);
    ARROW = new ORTokenElementType("ARROW", NsLanguage.INSTANCE);
    ASSERT = new ORTokenElementType("ASSERT", NsLanguage.INSTANCE);
    AS = new ORTokenElementType("AS", NsLanguage.INSTANCE);
    BACKSLASH = new ORTokenElementType("BACKSLASH", NsLanguage.INSTANCE);
    BOOL_VALUE = new ORTokenElementType("BOOL_VALUE", NsLanguage.INSTANCE);
    CATCH = new ORTokenElementType("CATCH", NsLanguage.INSTANCE);
    CHAR_VALUE = new ORTokenElementType("CHAR_VALUE", NsLanguage.INSTANCE);
    EXCEPTION_NAME = new ORTokenElementType("EXCEPTION_NAME", NsLanguage.INSTANCE);
    FLOAT_VALUE = new ORTokenElementType("FLOAT_VALUE", NsLanguage.INSTANCE);
    FUNCTION = new ORTokenElementType("FUNCTION", NsLanguage.INSTANCE);
    FUN = new ORTokenElementType("FUN", NsLanguage.INSTANCE);
    FUNCTOR = new ORTokenElementType("FUNCTOR", NsLanguage.INSTANCE);
    INT_VALUE = new ORTokenElementType("INT_VALUE", NsLanguage.INSTANCE);
    PROPERTY_NAME = new ORTokenElementType("PROPERTY_NAME", NsLanguage.INSTANCE);
    STRING_VALUE = new ORTokenElementType("STRING_VALUE", NsLanguage.INSTANCE);
    SWITCH = new ORTokenElementType("SWITCH", NsLanguage.INSTANCE);
    IF = new ORTokenElementType("IF", NsLanguage.INSTANCE);
    BACKTICK = new ORTokenElementType("BACKTICK", NsLanguage.INSTANCE);
    BEGIN = new ORTokenElementType("BEGIN", NsLanguage.INSTANCE);
    CARRET = new ORTokenElementType("CARRET", NsLanguage.INSTANCE);
    COLON = new ORTokenElementType("COLON", NsLanguage.INSTANCE);
    COMMA = new ORTokenElementType("COMMA", NsLanguage.INSTANCE);
    SINGLE_COMMENT = new ORTokenElementType("SINGLE_COMMENT", NsLanguage.INSTANCE);
    MULTI_COMMENT = new ORTokenElementType("MULTI_COMMENT", NsLanguage.INSTANCE);
    DIFF = new ORTokenElementType("DIFF", NsLanguage.INSTANCE);
    DIRECTIVE_IF = new ORTokenElementType("DIRECTIVE_IF", NsLanguage.INSTANCE);
    DIRECTIVE_ELSE = new ORTokenElementType("DIRECTIVE_ELSE", NsLanguage.INSTANCE);
    DIRECTIVE_ELIF = new ORTokenElementType("DIRECTIVE_ELIF", NsLanguage.INSTANCE);
    DIRECTIVE_END = new ORTokenElementType("DIRECTIVE_END", NsLanguage.INSTANCE);
    DIRECTIVE_ENDIF = new ORTokenElementType("DIRECTIVE_ENDIF", NsLanguage.INSTANCE);
    LT_OR_EQUAL = new ORTokenElementType("LT_OR_EQUAL", NsLanguage.INSTANCE);
    GT_OR_EQUAL = new ORTokenElementType("GT_OR_EQUAL", NsLanguage.INSTANCE);
    DOLLAR = new ORTokenElementType("DOLLAR", NsLanguage.INSTANCE);
    DOT = new ORTokenElementType("DOT", NsLanguage.INSTANCE);
    DOTDOTDOT = new ORTokenElementType("DOTDOTDOT", NsLanguage.INSTANCE);
    DO = new ORTokenElementType("DO", NsLanguage.INSTANCE);
    DONE = new ORTokenElementType("DONE", NsLanguage.INSTANCE);
    ELSE = new ORTokenElementType("ELSE", NsLanguage.INSTANCE);
    END = new ORTokenElementType("END", NsLanguage.INSTANCE);
    ENDIF = new ORTokenElementType("ENDIF", NsLanguage.INSTANCE);
    NOT_EQ = new ORTokenElementType("EQ", NsLanguage.INSTANCE);
    NOT_EQEQ = new ORTokenElementType("EQEQ", NsLanguage.INSTANCE);
    EQ = new ORTokenElementType("EQ", NsLanguage.INSTANCE);
    EQEQ = new ORTokenElementType("EQEQ", NsLanguage.INSTANCE);
    EQEQEQ = new ORTokenElementType("EQEQEQ", NsLanguage.INSTANCE);
    EXCEPTION = new ORTokenElementType("EXCEPTION", NsLanguage.INSTANCE);
    EXCLAMATION_MARK = new ORTokenElementType("EXCLAMATION_MARK", NsLanguage.INSTANCE);
    EXTERNAL = new ORTokenElementType("EXTERNAL", NsLanguage.INSTANCE);
    FOR = new ORTokenElementType("FOR", NsLanguage.INSTANCE);
    TYPE_ARGUMENT = new ORTokenElementType("TYPE_ARGUMENT", NsLanguage.INSTANCE);
    GT = new ORTokenElementType("GT", NsLanguage.INSTANCE);
    IN = new ORTokenElementType("IN", NsLanguage.INSTANCE);
    LAZY = new ORTokenElementType("LAZY", NsLanguage.INSTANCE);
    INCLUDE = new ORTokenElementType("INCLUDE", NsLanguage.INSTANCE);
    LARRAY = new ORTokenElementType("LARRAY", NsLanguage.INSTANCE);
    LBRACE = new ORTokenElementType("LBRACE", NsLanguage.INSTANCE);
    LBRACKET = new ORTokenElementType("LBRACKET", NsLanguage.INSTANCE);
    LET = new ORTokenElementType("LET", NsLanguage.INSTANCE);
    LIDENT = new ORTokenElementType("LIDENT", NsLanguage.INSTANCE);
    LPAREN = new ORTokenElementType("LPAREN", NsLanguage.INSTANCE);
    LT = new ORTokenElementType("LT", NsLanguage.INSTANCE);
    MATCH = new ORTokenElementType("MATCH", NsLanguage.INSTANCE);
    MINUS = new ORTokenElementType("MINUS", NsLanguage.INSTANCE);
    MINUSDOT = new ORTokenElementType("MINUSDOT", NsLanguage.INSTANCE);
    MODULE = new ORTokenElementType("MODULE", NsLanguage.INSTANCE);
    MUTABLE = new ORTokenElementType("MUTABLE", NsLanguage.INSTANCE);
    NONE = new ORTokenElementType("NONE", NsLanguage.INSTANCE);
    OF = new ORTokenElementType("OF", NsLanguage.INSTANCE);
    OPEN = new ORTokenElementType("OPEN", NsLanguage.INSTANCE);
    OPTION = new ORTokenElementType("OPTION", NsLanguage.INSTANCE);
    POLY_VARIANT = new ORTokenElementType("POLY_VARIANT", NsLanguage.INSTANCE);
    VARIANT_NAME = new ORTokenElementType("VARIANT_NAME", NsLanguage.INSTANCE);
    PIPE = new ORTokenElementType("PIPE", NsLanguage.INSTANCE);
    PIPE_FORWARD = new ORTokenElementType("PIPE_FORWARD", NsLanguage.INSTANCE);
    PIPE_FIRST = new ORTokenElementType("PIPE_FIRST", NsLanguage.INSTANCE);
    PLUS = new ORTokenElementType("PLUS", NsLanguage.INSTANCE);
    PERCENT = new ORTokenElementType("PERCENT", NsLanguage.INSTANCE);
    PLUSDOT = new ORTokenElementType("PLUSDOT", NsLanguage.INSTANCE);
    QUESTION_MARK = new ORTokenElementType("QUESTION_MARK", NsLanguage.INSTANCE);
    SINGLE_QUOTE = new ORTokenElementType("SINGLE_QUOTE", NsLanguage.INSTANCE);
    DOUBLE_QUOTE = new ORTokenElementType("DOUBLE_QUOTE", NsLanguage.INSTANCE);
    RAISE = new ORTokenElementType("RAISE", NsLanguage.INSTANCE);
    RARRAY = new ORTokenElementType("RARRAY", NsLanguage.INSTANCE);
    RBRACE = new ORTokenElementType("RBRACE", NsLanguage.INSTANCE);
    RBRACKET = new ORTokenElementType("RBRACKET", NsLanguage.INSTANCE);
    REC = new ORTokenElementType("REC", NsLanguage.INSTANCE);
    REF = new ORTokenElementType("REF", NsLanguage.INSTANCE);
    RPAREN = new ORTokenElementType("RPAREN", NsLanguage.INSTANCE);
    SEMI = new ORTokenElementType("SEMI", NsLanguage.INSTANCE);
    SIG = new ORTokenElementType("SIG", NsLanguage.INSTANCE);
    SHARP = new ORTokenElementType("SHARP", NsLanguage.INSTANCE);
    SHARPSHARP = new ORTokenElementType("SHARPSHARP", NsLanguage.INSTANCE);
    SHORTCUT = new ORTokenElementType("SHORTCUT", NsLanguage.INSTANCE);
    SLASH = new ORTokenElementType("SLASH", NsLanguage.INSTANCE);
    SLASHDOT = new ORTokenElementType("SLASHDOT", NsLanguage.INSTANCE);
    SOME = new ORTokenElementType("SOME", NsLanguage.INSTANCE);
    STAR = new ORTokenElementType("STAR", NsLanguage.INSTANCE);
    STARDOT = new ORTokenElementType("STARDOT", NsLanguage.INSTANCE);
    STRUCT = new ORTokenElementType("STRUCT", NsLanguage.INSTANCE);
    TAG_AUTO_CLOSE = new ORTokenElementType("TAG_AUTO_CLOSE", NsLanguage.INSTANCE);
    TAG_NAME = new ORTokenElementType("TAG_NAME", NsLanguage.INSTANCE);
    TAG_LT = new ORTokenElementType("TAG_LT", NsLanguage.INSTANCE);
    TAG_LT_SLASH = new ORTokenElementType("TAG_LT_SLASH", NsLanguage.INSTANCE);
    TAG_GT = new ORTokenElementType("TAG_GT", NsLanguage.INSTANCE);
    TILDE = new ORTokenElementType("TILDE", NsLanguage.INSTANCE);
    TO = new ORTokenElementType("TO", NsLanguage.INSTANCE);
    THEN = new ORTokenElementType("THEN", NsLanguage.INSTANCE);
    TRY = new ORTokenElementType("TRY", NsLanguage.INSTANCE);
    TYPE = new ORTokenElementType("TYPE", NsLanguage.INSTANCE);
    UIDENT = new ORTokenElementType("UIDENT", NsLanguage.INSTANCE);
    UNIT = new ORTokenElementType("UNIT", NsLanguage.INSTANCE);
    VAL = new ORTokenElementType("VAL", NsLanguage.INSTANCE);
    PUB = new ORTokenElementType("PUB", NsLanguage.INSTANCE);
    PRI = new ORTokenElementType("PRI", NsLanguage.INSTANCE);
    WHEN = new ORTokenElementType("WHEN", NsLanguage.INSTANCE);
    WHILE = new ORTokenElementType("WHILE", NsLanguage.INSTANCE);
    WITH = new ORTokenElementType("WITH", NsLanguage.INSTANCE);
    RAW = new ORTokenElementType("RAW", NsLanguage.INSTANCE);

    ASR = new ORTokenElementType("ASR", NsLanguage.INSTANCE);
    CLASS = new ORTokenElementType("CLASS", NsLanguage.INSTANCE);
    CONSTRAINT = new ORTokenElementType("CONSTRAINT", NsLanguage.INSTANCE);
    DOWNTO = new ORTokenElementType("DOWNTO", NsLanguage.INSTANCE);
    INHERIT = new ORTokenElementType("INHERIT", NsLanguage.INSTANCE);
    INITIALIZER = new ORTokenElementType("INITIALIZER", NsLanguage.INSTANCE);
    LAND = new ORTokenElementType("LAND", NsLanguage.INSTANCE);
    LOR = new ORTokenElementType("LOR", NsLanguage.INSTANCE);
    LSL = new ORTokenElementType("LSL", NsLanguage.INSTANCE);
    LSR = new ORTokenElementType("LSR", NsLanguage.INSTANCE);
    LXOR = new ORTokenElementType("LXOR", NsLanguage.INSTANCE);
    METHOD = new ORTokenElementType("METHOD", NsLanguage.INSTANCE);
    MOD = new ORTokenElementType("MOD", NsLanguage.INSTANCE);
    NEW = new ORTokenElementType("NEW", NsLanguage.INSTANCE);
    NONREC = new ORTokenElementType("NONREC", NsLanguage.INSTANCE);
    OR = new ORTokenElementType("OR", NsLanguage.INSTANCE);
    PRIVATE = new ORTokenElementType("PRIVATE", NsLanguage.INSTANCE);
    VIRTUAL = new ORTokenElementType("VIRTUAL", NsLanguage.INSTANCE);

    COLON_EQ = new ORTokenElementType("COLON_EQ", NsLanguage.INSTANCE);
    COLON_GT = new ORTokenElementType("COLON_GT", NsLanguage.INSTANCE);
    DOTDOT = new ORTokenElementType("DOTDOT", NsLanguage.INSTANCE);
    SEMISEMI = new ORTokenElementType("SEMISEMI", NsLanguage.INSTANCE);
    GT_BRACKET = new ORTokenElementType("GT_BRACKET", NsLanguage.INSTANCE);
    GT_BRACE = new ORTokenElementType("GT_BRACE", NsLanguage.INSTANCE);
    LEFT_ARROW = new ORTokenElementType("LEFT_ARROW", NsLanguage.INSTANCE);
    RIGHT_ARROW = new ORTokenElementType("RIGHT_ARROW", NsLanguage.INSTANCE);

    OBJECT = new ORTokenElementType("OBJECT", NsLanguage.INSTANCE);
    RECORD = new ORTokenElementType("RECORD", NsLanguage.INSTANCE);

    AMPERSAND = new ORTokenElementType("AMPERSAND", NsLanguage.INSTANCE);
    BRACKET_GT = new ORTokenElementType("BRACKET_GT", NsLanguage.INSTANCE);
    BRACKET_LT = new ORTokenElementType("BRACKET_LT", NsLanguage.INSTANCE);
    BRACE_LT = new ORTokenElementType("BRACE_LT", NsLanguage.INSTANCE);

    ML_STRING_VALUE = new ORTokenElementType("ML_STRING_VALUE", NsLanguage.INSTANCE);
    ML_STRING_OPEN = new ORTokenElementType("ML_STRING_OPEN", NsLanguage.INSTANCE);
    ML_STRING_CLOSE = new ORTokenElementType("ML_STRING_CLOSE", NsLanguage.INSTANCE);
    JS_STRING_OPEN = new ORTokenElementType("JS_STRING_OPEN", NsLanguage.INSTANCE);
    JS_STRING_CLOSE = new ORTokenElementType("JS_STRING_CLOSE", NsLanguage.INSTANCE);
    UNDERSCORE = new ORTokenElementType("UNDERSCORE", NsLanguage.INSTANCE);
  }
}
