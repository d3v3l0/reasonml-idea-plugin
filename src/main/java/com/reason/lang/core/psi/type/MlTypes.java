package com.reason.lang.core.psi.type;

import com.intellij.psi.tree.IElementType;

public abstract class MlTypes {
    // Composite element types

    public IElementType EXTERNAL_STMT;
    public IElementType LET_STMT;
    public IElementType MODULE_STMT;
    public IElementType CLASS_STMT;
    public IElementType EXP_TYPE;
    public IElementType VAL_EXPR;
    public IElementType CLASS_PARAMS;
    public IElementType CLASS_CONSTR;
    public IElementType CLASS_FIELD;
    public IElementType CLASS_METHOD;
    public IElementType ANNOTATION_EXPR;
    public IElementType EXCEPTION_EXPR;
    public IElementType OPEN_STMT;
    public IElementType INCLUDE_STMT;
    public IElementType LET_BINDING;
    public IElementType GENERIC_EXPR;
    public IElementType MACRO_EXPR;
    public IElementType MACRO_NAME;
    public IElementType MODULE_PATH;
    public IElementType ASSERT_STMT;
    public IElementType SCOPED_EXPR;
    public IElementType VARIANT_EXP;
    public IElementType IF_STMT;
    public IElementType BIN_CONDITION;
    public IElementType NAMED_SYMBOL;
    public IElementType PATTERN_MATCH_EXPR;
    public IElementType INTERPOLATION_EXPR;
    public IElementType SIG_SCOPE;
    public IElementType TAG_START;
    public IElementType TAG_CLOSE;
    public IElementType TAG_PROPERTY;
    public IElementType FUN_EXPR;
    public IElementType FUN_PARAMS;
    public IElementType FUN_BODY;
    public IElementType RECORD_EXPR;
    public IElementType RECORD_FIELD;
    public IElementType SWITCH_EXPR;
    public IElementType MATCH_EXPR;
    public IElementType TRY_EXPR;
    public IElementType WITH_EXPR;
    public IElementType TYPE_CONSTR_NAME;
    public IElementType TYPE_BINDING;
    public IElementType UPPER_SYMBOL;
    public IElementType LOWER_SYMBOL;
    public IElementType STRUCT_EXPR;

    // Token element types

    public MlTokenElementType BOOL;
    public MlTokenElementType STRING;
    public MlTokenElementType FLOAT;
    public MlTokenElementType CHAR;
    public MlTokenElementType INT;

    public MlTokenElementType BOOL_VALUE;
    public MlTokenElementType STRING_VALUE;
    public MlTokenElementType FLOAT_VALUE;
    public MlTokenElementType CHAR_VALUE;
    public MlTokenElementType INT_VALUE;

    public MlTokenElementType AND;
    public MlTokenElementType ANDAND;
    public MlTokenElementType ASSERT;
    public MlTokenElementType BEGIN;
    public MlTokenElementType CLASS;
    public MlTokenElementType CONSTRAINT;
    public MlTokenElementType DO;
    public MlTokenElementType DONE;
    public MlTokenElementType DOWNTO;
    public MlTokenElementType ELSE;
    public MlTokenElementType END;
    public MlTokenElementType EXCEPTION;
    public MlTokenElementType EXTERNAL;
    public MlTokenElementType FOR;
    public MlTokenElementType FUN;
    public MlTokenElementType FUNCTION;
    public MlTokenElementType FUNCTOR;
    public MlTokenElementType IF;
    public MlTokenElementType IN;
    public MlTokenElementType INCLUDE;
    public MlTokenElementType INHERIT;
    public MlTokenElementType INITIALIZER;
    public MlTokenElementType LAZY;
    public MlTokenElementType LET;
    public MlTokenElementType MIXIN_FIELD;
    public MlTokenElementType MODULE;
    public MlTokenElementType MUTABLE;
    public MlTokenElementType NEW;
    public MlTokenElementType NONREC;
    public MlTokenElementType OBJECT;
    public MlTokenElementType OF;
    public MlTokenElementType OPEN;
    public MlTokenElementType OR;
    public MlTokenElementType PUB;
    public MlTokenElementType PRI;
    public MlTokenElementType REC;
    public MlTokenElementType SIG;
    public MlTokenElementType STRUCT;
    public MlTokenElementType SWITCH;
    public MlTokenElementType THEN;
    public MlTokenElementType TO;
    public MlTokenElementType TRY;
    public MlTokenElementType TYPE;
    public MlTokenElementType VAL;
    public MlTokenElementType VIRTUAL;
    public MlTokenElementType WHEN;
    public MlTokenElementType WHILE;
    public MlTokenElementType WITH;

    public MlTokenElementType GENERIC_COND;
    public MlTokenElementType EXCEPTION_NAME;
    public MlTokenElementType LOCAL_OPEN;
    public MlTokenElementType PROPERTY_NAME;
    public MlTokenElementType SHARPSHARP;
    public MlTokenElementType ARROBASE;
    public MlTokenElementType ARROW;
    public MlTokenElementType AS;
    public MlTokenElementType BACKTICK;
    public MlTokenElementType CARRET;
    public MlTokenElementType COLON;
    public MlTokenElementType COMMA;
    public MlTokenElementType COMMENT;
    public MlTokenElementType DIFF;
    public MlTokenElementType LT_OR_EQUAL;
    public MlTokenElementType GT_OR_EQUAL;
    public MlTokenElementType DOLLAR;
    public MlTokenElementType DOT;
    public MlTokenElementType DOTDOTDOT;
    public MlTokenElementType NOT_EQ;
    public MlTokenElementType NOT_EQEQ;
    public MlTokenElementType EQ;
    public MlTokenElementType EQEQ;
    public MlTokenElementType EQEQEQ;
    public MlTokenElementType EXCLAMATION_MARK;
    public MlTokenElementType TYPE_ARGUMENT;
    public MlTokenElementType GT;
    public MlTokenElementType LARRAY;
    public MlTokenElementType LBRACE;
    public MlTokenElementType LBRACKET;
    public MlTokenElementType LIDENT;
    public MlTokenElementType LIST;
    public MlTokenElementType LPAREN;
    public MlTokenElementType LT;
    public MlTokenElementType MATCH;
    public MlTokenElementType MINUS;
    public MlTokenElementType MINUSDOT;
    public MlTokenElementType NONE;
    public MlTokenElementType OPTION;
    public MlTokenElementType POLY_VARIANT;
    public MlTokenElementType VARIANT_NAME;
    public MlTokenElementType PIPE;
    public MlTokenElementType PIPE_FORWARD;
    public MlTokenElementType PIPE_FIRST;
    public MlTokenElementType PLUS;
    public MlTokenElementType PERCENT;
    public MlTokenElementType PLUSDOT;
    public MlTokenElementType QUESTION_MARK;
    public MlTokenElementType QUOTE;
    public MlTokenElementType RAISE;
    public MlTokenElementType RARRAY;
    public MlTokenElementType RBRACE;
    public MlTokenElementType RBRACKET;
    public MlTokenElementType REF;
    public MlTokenElementType RPAREN;
    public MlTokenElementType SEMI;
    public MlTokenElementType SHARP;
    public MlTokenElementType SHORTCUT;
    public MlTokenElementType SLASH;
    public MlTokenElementType SLASHDOT;
    public MlTokenElementType SOME;
    public MlTokenElementType STAR;
    public MlTokenElementType STARDOT;
    public MlTokenElementType TAG_AUTO_CLOSE;
    public MlTokenElementType TAG_NAME;
    public MlTokenElementType TAG_LT;
    public MlTokenElementType TAG_LT_SLASH;
    public MlTokenElementType TAG_GT;
    public MlTokenElementType TILDE;
    public MlTokenElementType UIDENT;
    public MlTokenElementType UNIT;
    public MlTokenElementType RECORD;
    public MlTokenElementType ASR;
    public MlTokenElementType LAND;
    public MlTokenElementType LOR;
    public MlTokenElementType LSL;
    public MlTokenElementType LSR;
    public MlTokenElementType LXOR;
    public MlTokenElementType METHOD;
    public MlTokenElementType MOD;
    public MlTokenElementType PRIVATE;
    public MlTokenElementType COLON_EQ;
    public MlTokenElementType COLON_GT;
    public MlTokenElementType DOTDOT;
    public MlTokenElementType SEMISEMI;
    public MlTokenElementType GT_BRACKET;
    public MlTokenElementType GT_BRACE;
    public MlTokenElementType LEFT_ARROW;
    public MlTokenElementType RIGHT_ARROW;
    public MlTokenElementType AMPERSAND;
    public MlTokenElementType BRACKET_GT;
    public MlTokenElementType BRACKET_LT;
    public MlTokenElementType BRACE_LT;
    public MlTokenElementType ML_STRING_OPEN;
    public MlTokenElementType ML_STRING_CLOSE;
    public MlTokenElementType JS_STRING_OPEN;
    public MlTokenElementType JS_STRING_CLOSE;
    public MlTokenElementType UNDERSCORE;
}
