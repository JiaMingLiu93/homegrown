package com.homegrown.coding.compiler.lang.script.craft.antlrtest.test;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.util.List;

/**
 * @author youyu
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        Hello hello = new Hello(new ANTLRInputStream("int age = 45;\n" +
                "if (age >= 17+8+20){\n" +
                "    printf(\"Hello old man!\");\n" +
                "}"));
        CommonTokenStream tokens = new CommonTokenStream(hello);

        List<Token> tokens1 = tokens.getTokens();

        int numberOfOnChannelTokens = tokens.getNumberOfOnChannelTokens();
        System.out.println(numberOfOnChannelTokens);
        for (Token token : tokens1){
            System.out.println(token.getText());
        }

    }
}
