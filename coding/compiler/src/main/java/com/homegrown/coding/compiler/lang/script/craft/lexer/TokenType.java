package com.homegrown.coding.compiler.lang.script.craft.lexer;

/**
 * @author youyu
 * @date 2020/10/3 1:52 PM
 */
public enum TokenType {
    Plus,   // +
    Minus,  // -
    Star,   // *
    Slash,  // /

    GE,     // >=
    GT,     // >
    EQ,     // ==
    LE,     // <=
    LT,     // <

    SemiColon, // ;
    LeftParen, // (
    RightParen,// )

    Assignment,// =

    If,
    Else,

    Int,

    Identifier,     //标识符

    IntLiteral,     //整型字面量
    StringLiteral   //字符串字面量
}
