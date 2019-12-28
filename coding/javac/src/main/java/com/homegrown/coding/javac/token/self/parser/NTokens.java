package com.homegrown.coding.javac.token.self.parser;

import com.sun.tools.javac.api.Formattable;
import com.sun.tools.javac.api.Messages;
import com.sun.tools.javac.util.*;

import java.util.Locale;

/**
 * @author youyu
 * @date 2019/9/8 4:11 AM
 */
public class NTokens {

    private final Names names;

    /** The names of all tokens.
     */
    private Name[] tokenName = new Name[NTokenKind.values().length];

    /**  The number of the last entered keyword.
     */
    private int maxKey = 0;

    /**
     * Keyword array. Maps name indices to Token.
     */
    private final NTokenKind[] key;

    public static final Context.Key<NTokens> tokensKey =
            new Context.Key<>();


    public static NTokens instance(Context context) {
        NTokens instance = context.get(tokensKey);
        if (instance == null)
            instance = new NTokens(context);
        return instance;
    }

    protected NTokens(Context context) {
        context.put(tokensKey, this);
        names = Names.instance(context);
        //iterate NTokenKind.values()
        for (NTokenKind t : NTokenKind.values()) {
            if (t.name != null)
                enterKeyword(t.name, t);
            else
                tokenName[t.ordinal()] = null;
        }

        key = new NTokenKind[maxKey+1];
        for (int i = 0; i <= maxKey; i++) key[i] = NTokenKind.IDENTIFIER;
        //iterate NTokenKind.values()
        for (NTokenKind t : NTokenKind.values()) {
            if (t.name != null)
                key[tokenName[t.ordinal()].getIndex()] = t;
        }
    }

    private void enterKeyword(String s, NTokenKind token) {
        Name n = names.fromString(s);
        tokenName[token.ordinal()] = n;
        if (n.getIndex() > maxKey) maxKey = n.getIndex();
    }

    /**
     * Create a new token given a name; if the name corresponds to a token name,
     * a new token of the corresponding kind is returned; otherwise, an
     * identifier token is returned.
     */
    NTokenKind lookupKind(Name name) {
        return (name.getIndex() > maxKey) ? NTokenKind.IDENTIFIER : key[name.getIndex()];
    }

    /**
     * This enum defines all tokens used by the javac scanner. A token is
     * optionally associated with a name.
     */
    public enum NTokenKind implements Formattable, Filter<NTokens.NTokenKind> {
        EOF(),
        ERROR(),
        IDENTIFIER(NToken.NTag.NAMED),
        ABSTRACT("abstract"),
        ASSERT("assert", NToken.NTag.NAMED),
        BOOLEAN("boolean", NToken.NTag.NAMED),
        BREAK("break"),
        BYTE("byte", NToken.NTag.NAMED),
        CASE("case"),
        CATCH("catch"),
        CHAR("char", NToken.NTag.NAMED),
        CLASS("class"),
        CONST("const"),
        CONTINUE("continue"),
        DEFAULT("default"),
        DO("do"),
        DOUBLE("double", NToken.NTag.NAMED),
        ELSE("else"),
        ENUM("enum", NToken.NTag.NAMED),
        EXTENDS("extends"),
        FINAL("final"),
        FINALLY("finally"),
        FLOAT("float", NToken.NTag.NAMED),
        FOR("for"),
        GOTO("goto"),
        IF("if"),
        IMPLEMENTS("implements"),
        IMPORT("import"),
        INSTANCEOF("instanceof"),
        INT("int", NToken.NTag.NAMED),
        INTERFACE("interface"),
        LONG("long", NToken.NTag.NAMED),
        NATIVE("native"),
        NEW("new"),
        PACKAGE("package"),
        PRIVATE("private"),
        PROTECTED("protected"),
        PUBLIC("public"),
        RETURN("return"),
        SHORT("short", NToken.NTag.NAMED),
        STATIC("static"),
        STRICTFP("strictfp"),
        SUPER("super", NToken.NTag.NAMED),
        SWITCH("switch"),
        SYNCHRONIZED("synchronized"),
        THIS("this", NToken.NTag.NAMED),
        THROW("throw"),
        THROWS("throws"),
        TRANSIENT("transient"),
        TRY("try"),
        VOID("void", NToken.NTag.NAMED),
        VOLATILE("volatile"),
        WHILE("while"),
        INTLITERAL(NToken.NTag.NUMERIC),
        LONGLITERAL(NToken.NTag.NUMERIC),
        FLOATLITERAL(NToken.NTag.NUMERIC),
        DOUBLELITERAL(NToken.NTag.NUMERIC),
        CHARLITERAL(NToken.NTag.NUMERIC),
        STRINGLITERAL(NToken.NTag.STRING),
        TRUE("true", NToken.NTag.NAMED),
        FALSE("false", NToken.NTag.NAMED),
        NULL("null", NToken.NTag.NAMED),
        UNDERSCORE("_", NToken.NTag.NAMED),
        ARROW("->"),
        COLCOL("::"),
        LPAREN("("),
        RPAREN(")"),
        LBRACE("{"),
        RBRACE("}"),
        LBRACKET("["),
        RBRACKET("]"),
        SEMI(";"),
        COMMA(","),
        DOT("."),
        ELLIPSIS("..."),
        EQ("="),
        GT(">"),
        LT("<"),
        BANG("!"),
        TILDE("~"),
        QUES("?"),
        COLON(":"),
        EQEQ("=="),
        LTEQ("<="),
        GTEQ(">="),
        BANGEQ("!="),
        AMPAMP("&&"),
        BARBAR("||"),
        PLUSPLUS("++"),
        SUBSUB("--"),
        PLUS("+"),
        SUB("-"),
        STAR("*"),
        SLASH("/"),
        AMP("&"),
        BAR("|"),
        CARET("^"),
        PERCENT("%"),
        LTLT("<<"),
        GTGT(">>"),
        GTGTGT(">>>"),
        PLUSEQ("+="),
        SUBEQ("-="),
        STAREQ("*="),
        SLASHEQ("/="),
        AMPEQ("&="),
        BAREQ("|="),
        CARETEQ("^="),
        PERCENTEQ("%="),
        LTLTEQ("<<="),
        GTGTEQ(">>="),
        GTGTGTEQ(">>>="),
        MONKEYS_AT("@"),
        CUSTOM;

        public final String name;
        public final NToken.NTag tag;

        NTokenKind() {
            this(null, NToken.NTag.DEFAULT);
        }

        NTokenKind(String name) {
            this(name, NToken.NTag.DEFAULT);
        }

        NTokenKind(NToken.NTag tag) {
            this(null, tag);
        }

        NTokenKind(String name, NToken.NTag tag) {
            this.name = name;
            this.tag = tag;
        }

        public String toString() {
            switch (this) {
                case IDENTIFIER:
                    return "token.identifier";
                case CHARLITERAL:
                    return "token.character";
                case STRINGLITERAL:
                    return "token.string";
                case INTLITERAL:
                    return "token.integer";
                case LONGLITERAL:
                    return "token.long-integer";
                case FLOATLITERAL:
                    return "token.float";
                case DOUBLELITERAL:
                    return "token.double";
                case ERROR:
                    return "token.bad-symbol";
                case EOF:
                    return "token.end-of-input";
                case DOT: case COMMA: case SEMI: case LPAREN: case RPAREN:
                case LBRACKET: case RBRACKET: case LBRACE: case RBRACE:
                    return "'" + name + "'";
                default:
                    return name;
            }
        }

        public String getKind() {
            return "Token";
        }

        public String toString(Locale locale, Messages messages) {
            return name != null ? toString() : messages.getLocalizedString(locale, "compiler.misc." + toString());
        }

        @Override
        public boolean accepts(NTokenKind that) {
            return this == that;
        }
    }

    /**
     * This is the class representing a javac token. Each token has several fields
     * that are set by the javac lexer (i.e. start/end position, string value, etc).
     */
    public static class NToken {

        /** tags constants **/
        enum NTag {
            DEFAULT,
            NAMED,
            STRING,
            NUMERIC;
        }

        /** The token kind */
        public final NTokenKind kind;

        /** The start position of this token */
        public final int pos;

        /** The end position of this token */
        public final int endPos;

        /** Comment reader associated with this token */
        public final List<NComment> comments;

        NToken(NTokenKind kind, int pos, int endPos, List<NComment> comments) {
            this.kind = kind;
            this.pos = pos;
            this.endPos = endPos;
            this.comments = comments;
            checkKind();
        }

//        NToken[] split(NToken tokens) {
//            if (kind.name.length() < 2 || kind.tag != NTag.DEFAULT) {
//                throw new AssertionError("Cant split" + kind);
//            }
//
//            Tokens.TokenKind t1 = tokens.lookupKind(kind.name.substring(0, 1));
//            Tokens.TokenKind t2 = tokens.lookupKind(kind.name.substring(1));
//
//            if (t1 == null || t2 == null) {
//                throw new AssertionError("Cant split - bad subtokens");
//            }
//            return new Tokens.Token[] {
//                    new Tokens.Token(t1, pos, pos + t1.name.length(), comments),
//                    new Tokens.Token(t2, pos + t1.name.length(), endPos, null)
//            };
//        }

        protected void checkKind() {
            if (kind.tag != NTag.DEFAULT) {
                throw new AssertionError("Bad token kind - expected " + NTag.STRING);
            }
        }

        public Name name() {
            throw new UnsupportedOperationException();
        }

        public String stringVal() {
            throw new UnsupportedOperationException();
        }

        public int radix() {
            throw new UnsupportedOperationException();
        }
    }

    public interface NComment {

        enum NCommentStyle {
            LINE,
            BLOCK,
            JAVADOC,
        }

        String getText();
        int getSourcePos(int index);
        NCommentStyle getStyle();
        boolean isDeprecated();
    }

    final static class NNamedToken extends NToken {
        /** The name of this token */
        public final Name name;

        public NNamedToken(NTokenKind kind, int pos, int endPos, Name name, List<NComment> comments) {
            super(kind, pos, endPos, comments);
            this.name = name;
        }

        protected void checkKind() {
            if (kind.tag != NTag.NAMED) {
                throw new AssertionError("Bad token kind - expected " + NTag.NAMED);
            }
        }

        @Override
        public Name name() {
            return name;
        }
    }

    static class NStringToken extends NToken {
        /** The string value of this token */
        public final String stringVal;

        public NStringToken(NTokenKind kind, int pos, int endPos, String stringVal, List<NComment> comments) {
            super(kind, pos, endPos, comments);
            this.stringVal = stringVal;
        }

        protected void checkKind() {
            if (kind.tag != NTag.STRING) {
                throw new AssertionError("Bad token kind - expected " + NTag.STRING);
            }
        }

        @Override
        public String stringVal() {
            return stringVal;
        }
    }

    final static class NNumericToken extends NStringToken {
        /** The 'radix' value of this token */
        public final int radix;

        public NNumericToken(NTokenKind kind, int pos, int endPos, String stringVal, int radix, List<NComment> comments) {
            super(kind, pos, endPos, stringVal, comments);
            this.radix = radix;
        }

        protected void checkKind() {
            if (kind.tag != NTag.NUMERIC) {
                throw new AssertionError("Bad token kind - expected " + NTag.NUMERIC);
            }
        }

        @Override
        public int radix() {
            return radix;
        }
    }
}

