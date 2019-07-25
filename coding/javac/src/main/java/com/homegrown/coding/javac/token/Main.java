package com.homegrown.coding.javac.token;

/**
 * @author jam
 * @description
 * @create 2019-07-25
 **/
public class Main {
    public static void main(String[] args) {
        int compile = com.sun.tools.javac.Main.compile(new String[]{"/Users/jam/study/code/homegrown/coding/javac/src/main/java/com/homegrown/coding/javac/token/Token.java"});
        System.out.println(compile);
    }
}
