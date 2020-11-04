package com.demo.jdk7.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author youyu
 * @date 2020/4/23 6:10 PM
 */
public class Test {
    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("A");
        stringList.addAll(Arrays.asList());
    }
}
