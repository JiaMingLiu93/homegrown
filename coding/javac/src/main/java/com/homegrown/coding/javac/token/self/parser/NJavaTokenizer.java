package com.homegrown.coding.javac.token.self.parser;

import com.sun.tools.javac.code.Source;
import com.sun.tools.javac.parser.ScannerFactory;
import com.sun.tools.javac.parser.Tokens;
import com.sun.tools.javac.parser.UnicodeReader;
import com.sun.tools.javac.util.Log;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Position;

import java.nio.CharBuffer;

/**
 * @author jam
 * @description
 * @create 2019-09-04
 **/
public class NJavaTokenizer {
    private static final boolean scannerDebug = false;

    /** Allow hex floating-point literals.
     */
    private boolean allowHexFloats;

    /** Allow binary literals.
     */
    private boolean allowBinaryLiterals;

    /** Allow underscores in literals.
     */
    private boolean allowUnderscoresInLiterals;

    /** The source language setting.
     */
    private Source source;

    /** The log to be used for error reporting.
     */
    private final Log log;

    /** The token factory. */
    private final Tokens tokens;

    /** The token kind, set by nextToken().
     */
    protected Tokens.TokenKind tk;

    /** The token's radix, set by nextToken().
     */
    protected int radix;

    /** The token's name, set by nextToken().
     */
    protected Name name;

    /** The position where a lexical error occurred;
     */
    protected int errPos = Position.NOPOS;

    /** The Unicode reader (low-level stream reader).
     */
    protected NUnicodeReader reader;

    protected NScannerFactory fac;

    private static final boolean hexFloatsWork = hexFloatsWork();
    private static boolean hexFloatsWork() {
        try {
            Float.valueOf("0x1.0p1");
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    protected NJavaTokenizer(NScannerFactory fac, CharBuffer buf) {
        this(fac, new NUnicodeReader(fac, buf));
    }

    protected NJavaTokenizer(NScannerFactory fac, char[] buf, int inputLength) {
        this(fac, new NUnicodeReader(fac, buf, inputLength));
    }

    protected NJavaTokenizer(NScannerFactory fac, NUnicodeReader reader) {
        this.fac = fac;
        this.log = fac.log;
        this.tokens = fac.tokens;
        this.source = fac.source;
        this.reader = reader;
        this.allowBinaryLiterals = source.allowBinaryLiterals();
        this.allowHexFloats = source.allowHexFloats();
        this.allowUnderscoresInLiterals = source.allowUnderscoresInLiterals();
    }
}
