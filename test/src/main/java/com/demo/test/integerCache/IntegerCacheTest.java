package com.demo.test.integerCache;

import java.lang.reflect.Field;

/**
 * @author jam
 * @description
 * @create 2018-12-02
 **/
public class IntegerCacheTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a=1,b=2;
        swap(a,b);
        System.out.println("a="+a+",b="+b);
        swapWithReflection(a,b);
        System.out.println("a="+a+",b="+b);
    }

    private static void swap(Integer a, Integer b) {
        Integer temp = a;
        a = b;
        b = temp;
    }

    private static void swapWithReflection(Integer a, Integer b) throws NoSuchFieldException, IllegalAccessException {
        Field value = a.getClass().getDeclaredField("value");
        value.setAccessible(true);

        int temp = a.intValue();
        Integer tempInteger = a;
        value.set(a,b.intValue());
        value.set(b,temp);
    }
}
