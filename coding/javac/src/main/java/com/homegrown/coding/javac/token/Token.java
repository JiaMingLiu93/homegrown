package com.homegrown.coding.javac.token;

/**
 * @author jam
 * @description
 * @create 2019-07-25
 **/
public class Token {
    int a;
    int c = a + 1;

    public Token(int a, int c) {
        this.a = a;
        this.c = c;
    }

    public Token() {
    }

    private int cal(){
        return a+c;
    }

    public static void main(String[] args) {
        Token token = new Token();
        token.cal();
    }
}
