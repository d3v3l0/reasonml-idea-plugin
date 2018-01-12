package com.reason.lang.reason;

import com.intellij.psi.tree.IElementType;
import com.reason.lang.MlTypes;
import com.reason.lang.RmlLanguage;
import com.reason.lang.core.stub.type.ModuleStubElementType;
import com.reason.lang.core.stub.type.PsiLetStubElementType;
import com.reason.lang.core.stub.type.PsiTypeElementType;

public class RmlTypes extends MlTypes {

    public static final RmlTypes INSTANCE = new RmlTypes();

    private RmlTypes() {
        FILE_MODULE = new IElementType("FILE_MODULE", RmlLanguage.INSTANCE);

        ANNOTATION_EXPRESSION = new RmlElementType("ANNOTATION_EXPRESSION");
        EXTERNAL_EXPRESSION = new RmlElementType("EXTERNAL_EXPRESSION");
        EXCEPTION_EXPRESSION = new RmlElementType("EXCEPTION_EXPRESSION");
        EXCEPTION_NAME = new RmlElementType("EXCEPTION_NAME");
        INCLUDE_EXPRESSION = new RmlElementType("INCLUDE_EXPRESSION");
        LET_EXPRESSION = new PsiLetStubElementType("LET_EXPRESSION", RmlLanguage.INSTANCE, RmlTypes.INSTANCE);
        MACRO_EXPRESSION = new RmlElementType("MACRO_EXPRESSION");
        MACRO_NAME = new RmlElementType("MACRO_NAME");
        MODULE_EXPRESSION = new ModuleStubElementType("MODULE_EXPRESSION", RmlLanguage.INSTANCE);
        MODULE_NAME = new RmlElementType("MODULE_NAME");
        MODULE_PATH = new RmlElementType("MODULE_PATH");
        OPEN_EXPRESSION = new RmlElementType("OPEN_EXPRESSION");
        TYPE_EXPRESSION = new PsiTypeElementType("TYPE_EXPRESSION", RmlLanguage.INSTANCE, RmlTypes.INSTANCE);
        VAL_EXPRESSION = new PsiLetStubElementType("VAL_EXPRESSION", RmlLanguage.INSTANCE, RmlTypes.INSTANCE);

        LET_FUN_PARAMS = new RmlElementType("LET_FUN_PARAMS");
        LET_BINDING = new RmlElementType("LET_BINDING");
        TYPE_CONSTR_NAME = new RmlElementType("TYPE_CONSTR_NAME");
        PATTERN_MATCH_EXPR = new RmlElementType("PATTERN_MATCH_EXPR");
        OBJECT_EXPR = new RmlElementType("OBJECT_EXPR");
        SCOPED_EXPR = new RmlElementType("SCOPED_EXPR");
        SIG_SCOPE = new RmlElementType("SIG_SCOPE");
        VALUE_NAME = new RmlElementType("VALUE_NAME");
        PROPERTY_NAME = new RmlElementType("PROPERTY_NAME");
        FUNCTOR_PARAMS = new RmlElementType("FUNCTOR_PARAMS");

        AND = new RmlTokenType("AND");
        ANDAND = new RmlTokenType("ANDAND");
        ARROBASE = new RmlTokenType("ARROBASE");
        ARROW = new RmlTokenType("ARROW");
        ASSERT = new RmlTokenType("ASSERT");
        AS = new RmlTokenType("AS");
        BACKTICK = new RmlTokenType("BACKTICK");
        BEGIN = new RmlTokenType("BEGIN");
        CARRET = new RmlTokenType("CARRET");
        COLON = new RmlTokenType("COLON");
        COMMA = new RmlTokenType("COMMA");
        COMMENT = new RmlTokenType("COMMENT");
        DIFF = new RmlTokenType("DIFF");
        DOLLAR = new RmlTokenType("DOLLAR");
        DOT = new RmlTokenType("DOT");
        DOTDOTDOT = new RmlTokenType("DOTDOTDOT");
        DO = new RmlTokenType("DO");
        DONE = new RmlTokenType("DONE");
        ELSE = new RmlTokenType("ELSE");
        END = new RmlTokenType("END");
        NOT_EQ = new RmlTokenType("EQ");
        NOT_EQEQ = new RmlTokenType("EQEQ");
        EQ = new RmlTokenType("EQ");
        EQEQ = new RmlTokenType("EQEQ");
        EQEQEQ = new RmlTokenType("EQEQEQ");
        EXCEPTION = new RmlTokenType("EXCEPTION");
        EXCLAMATION_MARK = new RmlTokenType("EXCLAMATION_MARK");
        EXTERNAL = new RmlTokenType("EXTERNAL");
        FALSE = new RmlTokenType("FALSE");
        FLOAT = new RmlTokenType("FLOAT");
        CHAR = new RmlElementType("CHAR");
        FOR = new RmlElementType("FOR");
        FUN = new RmlTokenType("FUN");
        FUN_PARAMS = new RmlTokenType("FUN_PARAMS");
        FUN_BODY = new RmlTokenType("FUN_BODY");
        FUNCTION = new RmlTokenType("FUNCTION");
        TYPE_ARGUMENT = new RmlTokenType("TYPE_ARGUMENT");
        GT = new RmlTokenType("GT");
        IF = new RmlTokenType("IF");
        BIN_CONDITION = new RmlTokenType("BIN_CONDITION");
        IN = new RmlElementType("IN");
        LAZY = new RmlElementType("LAZY");
        INCLUDE = new RmlTokenType("INCLUDE");
        INT = new RmlTokenType("INT");
        LARRAY = new RmlTokenType("LARRAY");
        LBRACE = new RmlTokenType("LBRACE");
        LBRACKET = new RmlTokenType("LBRACKET");
        LET = new RmlTokenType("LET");
        LIDENT = new RmlTokenType("LIDENT");
        LIST = new RmlTokenType("LIST");
        LPAREN = new RmlTokenType("LPAREN");
        LT = new RmlTokenType("LT");
        MATCH = new RmlTokenType("MATCH");
        MINUS = new RmlTokenType("MINUS");
        MINUSDOT = new RmlTokenType("MINUSDOT");
        MODULE = new RmlTokenType("MODULE");
        MUTABLE = new RmlTokenType("MUTABLE");
        NONE = new RmlTokenType("NONE");
        OBJECT = new RmlElementType("OBJECT");
        OF = new RmlElementType("OF");
        OPEN = new RmlTokenType("OPEN");
        OPTION = new RmlTokenType("OPTION");
        POLY_VARIANT = new RmlTokenType("POLY_VARIANT");
        PIPE = new RmlTokenType("PIPE");
        PIPE_FORWARD = new RmlTokenType("PIPE_FORWARD");
        PLUS = new RmlTokenType("PLUS");
        PERCENT = new RmlTokenType("PERCENT");
        PLUSDOT = new RmlTokenType("PLUSDOT");
        QUESTION_MARK = new RmlTokenType("QUESTION_MARK");
        QUOTE = new RmlTokenType("QUOTE");
        RAISE = new RmlElementType("RAISE");
        RARRAY = new RmlTokenType("RARRAY");
        RBRACE = new RmlTokenType("RBRACE");
        RBRACKET = new RmlTokenType("RBRACKET");
        REC = new RmlTokenType("REC");
        REF = new RmlTokenType("REF");
        RPAREN = new RmlTokenType("RPAREN");
        SEMI = new RmlTokenType("SEMI");
        SIG = new RmlTokenType("SIG");
        SIMPLE_ARROW = new RmlTokenType("SIMPLE_ARROW");
        SHARP = new RmlTokenType("SHARP");
        SHORTCUT = new RmlTokenType("SHORTCUT");
        SLASH = new RmlTokenType("SLASH");
        SLASHDOT = new RmlTokenType("SLASHDOT");
        SOME = new RmlTokenType("SOME");
        STAR = new RmlTokenType("STAR");
        STARDOT = new RmlTokenType("STARDOT");
        STRING = new RmlTokenType("STRING");
        STRUCT = new RmlTokenType("STRUCT");
        SWITCH = new RmlTokenType("SWITCH");
        TAG_AUTO_CLOSE = new RmlTokenType("TAG_AUTO_CLOSE");
        TAG_START = new RmlTokenType("TAG_START");
        TAG_CLOSE = new RmlTokenType("TAG_CLOSE");
        TAG_NAME = new RmlTokenType("TAG_NAME");
        TAG_PROPERTY = new RmlTokenType("TAG_PROPERTY");
        TAG_LT = new RmlTokenType("TAG_LT");
        TAG_GT = new RmlTokenType("TAG_GT");
        TILDE = new RmlElementType("TILDE");
        TO = new RmlElementType("TO");
        THEN = new RmlTokenType("THEN");
        TRUE = new RmlTokenType("TRUE");
        TRY = new RmlTokenType("TRY");
        TYPE = new RmlTokenType("TYPE");
        UIDENT = new RmlTokenType("UIDENT");
        UNIT = new RmlTokenType("UNIT");
        VAL = new RmlTokenType("VAL");
        WHEN = new RmlTokenType("WHEN");
        WHILE = new RmlTokenType("WHILE");
        WITH = new RmlTokenType("WITH");
    }
}