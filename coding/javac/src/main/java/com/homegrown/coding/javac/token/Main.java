package com.homegrown.coding.javac.token;

/**
 * @author jam
 * @description
 * @create 2019-07-25
 **/
public class Main {
    public static void main(String[] args) {

        int compile = com.sun.tools.javac.Main.compile(new String[]{"/Users/jam/company/code/homegrown/coding/javac/src/main/java/com/homegrown/coding/javac/token/Token.java"});
        System.out.println(compile);
//        Float value = Float.valueOf("0x1.0p1");
//        System.out.println(value);
//        testVolatile();
//         testEnumMap();
    }

    public static int reverse(int i) {
        // HD, Figure 7-1
        i = (i & 0x55555555) << 1 | (i & 0xAAAAAAAA) >>> 1;
        i = (i & 0x33333333) << 2 | (i & 0xCCCCCCCC) >>> 2;
        i = (i & 0x0f0f0f0f) << 4 | (i >>> 4) & 0x0f0f0f0f;
        i = (i << 24) | ((i & 0xff00) << 8) |
                ((i >>> 8) & 0xff00) | (i >>> 24);
        return i;
    }

    public static void testRevsersAtInteger(){
        Integer a = 0x1234;
        Integer b = 123456;
        System.out.println(reverse(a));
        System.out.println(Integer.toString(a,2));
        System.out.println(Integer.reverse(a));

        System.out.println(Integer.toBinaryString(305419896));

        //i=10010001101000101011001111000
        //i & 0x55555555=10000000101000101010001010000
        System.out.println(Integer.toBinaryString(269767760));
        //(i & 0x55555555) << 1 = 100000001010001010100010100000
        System.out.println(Integer.toBinaryString(539535520));
        //i >>> 1 = 1001000110100010101100111100
        System.out.println(Integer.toBinaryString(152709948));
        //(i >>> 1) & 0x55555555 = 1000100000000000100010100
        System.out.println(Integer.toBinaryString(17826068));
    }

    static volatile int i=0;
    public static void testVolatile(){

        new Thread(()->{
            for (int j=0;j<10000;j++){
                i++;
            }
        }).start();
        new Thread(()->{
            for (int j=0;j<10000;j++){
                i++;
            }
        }).start();

        System.out.println(i);
    }

    public static void increase(){

    }

    public static void testEnumMap(){
        long x = 1L << 2;
        System.out.println(Long.toBinaryString(x));
    }
}
