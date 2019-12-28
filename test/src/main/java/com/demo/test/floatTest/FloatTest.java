package com.demo.test.floatTest;

/**
 * @author youyu
 * @date 2019/12/14 4:29 PM
 */
public class FloatTest {
    public static void main(String[] args) {
        kahanSummation();
    }

    private static void testWrongSum(){
        float sum = 0.0f;
        for (int i = 0; i < 20000000; i++) {
            float x = 1.0f; sum += x;
        }
        System.out.println("sum is " + sum);
    }

    private static void kahanSummation(){
        float sum = 0.0f;
        float c = 0.0f;
        for (int i = 0; i < 20000000; i++) {
            float x = 1.0f;
            float y = x - c;
            float t = sum + y;
            c = (t-sum)-y; sum = t;
        }
        System.out.println("sum is " + sum);
    }
}
