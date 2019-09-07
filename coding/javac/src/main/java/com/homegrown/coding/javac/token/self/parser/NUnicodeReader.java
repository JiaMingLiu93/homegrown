package com.homegrown.coding.javac.token.self.parser;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;
import com.sun.tools.javac.util.Names;

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

    public static void main(String[] args) {
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
