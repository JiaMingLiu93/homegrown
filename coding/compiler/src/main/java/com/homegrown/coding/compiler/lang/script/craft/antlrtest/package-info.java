package com.homegrown.coding.compiler.lang.script.craft.antlrtest;
/*

    grammar目录只有CommonLexer.g4、PlayScript.g4两个文件，其余都是生成的，可以删除，然后通过以下命令进行生成

    在antlr脚本所在目录下执行如下命令，会生成一堆文件：
    ./antlr com/homegrown/coding/compiler/lang/script/craft/antlrtest/grammar/PlayScript.g4
    再执行如下命令，进行编译，也会生成一堆字节码文件：
    ./compile com/homegrown/coding/compiler/lang/script/craft/antlrtest/grammar/*.java
    当语法分析器生成好后，就可以来验证一下：
    ./grun com.homegrown.coding.compiler.lang.script.craft.antlrtest.grammar.PlayScript expression -gui
    输完这条命令后，继续输入age+10*2+10，然后再输入^D（指文件结束符：EOF），接着就会跳出一个图形界面展示AST

    通过下面这条命令生成PlayScriptBaseVisitor和PlayScriptVisitor两个文件，用来遍历AST
    ./antlr -visitor com/homegrown/coding/compiler/lang/script/craft/antlrtest/grammar/PlayScript.g4

 */