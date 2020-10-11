package com.demo.test.list.iterator;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author youyu
 */
public class ListTest {
    public static void main(String[] args) {
        ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3);
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            if (next == 2){
                System.out.println(next);
                break;
            }
        }
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        iterator.forEachRemaining(System.out::println);
    }
}
