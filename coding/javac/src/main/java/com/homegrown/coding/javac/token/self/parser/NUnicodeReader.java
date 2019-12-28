package com.homegrown.coding.javac.token.self.parser;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.util.*;

import java.nio.CharBuffer;
import java.util.Arrays;

import static com.sun.tools.javac.util.LayoutCharacters.EOI;

/**
 * The char reader used by the javac lexer/tokenizer. Returns the sequence of
 * characters contained in the input stream, handling unicode escape accordingly.
 * Additionally, it provides features for saving chars into a buffer and to retrieve
 * them at a later stage.
 * 字符读取器，处理unicode，返回字符序列
 * @author jam
 * @description
 * @create 2019-09-05
 **/
public class NUnicodeReader {
    /** The input buffer, index of next character to be read,
     *  index of one past last character in buffer.
     */
    protected char[] buf;
    protected int bp;
    protected final int buflen;

    /** The current character.
     */
    protected char ch;

    /** The buffer index of the last converted unicode character
     */
    protected int unicodeConversionBp = -1;

    protected Log log;
    protected Names names;

    /** A character buffer for saved chars.
     */
    protected char[] sbuf = new char[128];
    protected int sp;

    protected NUnicodeReader(NScannerFactory sf, CharBuffer buffer) {
        this(sf, JavacFileManager.toArray(buffer), buffer.limit());
    }

    protected NUnicodeReader(NScannerFactory sf, char[] input, int inputLength) {
        log = sf.log;
        names = sf.names;
        if (inputLength == input.length) {
            if (input.length > 0 && Character.isWhitespace(input[input.length - 1])) {
                inputLength--;
            } else {
                input = Arrays.copyOf(input, inputLength + 1);
            }
        }
        buf = input;
        buflen = inputLength;
        buf[buflen] = EOI;
        bp = -1;
        scanChar();
    }

    /** Read next character.
     */
    protected void scanChar() {
        if (bp < buflen) {
            ch = buf[++bp];
            if (ch == '\\') {
                convertUnicode();
            }
        }
    }

    /** Convert unicode escape; bp points to initial '\' character
     *  (Spec 3.3).
     */
    protected void convertUnicode() {
        if (ch == '\\' && unicodeConversionBp != bp) {
            bp++; ch = buf[bp];
            if (ch == 'u') {
                do {
                    bp++; ch = buf[bp];
                } while (ch == 'u');
                int limit = bp + 3;
                if (limit < buflen) {
                    int d = digit(bp, 16);
                    int code = d;
                    while (bp < limit && d >= 0) {
                        bp++; ch = buf[bp];
                        d = digit(bp, 16);
                        code = (code << 4) + d;
                    }
                    if (d >= 0) {
                        ch = (char)code;
                        unicodeConversionBp = bp;
                        return;
                    }
                }
                log.error(bp, "illegal.unicode.esc");
            } else {
                bp--;
                ch = '\\';
            }
        }
    }

    /** Convert an ASCII digit from its base (8, 10, or 16)
     *  to its value.
     */
    protected int digit(int pos, int base) {
        char c = ch;
        int result = Character.digit(c, base);
        if (result >= 0 && c > 0x7f) {
            log.error(pos + 1, "illegal.nonascii.digit");
            ch = "0123456789abcdef".charAt(result);
        }
        return result;
    }

    /**
     * Returns a copy of the input buffer, up to its inputLength.
     * Unicode escape sequences are not translated.
     *
     * 复制一份输入缓存
     * Unicode转义序列不会被翻译
     */
    public char[] getRawCharacters() {
        char[] chars = new char[buflen];
        System.arraycopy(buf, 0, chars, 0, buflen);
        return chars;
    }

    /**
     * Returns a copy of a character array subset of the input buffer.
     * The returned array begins at the {@code beginIndex} and
     * extends to the character at index {@code endIndex - 1}.
     * Thus the length of the substring is {@code endIndex-beginIndex}.
     * This behavior is like
     * {@code String.substring(beginIndex, endIndex)}.
     * Unicode escape sequences are not translated.
     *
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex the ending index, exclusive.
     * @throws ArrayIndexOutOfBoundsException if either offset is outside of the
     *         array bounds
     */
    public char[] getRawCharacters(int beginIndex, int endIndex) {
        int length = endIndex - beginIndex;
        char[] chars = new char[length];
        System.arraycopy(buf, beginIndex, chars, 0, length);
        return chars;
    }

    /** Append a character to sbuf.
     */
    protected void putChar(char ch, boolean scan) {
        sbuf = ArrayUtils.ensureCapacity(sbuf, sp);
        sbuf[sp++] = ch;
        if (scan)
            scanChar();
    }

    protected void putChar(char ch) {
        putChar(ch, false);
    }

    protected void putChar(boolean scan) {
        putChar(ch, scan);
    }

    Name name() {
        return names.fromChars(sbuf, 0, sp);
    }

    /** Are surrogates supported?
     */
    final static boolean surrogatesSupported = surrogatesSupported();
    private static boolean surrogatesSupported() {
        try {
            Character.isHighSurrogate('a');
            return true;
        } catch (NoSuchMethodError ex) {
            return false;
        }
    }

    /** Scan surrogate pairs.  If 'ch' is a high surrogate and
     *  the next character is a low surrogate, then put the low
     *  surrogate in 'ch', and return the high surrogate.
     *  otherwise, just return 0.
     */
    protected char scanSurrogates() {
        if (surrogatesSupported && Character.isHighSurrogate(ch)) {
            char high = ch;

            scanChar();

            if (Character.isLowSurrogate(ch)) {
                return high;
            }

            ch = high;
        }

        return 0;
    }

    /** Read next character in comment, skipping over double '\' characters.
     */
    protected void scanCommentChar() {
        scanChar();
        if (ch == '\\') {
            if (peekChar() == '\\' && !isUnicode()) {
                skipChar();
            } else {
                convertUnicode();
            }
        }
    }

    protected char peekChar() {
        return buf[bp + 1];
    }

    protected boolean isUnicode() {
        return unicodeConversionBp == bp;
    }

    protected void skipChar() {
        bp++;
    }

    String chars() {
        return new String(sbuf, 0, sp);
    }

    public static void main(String[] args) {
        System.out.println((char)26469);

        String value = "i'm from China";
        String value1 = "\\u6211\\u6765\\u81ea\\u4e2d\\u56fd\\u000d\\u000a";
        String value2 = "我来自中国";
        CharBuffer buffer = CharBuffer.wrap(value1);
        NUnicodeReader unicodeReader = new NUnicodeReader(new NScannerFactory(new Context()), buffer);
        System.out.println(unicodeReader.sbuf);
        unicodeReader.scanChar();
        System.out.println(unicodeReader.ch);
        System.out.println(unicodeReader.sbuf);
    }
}
