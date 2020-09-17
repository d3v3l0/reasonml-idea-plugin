package com.reason.lang.reason;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.reason.lang.CommonParser;
import com.reason.lang.ParserScope;
import com.reason.lang.ParserState;

import static com.intellij.codeInsight.completion.CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED;
import static com.reason.lang.ParserScopeEnum.*;

public class RmlParser extends CommonParser<RmlTypes> {

    RmlParser() {
        super(RmlTypes.INSTANCE);
    }

    @Override
    protected void parseFile(@NotNull PsiBuilder builder, @NotNull ParserState state) {
        IElementType tokenType = null;
        state.previousElementType1 = null;

        //long parseStart = System.currentTimeMillis();

        while (true) {
            //long parseTime = System.currentTimeMillis();
            //if (5 < parseTime - parseStart) {
            // Protection: abort the parsing if too much time spent
            //break;
            //}

            state.previousElementType2 = state.previousElementType1;
            state.previousElementType1 = tokenType;
            tokenType = state.getTokenType();
            if (tokenType == null) {
                break;
            }

            if (state.isInContext(interpolationString)) {
                // special analysis when inside an interpolation string
                if (tokenType == m_types.JS_STRING_CLOSE) {
                    parseJsStringClose(state);
                } else if (tokenType == m_types.DOLLAR) {
                    if (state.isCurrentResolution(interpolationPart)) {
                        state.popEnd();
                        state.advance().mark(interpolationReference, m_types.C_INTERPOLATION_REF);
                    }
                } else if (state.isCurrentResolution(interpolationReference)) {
                    state.advance().popEnd();
                } else if (state.currentResolution() != interpolationPart) {
                    state.mark(interpolationPart, m_types.C_INTERPOLATION_PART);
                }
            } else {
                // special keywords that can be used as lower identifier in records
                if (tokenType == m_types.REF && state.isCurrentResolution(recordBinding)) {
                    parseLIdent(state);
                } else if (tokenType == m_types.METHOD && state.isCurrentResolution(recordBinding)) {
                    parseLIdent(state);
                }
                //
                else if (tokenType == m_types.SEMI) {
                    parseSemi(state);
                } else if (tokenType == m_types.EQ) {
                    parseEq(state);
                } else if (tokenType == m_types.UNDERSCORE) {
                    parseUnderscore(state);
                } else if (tokenType == m_types.ARROW) {
                    parseArrow(state);
                } else if (tokenType == m_types.REF) {
                    parseRef(state);
                } else if (tokenType == m_types.OPTION) {
                    parseOption(state);
                } else if (tokenType == m_types.SOME) {
                    parseSome(state);
                } else if (tokenType == m_types.NONE) {
                    parseNone(state);
                } else if (tokenType == m_types.TRY) {
                    parseTry(state);
                } else if (tokenType == m_types.SWITCH) {
                    parseSwitch(state);
                } else if (tokenType == m_types.LIDENT) {
                    parseLIdent(state);
                } else if (tokenType == m_types.UIDENT) {
                    parseUIdent(state);
                } else if (tokenType == m_types.ARROBASE) {
                    parseArrobase(state);
                } else if (tokenType == m_types.PERCENT) {
                    parsePercent(state);
                } else if (tokenType == m_types.COLON) {
                    parseColon(state);
                } else if (tokenType == m_types.RAW) {
                    parseRaw(state);
                } else if (tokenType == m_types.STRING_VALUE) {
                    parseStringValue(state);
                } else if (tokenType == m_types.PIPE) {
                    parsePipe(state);
                } else if (tokenType == m_types.COMMA) {
                    parseComma(state);
                } else if (tokenType == m_types.AND) {
                    parseAnd(state);
                } else if (tokenType == m_types.FUN) {
                    parseFun(state);
                } else if (tokenType == m_types.ASSERT) {
                    parseAssert(state);
                } else if (tokenType == m_types.IF) {
                    parseIf(state);
                } else if (tokenType == m_types.DOT) {
                    parseDot(state);
                } else if (tokenType == m_types.DOTDOTDOT) {
                    parseDotDotDot(state);
                } else if (tokenType == m_types.WITH) {
                    parseWith(state);
                } else if (tokenType == m_types.TILDE) {
                    parseTilde(state);
                }
                // ( ... )
                else if (tokenType == m_types.LPAREN) {
                    parseLParen(state);
                } else if (tokenType == m_types.RPAREN) {
                    parseRParen(state);
                }
                // { ... }
                else if (tokenType == m_types.LBRACE) {
                    parseLBrace(state);
                } else if (tokenType == m_types.RBRACE) {
                    parseRBrace(state);
                }
                // [| ... |]
                else if (tokenType == m_types.LARRAY) {
                    parseLArray(state);
                } else if (tokenType == m_types.RARRAY) {
                    parseRArray(state);
                }
                // [ ... ]
                // [> ... ]
                else if (tokenType == m_types.LBRACKET) {
                    parseLBracket(state);
                } else if (tokenType == m_types.BRACKET_GT) {
                    parseBracketGt(state);
                } else if (tokenType == m_types.RBRACKET) {
                    parseRBracket(state);
                }
                // < ... >
                else if (tokenType == m_types.LT) {
                    parseLt(state);
                } else if (tokenType == m_types.TAG_LT_SLASH) {
                    parseLtSlash(state);
                } else if (tokenType == m_types.GT) {
                    parseGt(state);
                } else if (tokenType == m_types.TAG_AUTO_CLOSE) {
                    parseGtAutoClose(state);
                }
                // {| ... |}
                else if (tokenType == m_types.ML_STRING_OPEN) {
                    parseMlStringOpen(state);
                } else if (tokenType == m_types.ML_STRING_CLOSE) {
                    parseMlStringClose(state);
                }
                // {j| ... |j}
                else if (tokenType == m_types.JS_STRING_OPEN) {
                    parseJsStringOpen(state);
                }
                // Starts an expression
                else if (tokenType == m_types.OPEN) {
                    parseOpen(state);
                } else if (tokenType == m_types.INCLUDE) {
                    parseInclude(state);
                } else if (tokenType == m_types.EXTERNAL) {
                    parseExternal(state);
                } else if (tokenType == m_types.TYPE) {
                    parseType(state);
                } else if (tokenType == m_types.MODULE) {
                    parseModule(state);
                } else if (tokenType == m_types.CLASS) {
                    parseClass(state);
                } else if (tokenType == m_types.LET) {
                    parseLet(state);
                } else if (tokenType == m_types.VAL) {
                    parseVal(state);
                } else if (tokenType == m_types.PUB) {
                    parsePub(state);
                } else if (tokenType == m_types.EXCEPTION) {
                    parseException(state);
                }
            }

            if (state.dontMove) {
                state.dontMove = false;
            } else {
                builder.advanceLexer();
            }
        }
    }

    private void parseTilde(ParserState state) {
        if (state.in(m_types.C_SIG_ITEM)) {
            state.updateCurrentResolution(namedItem).
                    updateCurrentCompositeElementType(m_types.C_NAMED_PARAM);
        }
    }

    private void parseRef(@NotNull ParserState state) {
        if (state.is(m_types.C_TAG_START)) {
            state.remapCurrentToken(m_types.PROPERTY_NAME).
                    mark(jsxTagProperty, m_types.C_TAG_PROPERTY);
        }
    }

    private void parseOption(@NotNull ParserState state) {
        state.mark(option, m_types.C_OPTION);
    }

    private void parseSome(@NotNull ParserState state) {
        if (state.isCurrentResolution(patternMatch)) {
            // Defining a pattern match
            // switch (c) { | |>Some<| .. }
            state.//remapCurrentToken(m_types.VARIANT_NAME).
                    wrapWith(m_types.C_VARIANT).
                    updateCurrentResolution(patternMatchVariant);
        }
    }

    private void parseNone(@NotNull ParserState state) {
        if (state.isCurrentResolution(patternMatch)) {
            // Defining a pattern match
            // switch (c) { | |>Some<| .. }
            state.//remapCurrentToken(m_types.VARIANT_NAME).
                    wrapWith(m_types.C_VARIANT).
                    updateCurrentResolution(patternMatchVariant);
        }
    }

    private void parseRaw(@NotNull ParserState state) {
        // % |>raw<| ...
        if (state.isCurrentResolution(macroName)) {
            state.advance().
                    popEnd().
                    updateCurrentResolution(macroRawNamed);
        }
    }

    private void parseUnderscore(@NotNull ParserState state) {
        if (state.isCurrentResolution(let)) {
            state.updateCurrentResolution(letNamed);
        }
    }

    private void parseIf(@NotNull ParserState state) {
        state.mark(ifThenStatement, m_types.C_IF_STMT);
    }

    private void parseDot(@NotNull ParserState state) {
        if (state.previousElementType1 == m_types.LBRACE && (state.isCurrentResolution(jsObject) || state.isCurrentResolution(jsObjectBinding))) {
            // Js object definition
            // ... { |>.<| ... }
            state.advance().
                    mark(objectField, m_types.C_OBJECT_FIELD);
        }
    }

    private void parseDotDotDot(@NotNull ParserState state) {
        if (state.previousElementType1 == m_types.LBRACE) {
            // Mixin
            // { |>...<| x ...
            state.updateCurrentResolution(recordUsage).
                    updateCurrentCompositeElementType(m_types.C_RECORD_EXPR).
                    mark(mixin, m_types.C_MIXIN_FIELD);
        }
    }

    private void parseWith(@NotNull ParserState state) {
        if (state.isCurrentResolution(functorResult)) {
            // module M (X) : ( S |>with<| ... ) = ...
            state.popEnd().
                    mark(constraints, m_types.C_CONSTRAINTS);
        }
    }

    private void parseAssert(@NotNull ParserState state) {
        state.mark(assert_, m_types.C_ASSERT_STMT).
                advance();
    }

    private void parseFun(@NotNull ParserState state) {
        if (state.isCurrentResolution(letBinding)) {
            // fun keyword is equivalent to a switch body
            // let x = |>fun<| | ...
            state.mark(funPattern, m_types.C_FUN_EXPR);
        }
    }

    private void parseAnd(@NotNull ParserState state) {
        if (state.isCurrentResolution(constraint)) {
            // module M = (X) : ( S with ... |>and<| ... ) = ...
            state.popEnd();
        } else {
            ParserScope latestScope = state.popEndUntilScope();

            if (isTypeResolution(latestScope)) {
                state.advance().
                        markStart(type, m_types.C_TYPE_DECLARATION);
            } else if (isLetResolution(latestScope)) {
                state.advance().
                        markStart(let, m_types.C_LET_DECLARATION);
            } else if (isModuleResolution(latestScope)) {
                state.advance().
                        markStart(module, m_types.C_MODULE_DECLARATION);
            }
        }
    }

    private void parseComma(@NotNull ParserState state) {
        ParserScope latestScope = state.popEndUntilScope();

        if (latestScope.isResolution(signatureItem) || latestScope.isResolution(namedItem)) {
            state.advance().
                    mark(signatureItem, m_types.C_SIG_ITEM);
        } else if (latestScope.isResolution(mixin)) {
            state.advance();
            IElementType tokenType = state.getTokenType();
            if (tokenType != m_types.RBRACE && tokenType != m_types.LBRACKET) {
                state.mark(recordField, m_types.C_RECORD_FIELD);
            }
        } else if (latestScope.isResolution(recordField) || latestScope.isResolution(objectField)) { // zzz: use field
            state.advance();
            IElementType tokenType = state.getTokenType();
            if (tokenType != m_types.RBRACE && tokenType != m_types.LBRACKET) {
                boolean isJsObjectField = latestScope.isCompositeType(m_types.C_OBJECT_FIELD);
                state.mark(recordField, isJsObjectField ? m_types.C_OBJECT_FIELD : m_types.C_RECORD_FIELD);
            }
        } else if (state.isPreviousResolution(let) && state.isCurrentResolution(genericExpression)) {
            // It must be a deconstruction ::  let ( a |>,<| b ) = ...
            // We need to do it again because lower symbols must be wrapped with identifiers
            ParserScope scope = state.pop();
            if (scope != null) {
                scope.rollbackTo();
                state.updateCurrentResolution(letNamed).
                        markScope(deconstruction, m_types.C_DECONSTRUCTION, m_types.LPAREN).
                        advance();
            }
        } else if (state.isCurrentResolution(functionCallParams) || state.isCurrentResolution(functionParameters)) {
            state.advance();
            IElementType nextTokenType = state.getTokenType();
            if (nextTokenType != m_types.RPAREN) {
                // not at the end of a list: ie not => (p1, p2<,> )
                state.mark(functionParameter, m_types.C_FUN_PARAM);
            }
        }
    }

    private void parsePipe(@NotNull ParserState state) {
        if (state.isCurrentResolution(typeBinding) || state.isCurrentResolution(variantDeclaration)) {
            // type t = |>|<| ...
            // type t = | X |>|<| Y ...
            state.popEndUntilResolution(typeBinding).
                    advance().
                    mark(variantDeclaration, m_types.C_VARIANT_DECL);
        } else if (state.isCurrentResolution(tryBodyWith)) {
            // try (...) { |>|<| ...
            state.mark(tryBodyWithHandler, m_types.C_TRY_HANDLER);
        } else {
            if (state.isCurrentResolution(patternMatchBody)) {
                // can be a switchBody or a 'fun'
                state.popEnd().popEnd();
            }

            // By default, a pattern match
            state.mark(patternMatch, m_types.C_PATTERN_MATCH_EXPR);
        }
    }

    private void parseStringValue(@NotNull ParserState state) {
        if (state.isCurrentResolution(macroRawNamed)) {
            // [%raw |>"x"<| ...
            state.wrapWith(m_types.C_MACRO_RAW_BODY);
        } else if (state.isCurrentResolution(raw)) {
            // %raw |>"x"<| ...
            state.mark(rawBody, m_types.C_MACRO_RAW_BODY).
                    advance().
                    popEnd();
        } else if (state.isCurrentResolution(annotationName)) {
            state.popEndUntilScope();
        } else if (state.isCurrentResolution(maybeRecordUsage)) {
            IElementType nextToken = state.lookAhead(1);
            if (nextToken == m_types.COLON) {
                state.updateCurrentResolution(jsObject).
                        updateCurrentCompositeElementType(m_types.C_JS_OBJECT).
                        mark(objectField, m_types.C_OBJECT_FIELD);
            }
        } else if (state.isCurrentResolution(jsObject) || state.isCurrentResolution(jsObjectBinding)) {
            state.mark(objectField, m_types.C_OBJECT_FIELD);
        }
    }

    private void parseMlStringOpen(@NotNull ParserState state) {
        if (state.isCurrentResolution(macroRawNamed)) {
            state.mark(macroRawBody, m_types.C_MACRO_RAW_BODY);
        }

        state.markScope(multilineStart, m_types.C_ML_INTERPOLATOR, m_types.ML_STRING_OPEN);
    }

    private void parseMlStringClose(@NotNull ParserState state) {
        ParserScope scope = state.popEndUntilScopeToken(m_types.ML_STRING_OPEN);
        state.advance();

        if (scope != null) {
            scope.complete();
            state.popEnd();
        }
    }

    private void parseJsStringOpen(@NotNull ParserState state) {
        state.markScope(interpolationString, m_types.C_INTERPOLATION_EXPR, m_types.JS_STRING_OPEN);
    }

    private void parseJsStringClose(@NotNull ParserState state) {
        ParserScope scope = state.popEndUntilScopeToken(m_types.JS_STRING_OPEN);
        state.advance();

        if (scope != null) {
            state.popEnd();
        }
    }

    private void parseLet(@NotNull ParserState state) {
        state.popEndUntilScope();
        state.markStart(let, m_types.C_LET_DECLARATION);
    }

    private void parseVal(@NotNull ParserState state) {
        if (!state.isCurrentResolution(annotationName)) {
            state.popEndUntilScope();
            if (state.isCurrentResolution(clazzBody)) {
                state.mark(clazzField, m_types.C_CLASS_FIELD);
            }
        }
    }

    private void parsePub(@NotNull ParserState state) {
        state.popEndUntilScope();
        if (state.isCurrentResolution(clazzBody)) {
            state.mark(clazzMethod, m_types.C_CLASS_METHOD);
        }
    }

    private void parseModule(@NotNull ParserState state) {
        if (!state.isCurrentResolution(annotationName)) {
            state.popEndUntilScope();
            state.markStart(module, m_types.C_MODULE_DECLARATION);
        }
    }

    private void parseException(@NotNull ParserState state) {
        state.popEndUntilScope();
        state.markStart(exception, m_types.C_EXCEPTION_DECLARATION);
    }

    private void parseClass(@NotNull ParserState state) {
        state.popEndUntilScope();
        state.markStart(clazz, m_types.C_CLASS_DECLARATION);
    }

    private void parseType(@NotNull ParserState state) {
        if (state.isCurrentResolution(constraints)) {
            // module M = (X) : ( S with |>type<| ... ) = ...
            state.mark(constraint, m_types.C_CONSTRAINT);
        } else if (!state.isCurrentResolution(module) && !state.isCurrentResolution(clazz)) {
            // a type definition ::  |>type<| ...
            state.mark(type, m_types.C_TYPE_DECLARATION);
        }
    }

    private void parseExternal(@NotNull ParserState state) {
        state.popEndUntilScope();
        state.markStart(external, m_types.C_EXTERNAL_DECLARATION);
    }

    private void parseOpen(@NotNull ParserState state) {
        state.popEndUntilScope();
        state.markStart(open, m_types.C_OPEN);
    }

    private void parseInclude(@NotNull ParserState state) {
        state.popEndUntilScope();
        state.markStart(include, m_types.C_INCLUDE);
    }

    private void parsePercent(@NotNull ParserState state) {
        if (state.isCurrentResolution(macro)) {
            state.mark(macroName, m_types.C_MACRO_NAME);
        } else if (state.isCurrentResolution(letNamed)) {
            // let name|>%<|private = ...
            state.mark(letNamedAttribute, m_types.C_LET_ATTR);
        } else {
            IElementType nextTokenType = state.rawLookup(1);
            if (nextTokenType == m_types.RAW) {
                // |>%<| raw ...
                state.markStart(raw, m_types.C_RAW);
            }
        }
    }

    private void parseColon(@NotNull ParserState state) {
        if (state.isCurrentResolution(maybeRecordUsage)) {
            // yes it is a record, remove the maybe
            ParserScope latestScope = state.getLatestScope();
            state.pop();
            latestScope.rollbackTo();

            state.markScope(record, m_types.C_RECORD_EXPR, m_types.LBRACE).
                    advance().
                    mark(recordField, m_types.C_RECORD_FIELD);
            return;
        }

        if (state.isCurrentResolution(externalNamed)) {
            state.updateCurrentResolution(externalNamedSignature).
                    advance().
                    mark(signature, m_types.C_SIG_EXPR).
                    mark(signatureItem, m_types.C_SIG_ITEM);
        } else if (state.isCurrentResolution(letNamed)) {
            state.advance().
                    mark(signature, m_types.C_SIG_EXPR).
                    mark(signatureItem, m_types.C_SIG_ITEM);
        } else if (state.isCurrentResolution(moduleNamed)) {
            // module M |> :<| ...
            state.updateCurrentResolution(moduleNamedSignature);
        } else if (state.isCurrentResolution(functorNamedEq)) {
            // module M = (X:Y) |> :<| ...
            state.updateCurrentResolution(functorNamedEqColon).advance();
            IElementType tokenType = state.getTokenType();
            if (tokenType == m_types.LPAREN) {
                // module M = (X:Y) : |>(<| S ... ) = ...
                state.markScope(scope, m_types.C_SCOPED_EXPR, m_types.LPAREN).
                        dummy().
                        advance();
            }
            state.mark(functorResult, m_types.C_FUNCTOR_RESULT);
        } else if (state.isCurrentResolution(functorParam)) {
            state.updateCurrentResolution(functorParamColon);
        } else if (state.isCurrentResolution(recordField) || state.isCurrentResolution(objectField)) {
            state.advance();
            if (!state.isPreviousResolution(recordUsage) && !state.isPreviousResolution(jsObject)) {
                state.mark(signature, m_types.C_SIG_EXPR).
                        mark(signatureItem, m_types.C_SIG_ITEM);
            }
        } else if (state.isCurrentResolution(functionParameter)) {
            state.advance().
                    mark(signature, m_types.C_SIG_EXPR).
                    mark(signatureItem, m_types.C_SIG_ITEM);
        } else if (state.isCurrentResolution(namedItem)) {
            state.advance().
                    mark(signatureItem, m_types.C_SIG_ITEM);
        }
    }

    private void parseArrobase(@NotNull ParserState state) {
        if (state.isCurrentResolution(annotation)) {
            state.mark(annotationName, m_types.C_MACRO_NAME);
        }
    }

    private void parseLt(@NotNull ParserState state) {
        // Can be a symbol or a JSX tag
        IElementType nextTokenType = state.rawLookup(1);
        // Note that option is a ReasonML keyword but also a JSX keyword !
        if (nextTokenType == m_types.LIDENT || nextTokenType == m_types.UIDENT || nextTokenType == m_types.OPTION) {
            // Surely a tag
            state.remapCurrentToken(m_types.TAG_LT).
                    mark(jsxTag, m_types.C_TAG).
                    markScope(jsxStartTag, m_types.C_TAG_START, m_types.TAG_LT).
                    advance().
                    remapCurrentToken(m_types.TAG_NAME).
                    wrapWith(nextTokenType == m_types.UIDENT ? m_types.C_UPPER_SYMBOL : m_types.C_LOWER_SYMBOL);
        }
    }

    private void parseLtSlash(@NotNull ParserState state) {
        IElementType nextTokenType = state.rawLookup(1);
        // Note that option is a ReasonML keyword but also a JSX keyword !
        if (nextTokenType == m_types.LIDENT || nextTokenType == m_types.UIDENT || nextTokenType == m_types.OPTION) {
            // A closing tag
            if (state.is(m_types.C_TAG_BODY)) {
                state.popEnd();
            }

            state.remapCurrentToken(m_types.TAG_LT).
                    mark(jsxTagClose, m_types.C_TAG_CLOSE).
                    advance().
                    remapCurrentToken(m_types.TAG_NAME).
                    wrapWith(nextTokenType == m_types.UIDENT ? m_types.C_UPPER_SYMBOL : m_types.C_LOWER_SYMBOL);
        }
    }

    private void parseGt(@NotNull ParserState state) {
        if (state.is(m_types.C_TAG_START)) {
            state.remapCurrentToken(m_types.TAG_GT).
                    advance().
                    popEnd().
                    mark(jsxTagBody, m_types.C_TAG_BODY);
        } else if (state.is(m_types.C_TAG_CLOSE)) {
            // end the tag
            state.remapCurrentToken(m_types.TAG_GT).
                    advance().
                    popEndUntilResolution(jsxTag).
                    popEnd();
        }
    }

    private void parseGtAutoClose(@NotNull ParserState state) {
        if (state.isCurrentResolution(jsxTagPropertyValue)) {
            state.popEnd().popEnd();
        }

        state.advance().popEnd().popEnd();
    }

    private void parseLIdent(@NotNull ParserState state) {
        if (state.isCurrentResolution(maybeRecordUsage)) {
            // Maybe a record, we must check
            IElementType nextTokenType = state.lookAhead(1);
            if (nextTokenType == m_types.COLON) {
                // Yes, this is a record usage
                state.updateCurrentResolution(recordUsage).
                        updateCurrentCompositeElementType(m_types.C_RECORD_EXPR).
                        mark(recordField, m_types.C_RECORD_FIELD);
            }
        }

        if (state.isCurrentResolution(let)) {
            // let |>x<| ...
            state.updateCurrentResolution(letNamed).
                    wrapWith(m_types.C_LOWER_IDENTIFIER);
        } else if (state.is(m_types.C_TYPE_DECLARATION)) {
            // type |>x<| ...
            state.updateCurrentResolution(typeNamed).
                    wrapWith(m_types.C_LOWER_IDENTIFIER);
        } else if (state.isCurrentResolution(external)) {
            // external |>x<| ...
            state.updateCurrentResolution(externalNamed).
                    wrapWith(m_types.C_LOWER_IDENTIFIER);
        } else if (state.isCurrentResolution(clazz)) {
            // class |>x<| ...
            state.updateCurrentResolution(clazzNamed).
                    wrapWith(m_types.C_LOWER_IDENTIFIER);
        } else {
            if (state.isCurrentResolution(functionParameters) || state.isCurrentResolution(variantConstructor)) {
                // ( x , |>y<| ...
                state.mark(functionParameter, m_types.C_FUN_PARAM);
            } else if (state.isCurrentResolution(jsxStartTag)) {
                // This is a property
                state.popEndUntilScope();
                state.remapCurrentToken(m_types.PROPERTY_NAME).
                        mark(jsxTagProperty, m_types.C_TAG_PROPERTY).
                        setWhitespaceSkippedCallback((type, start, end) -> {
                            if (state.isCurrentResolution(jsxTagProperty) || (state.isCurrentResolution(jsxTagPropertyValue) && state.notInScopeExpression())) {
                                if (state.isCurrentResolution(jsxTagPropertyValue)) {
                                    state.popEnd();
                                }
                                state.popEnd();
                                state.setWhitespaceSkippedCallback(null);
                            }
                        });
            } else if (state.isCurrentResolution(recordBinding)) {
                state.mark(recordField, m_types.C_RECORD_FIELD);
            } else if (state.isCurrentResolution(jsObjectBinding)) {
                state.mark(recordField, m_types.C_OBJECT_FIELD);
            } else if (state.isCurrentResolution(record)) {
                state.mark(recordField, m_types.C_RECORD_FIELD);
            } else {
                IElementType nextElementType = state.lookAhead(1);
                if (nextElementType == m_types.ARROW && !state.is(m_types.C_SIG_ITEM)) {
                    // Single (paren less) function parameters
                    // |>x<| => ...
                    state.mark(function, m_types.C_FUN_EXPR).
                            mark(functionParameters, m_types.C_FUN_PARAMS).
                            mark(functionParameter, m_types.C_FUN_PARAM);
                }
            }

            if (state.is(m_types.C_DECONSTRUCTION)) {
                state.wrapWith(m_types.C_LOWER_IDENTIFIER);
            } else if (!state.isCurrentResolution(jsxTagProperty)) {
                state.wrapWith(m_types.C_LOWER_SYMBOL);
            }
        }
    }

    private void parseLArray(@NotNull ParserState state) {
        state.markScope(array, m_types.C_SCOPED_EXPR, m_types.LARRAY);
    }

    private void parseRArray(@NotNull ParserState state) {
        ParserScope scope = state.popEndUntilScopeToken(m_types.LARRAY);
        state.advance();

        if (scope != null) {
            state.popEnd();
        }
    }

    private void parseLBracket(@NotNull ParserState state) {
        IElementType nextTokenType = state.rawLookup(1);
        if (nextTokenType == m_types.ARROBASE) {
            state.markScope(annotation, m_types.C_ANNOTATION, m_types.LBRACKET);
        } else if (nextTokenType == m_types.PERCENT) {
            state.markScope(macro, m_types.C_MACRO_EXPR, m_types.LBRACKET);
        } else {
            if (state.previousElementType2 == m_types.UIDENT && state.previousElementType1 == m_types.DOT) {
                // Local open
                // M.|>[ <| ... ]
                state.markScope(localOpen, m_types.C_LOCAL_OPEN, m_types.LBRACKET);
            } else {
                state.markScope(bracket, m_types.C_SCOPED_EXPR, m_types.LBRACKET);
            }
        }
    }

    private void parseRBracket(@NotNull ParserState state) {
        ParserScope scope = state.popEndUntilScopeToken(m_types.LBRACKET);
        state.advance();

        if (scope != null) {
            state.popEnd();
        }
    }

    private void parseBracketGt(@NotNull ParserState state) {
        //state.markScope(bracketGt, m_types.C_SCOPED_EXPR, m_types.LBRACKET);
    }

    private void parseLBrace(@NotNull ParserState state) {
        if (state.previousElementType1 == m_types.DOT && state.previousElementType2 == m_types.UIDENT) {
            // Local open a js object or a record
            // Xxx.|>{<| ... }
            state.mark(localOpen, m_types.C_LOCAL_OPEN);
            IElementType nextElementType = state.lookAhead(1);
            if (nextElementType == m_types.LIDENT) {
                state.markScope(record, m_types.C_RECORD_EXPR, m_types.LBRACE);
            } else {
                state.markScope(jsObject, m_types.C_JS_OBJECT, m_types.LBRACE);
            }
        } else if (state.isCurrentResolution(typeBinding)) {
            boolean isJsObject = state.lookAhead(1) == m_types.DOT;
            state.markScope(isJsObject ? jsObjectBinding : recordBinding, isJsObject ? m_types.C_JS_OBJECT : m_types.C_RECORD_EXPR, m_types.LBRACE);
        } else if (state.isCurrentResolution(tryBody)) {
            // A try expression
            //   try (..) |>{<| .. }
            state.markScope(tryBodyWith, m_types.C_TRY_HANDLERS, m_types.LBRACE);
        } else if (state.isCurrentResolution(module)) {
            // module M = |>{<| ...
            state.markScope(moduleBinding, m_types.C_SCOPED_EXPR, m_types.LBRACE);
        } else if (isFunctorResolution(state.getLatestScope())) {
            // module M = (...) => |>{<| ...
            state.markScope(functorBinding, m_types.C_FUNCTOR_BINDING, m_types.LBRACE);
        } else if (state.isCurrentResolution(moduleNamedSignature)) {
            state.markScope(signature, m_types.C_SCOPED_EXPR, m_types.LBRACE);
        } else if (state.isCurrentResolution(letBinding)) {
            // let x = |>{<| ... }
            state.markScope(maybeRecordUsage, m_types.C_SCOPED_EXPR, m_types.LBRACE);
        } else if (state.isCurrentResolution(clazzNamedEq)) {
            // class x = |>{<| ... }
            state.markScope(clazzBody, m_types.C_OBJECT, m_types.LBRACE);
        } else if (state.isCurrentResolution(switch_)) {
            state.markScope(switchBody, m_types.C_SCOPED_EXPR, m_types.LBRACE);
        } else if (state.isCurrentResolution(functionParameters)) {
            // ( x , |>{<| ... } ) =>
            state.mark(functionParameter, m_types.C_FUN_PARAM).
                    markScope(scope, m_types.C_SCOPED_EXPR, m_types.LBRACE);
        } else {
            // it might be a js object
            IElementType nextElement = state.lookAhead(1);
            if (state.isCurrentResolution(signatureItem) && nextElement == m_types.DOT) {
                // js object detected (in definition)
                // let x: |>{<| . ... }
                state.markScope(jsObject, m_types.C_JS_OBJECT, m_types.LBRACE);
            } else if (nextElement == m_types.STRING_VALUE || nextElement == m_types.DOT) {
                // js object detected (in usage)
                // |>{<| "x" ... }
                state.markScope(jsObject, m_types.C_JS_OBJECT, m_types.LBRACE);
            } else if (nextElement == m_types.DOTDOTDOT) {
                // record usage ::  x  => |>{<| ...
                state.markScope(recordUsage, m_types.C_RECORD_EXPR, m_types.LBRACE).
                        advance().
                        mark(mixin, m_types.C_MIXIN_FIELD);
            } else if (state.is(m_types.C_FUN_BODY)) {
                // function body ::  x => |>{<| ... }
                state.updateScopeToken(m_types.LBRACE);
            } else {
                state.markScope(scope, m_types.C_SCOPED_EXPR, m_types.LBRACE);
            }
        }
    }

    private void parseRBrace(@NotNull ParserState state) {
        ParserScope scope = state.popEndUntilOneOfElementType(m_types.LBRACE, m_types.RECORD, m_types.SWITCH);
        state.advance();

        if (scope != null) {
            scope.complete();
            state.popEnd();
        }

        if (state.is(m_types.C_TAG_PROP_VALUE)) {
            state.popEndUntil(m_types.C_TAG_PROPERTY).popEnd();
        } else if (state.isCurrentResolution(localOpen)) {
            state.popEnd();
        }
    }

    private void parseLParen(@NotNull ParserState state) {
        if (state.is(m_types.C_SIG_ITEM) && state.previousElementType1 == m_types.COLON) {
            // A ReasonML signature is written like a function, but it's not
            //   (x, y) => z  alias x => y => z
            state.markScope(signatureScope, m_types.C_SCOPED_EXPR, m_types.LPAREN);
        } else if (state.isCurrentResolution(moduleBinding) && state.previousElementType1 != m_types.UIDENT) {
            // This is a functor ::  module M = |>(<| .. )
            state.popEnd().
                    updateCurrentResolution(functorNamedEq).
                    updateCurrentCompositeElementType(m_types.C_FUNCTOR).
                    markScope(functorParams, m_types.C_FUNCTOR_PARAMS, m_types.LPAREN).
                    advance().
                    mark(functorParam, m_types.C_FUNCTOR_PARAM);
        } else if (state.isCurrentResolution(maybeFunctorCall)) {
            // We know now that it is really a functor call ::  module M = X |>(<| ... )
            state.updateCurrentResolution(functorCall).
                    complete().
                    markScope(functorParams, m_types.C_FUNCTOR_PARAMS, m_types.LPAREN).
                    advance().
                    mark(functorParam, m_types.C_FUNCTOR_PARAM);
        } else if (state.isCurrentResolution(variantDeclaration)) {
            // Variant params ::  type t = | Variant |>(<| .. )
            state.markScope(variantConstructor, m_types.C_FUN_PARAMS, m_types.LPAREN).
                    advance().
                    mark(functionParameter, m_types.C_FUN_PARAM);
        } else if (state.isCurrentResolution(patternMatchVariant)) {
            // It's a constructor ::  | Variant |>(<| .. ) => ..
            state.markScope(patternMatchVariantConstructor, m_types.C_VARIANT_CONSTRUCTOR, m_types.LPAREN);
        } else if (state.previousElementType2 == m_types.UIDENT && state.previousElementType1 == m_types.DOT) {
            // Local open ::  M.|>(<| ...
            state.markScope(localOpen, m_types.C_LOCAL_OPEN, m_types.LPAREN);
        } else if (state.isCurrentResolution(clazzNamedParameters)) {
            state.markScope(clazzConstructor, m_types.C_CLASS_CONSTR, m_types.LPAREN);
        } else if (state.isCurrentResolution(try_)) {
            // Valid try expression ::  try |>(<| ...
            state.updateCurrentResolution(tryBody).
                    markScope(tryBody, m_types.C_TRY_BODY, m_types.LPAREN);
        } else if (state.isCurrentResolution(ifThenStatement)) {
            // if |>(<| ...
            state.markScope(binaryCondition, m_types.C_BIN_CONDITION, m_types.LPAREN);
        } else if (state.isCurrentResolution(switch_)) {
            // switch |>(<| ...
            state.markScope(binaryCondition, m_types.C_BIN_CONDITION, m_types.LPAREN);
        } else if (state.previousElementType1 == m_types.LIDENT && !(state.is(m_types.C_TYPE_DECLARATION) || state
                .inAny(m_types.C_TYPE_BINDING, m_types.C_SIG_ITEM))) {
            // calling a function
            state.markScope(functionCallParams, m_types.C_FUN_CALL_PARAMS, m_types.LPAREN).
                    advance();
            IElementType nextTokenType = state.getTokenType();
            if (nextTokenType != m_types.RPAREN) {
                state.mark(functionParameter, m_types.C_FUN_PARAM);
            }
        } else if (state.isCurrentResolution(let)) {
            // Overloading operator OR deconstructing a term
            //  let |>(<| + ) =
            //  let |>(<| a, b ) =
            state.markScope(genericExpression, m_types.C_SCOPED_EXPR, m_types.LPAREN);
        } else {
            IElementType nextTokenType = state.lookAhead(1);

            if (!state.in(m_types.C_SIG_ITEM)) {
                if (nextTokenType == m_types.DOT || nextTokenType == m_types.TILDE) {
                    // |>(<| .  OR  |>(<| ~
                    state.mark(function, m_types.C_FUN_EXPR).
                            markScope(functionParameters, m_types.C_FUN_PARAMS, m_types.LPAREN).
                            advance();
                    if (nextTokenType == m_types.DOT) {
                        state.advance();
                    }
                    state.mark(functionParameter, m_types.C_FUN_PARAM);
                } else if (nextTokenType == m_types.RPAREN) {
                    IElementType nexNextTokenType = state.lookAhead(2);
                    if (nexNextTokenType == m_types.ARROW) {
                        // Function with unit parameter ::  |>(<| ) => ...
                        state.mark(function, m_types.C_FUN_EXPR).
                                mark(functionParameters, m_types.C_FUN_PARAMS).
                                advance().
                                advance().
                                popEnd();
                    } else {
                        state.markScope(scope, m_types.C_SCOPED_EXPR, m_types.LPAREN);
                    }
                } else {
                    state.markScope(scope, m_types.C_SCOPED_EXPR, m_types.LPAREN);
                }
            } else {
                state.markScope(scope, m_types.C_SCOPED_EXPR, m_types.LPAREN);
            }
        }
    }

    private void parseRParen(@NotNull ParserState state) {
        if (state.isCurrentResolution(scope)) {
            IElementType aheadType = state.lookAhead(1);
            if (aheadType == m_types.ARROW && !state.in(m_types.C_SIG_ITEM)) {
                // if current resolution is UNKNOWN and next item is an arrow, it means we are processing a function definition,
                // we must rollback to the start of the scope and start the parsing again, but this time with exact information!
                ParserScope startScope = state.popEndUntilOneOfElementType(m_types.LPAREN);
                if (startScope != null) {
                    startScope.rollbackTo();
                    state.pop();
                    state.mark(function, m_types.C_FUN_EXPR).
                            markScope(functionParameters, m_types.C_FUN_PARAMS, m_types.LPAREN).
                            advance().
                            mark(functionParameter, m_types.C_FUN_PARAM);
                    return;
                }
            }
        }

        ParserScope parenScope = state.popEndUntilScopeToken(m_types.LPAREN);
        state.advance();
        IElementType nextTokenType = state.getTokenType();

        if (parenScope != null) {
            if (nextTokenType == m_types.EQ) {
                if (state.isPreviousResolution(clazzNamed)) {
                    parenScope.resolution(clazzConstructor).updateCompositeElementType(m_types.C_CLASS_CONSTR);
                    state.updateCurrentResolution(clazzNamedConstructor);
                }
            }

            // Remove the scope from the stack, we want to test its parent
            state.popEnd();

            if (nextTokenType == m_types.LPAREN) {
                if (state.isCurrentResolution(clazzNamed)) {
                    // First parens found, it must be a class parameter
                    //   class c ( ... |>)<| ( ...
                    parenScope.updateCompositeElementType(m_types.C_CLASS_PARAMS);
                    state.updateCurrentResolution(clazzNamedParameters);
                }
            } else if (nextTokenType == m_types.COLON) {
                if (state.isCurrentResolution(let)) {
                    // let ( op |>)<| : ...
                    state.updateCurrentResolution(letNamed);
                }
            } else if (state.isCurrentResolution(jsxTagPropertyValue)) {
                state.popEnd().popEnd();
            } else if (nextTokenType == m_types.ARROW && parenScope.isCompositeType(m_types.C_SIG_ITEM)) {
                state.advance().
                        mark(signatureItem, m_types.C_SIG_ITEM);
            }
        }
    }

    private void parseEq(@NotNull ParserState state) {
        if (state.isInContext(signature)) {
            if (!state.isInContext(namedItem)) {
                state.popEndUntilResolution(signature).popEnd();
            }
        }

        if (state.isCurrentResolution(typeNamed)) {
            state.updateCurrentResolution(typeNamedEq).
                    advance().
                    mark(typeBinding, m_types.C_TYPE_BINDING);
        } else if (state.isCurrentResolution(letNamed) || state.isCurrentResolution(letNamedAttribute) || state.isCurrentResolution(letNamedSignature)) {
            if (state.isCurrentResolution(letNamedSignature) || state.isCurrentResolution(letNamedAttribute)) {
                // attribute : let x%private |> = <| ...
                state.popEnd();
            }

            state.updateCurrentResolution(letNamedEq).
                    advance().
                    mark(letBinding, m_types.C_LET_BINDING);
        } else if (state.isCurrentResolution(jsxTagProperty)) {
            // <X p|> =<| ...
            state.updateCurrentResolution(jsxTagPropertyEq).
                    advance().
                    mark(jsxTagPropertyValue, m_types.C_TAG_PROP_VALUE);
        } else if (state.isCurrentResolution(module)) {
            // module M |> =<| ...
            state.advance().
                    markDummy(moduleBinding, m_types.C_UNKNOWN_EXPR/*C_DUMMY*/);
        } else if (state.isCurrentResolution(externalNamedSignature)) {
            state.updateCurrentResolution(externalNamedSignatureEq);
        } else if (state.isCurrentResolution(clazzNamed) || state.isCurrentResolution(clazzNamedParameters) || state
                .isCurrentResolution(clazzNamedConstructor)) {
            state.updateCurrentResolution(clazzNamedEq);
        } else if (state.isCurrentResolution(functionParameter)) {
            // call(~x |> =<| .. )
            state.advance().
                    mark(functionParameterBinding, m_types.C_FUN_PARAM_BINDING);
        }
    }

    private void parseSemi(@NotNull ParserState state) {
        // Special case for the `fun` keyword that must be seen like a switch
        if (state.isInContext(funPattern)) {
            state.popEndUntilResolution(funPattern);
        }

        if (!state.isCurrentResolution(patternMatchBody)) {
            state.popEndUntilScope();
        }
    }

    private void parseUIdent(@NotNull ParserState state) {
        if (DUMMY_IDENTIFIER_TRIMMED.equals(state.getTokenText())) {
            return;
        }

        if (state.isCurrentResolution(module)) {
            // module |>M<| ...
            state.wrapWith(m_types.C_UPPER_IDENTIFIER);
        } else if (state.isCurrentResolution(variantDeclaration)) {
            // Declaring a variant ::  type t = | |>X<| ..
            state.wrapWith(m_types.C_UPPER_IDENTIFIER);
        } else if (state.isCurrentResolution(exception)) {
            // Declaring an exception ::  exception |>E<| ..
            state.updateCurrentResolution(exceptionNamed).
                    wrapWith(m_types.C_UPPER_IDENTIFIER);
        } else {
            if (state.isCurrentResolution(open)) {
                // It is a module name/path, or maybe a functor call ::  open |>M<| ...
                state.markOptional(maybeFunctorCall, m_types.C_FUNCTOR_CALL);
            } else if (state.isCurrentResolution(include)) {
                // It is a module name/path, or maybe a functor call
                //   include |>M<| ...
                state.markOptional(maybeFunctorCall, m_types.C_FUNCTOR_CALL);
            } else if (state.isCurrentResolution(moduleBinding)) {
                // it might be a module functor call
                //  module M = |>X<| ( ... )
                state.markOptional(maybeFunctorCall, m_types.C_FUNCTOR_CALL);
            } else if ((state.isCurrentResolution(jsxStartTag) || state.isCurrentResolution(jsxTagClose)) && state.previousElementType1 == m_types.DOT) {
                // a namespaced custom component
                // <X.|>Y<| ...
                state.remapCurrentToken(m_types.TAG_NAME);
            } else if (state.isCurrentResolution(patternMatch)) {
                IElementType nextElementType = state.lookAhead(1);
                if (nextElementType != m_types.DOT) {
                    // Defining a pattern match
                    // switch (c) { | |>X<|
                    state.updateCurrentResolution(patternMatchVariant).
                            //remapCurrentToken(m_types.VARIANT_NAME).
                                    wrapWith(m_types.C_UPPER_SYMBOL);
                    return;
                }
            } else if (state.isCurrentResolution(functionParameter)) {
                // ok
            } else {
                IElementType nextElementType = state.lookAhead(1);
                //if (!state.isCurrentResolution(moduleNamedEq) && !state.isCurrentResolution(maybeFunctorCall) && nextElementType == m_types.LPAREN) {
                // A variant with a constructor
                //state.remapCurrentToken(m_types.VARIANT_NAME);
                //if (state.isCurrentResolution(typeBinding)) {
                //    state.mark(variantDeclaration, m_types.C_VARIANT_DECL);
                //}
                //state.wrapWith(m_types.C_UPPER_IDENTIFIER);
                //return;
                //} else
                if (state.isCurrentResolution(typeBinding) && nextElementType != m_types.DOT) {
                    // We are declaring a variant without a pipe before
                    // type t = |>X<| | ...
                    state.mark(variantDeclaration, m_types.C_VARIANT_DECL).
                            wrapWith(m_types.C_UPPER_IDENTIFIER);
                    return;
                } else if (!state.isCurrentResolution(moduleNamedEq) && !state.isCurrentResolution(maybeFunctorCall) && nextElementType != m_types.DOT) {
                    // Must be a variant call
                    state.//remapCurrentToken(m_types.VARIANT_NAME).
                            wrapWith(m_types.C_VARIANT);
                    return;
                }
            }

            state.wrapWith(m_types.C_UPPER_SYMBOL);
        }
    }

    private void parseSwitch(@NotNull ParserState state) {
        state.mark(switch_, m_types.C_SWITCH_EXPR);
    }

    private void parseTry(@NotNull ParserState state) {
        state.markStart(try_, m_types.C_TRY_EXPR);
    }

    private void parseArrow(@NotNull ParserState state) {
        if (state.is(m_types.C_SIG_ITEM)) {
            state.popEnd().
                    advance().
                    mark(signatureItem, m_types.C_SIG_ITEM);
        } else if (state.isCurrentResolution(functionParameter)) {
            // x |>=><| ...
            state.popEndUntilResolution(function).
                    advance().
                    mark(functionBody, m_types.C_FUN_BODY);
        } else if (state.isCurrentResolution(function)) {
            // let x = ( ... ) |>=><|
            state.advance().
                    mark(functionBody, m_types.C_FUN_BODY);
        } else if (state.isCurrentResolution(functorNamedEq) || state.isCurrentResolution(functorResult)) {
            // module Make = (M) : R |>=><| ...
            if (state.isCurrentResolution(functorResult)) {
                state.popEnd();
            }
            //state.advance().mark(functorBinding, m_types.C_FUNCTOR_BINDING);
        } else if (state.isCurrentResolution(patternMatchVariant) || state.isCurrentResolution(patternMatchVariantConstructor)) {
            // switch ( ... ) { | ... |>=><|
            if (state.isCurrentResolution(patternMatchVariantConstructor)) {
                state.popEnd();
            }
            state.advance().
                    mark(patternMatchBody, m_types.C_PATTERN_MATCH_BODY);
        }
    }
}
