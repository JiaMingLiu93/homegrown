package com.demo.test.math;

/**
 * @author jam
 * @description
 * @create 2018-11-10
 **/
public class MathTest {
    public static void main(String[] args) {
        //2^32
        System.out.println(1L << 32);
        System.out.println(35>>1);
        System.out.println(35>>33);
        System.out.println((-35)>>>1);
        float a = 1f;
        float b = 0.9f;
        System.out.println(a-b);
    }
}
