package com.demo.test.list.removeAll;

import java.util.ArrayList;

/**
 * @author jam
 * @description
 * @create 2018-11-07
 **/
public class ListTest {
    public static void main(String[] args) {
        ArrayList<Long> list1 = new ArrayList<>();
        list1.add(1L);
        list1.add(2L);
        list1.add(3L);
        list1.add(4L);

        ArrayList<Long> list2 = new ArrayList<>();
        list2.add(5L);
        list2.add(1L);
        ArrayList<Long> temp = new ArrayList<>();
        temp.addAll(list1);
        temp.removeAll(list2);
        temp.forEach(System.out::println);

    }
}
