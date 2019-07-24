package com.demo.test;

/**
 * @author jam
 * @description
 * @create 2018-12-25
 **/
public class Interview {
    public static void main(String[] args) {
        String a[] = new String[5];
        System.out.println(a);
        Demo example = new Demo("example");
        System.out.println(example.getStr1()+"-"+example.getStr2());
    }
}

class Example{
    private String str1;
    public Example(String s){
        this.str1 = s;
    }
}

class Demo extends Example{
    private String str1 = "example1";
    private String str2 = "example2";

    public Demo(String s){
        super(s);
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }
}