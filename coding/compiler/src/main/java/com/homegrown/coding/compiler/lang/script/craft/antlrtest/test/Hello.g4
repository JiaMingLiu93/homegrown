/**
这是一个简单的词法规则文件，把SimpleLexer里用到的词法用Antlr重新实现一遍。
使用方法：
./antlr com/homegrown/coding/compiler/lang/script/craft/antlrtest/test/Hello.g4   //生成Hello.java
./compile com/homegrown/coding/compiler/lang/script/craft/antlrtest/test/Hello.java   //编译Hello.java
./grun com.homegrown.coding.compiler.lang.script.craft.antlrtest.test.Hello tokens -tokens com/homegrown/coding/compiler/lang/script/craft/antlrtest/test/hello.play
这样会把hello.play中的Token都解析打印出来。
*/

lexer grammar Hello;  //lexer关键字意味着这是一个词法规则文件，要与文件名相同

@header {
package com.homegrown.coding.compiler.lang.script.craft.antlrtest.test;
}

//关键字
If :               'if';   //可以在程序里用‘如果’来代替'if'
Int :              'int';

//常量
IntLiteral:        [0-9]+;
StringLiteral:      '"' .*? '"' ;  //字符串常量

//操作符
AssignmentOP:       '=' ;
RelationalOP:       '=='|'>'|'>='|'<' |'<=' ;
Star:               '*';
Plus:               '+';
Sharp:              '#';
SemiColon:          ';';
Dot:                '.';
Comm:               ',';
LeftBracket :       '[';
RightBracket:       ']';
LeftBrace:          '{';
RightBrace:         '}';
LeftParen:          '(';
RightParen:         ')';

//标识符
Id :                [a-zA-Z_] ([a-zA-Z_] | [0-9])*;

//空白字符，抛弃
Whitespace:         [ \t]+ -> skip;
Newline:            ( '\r' '\n'?|'\n')-> skip;