package com.homegrown.basic.algorithm.sort;

import java.util.stream.Stream;

/**
 * @author youyu
 * @date 2020/1/21 10:54 PM
 */
public class BubbleSort {
    public static void main(String[] args) {
        Integer []a = of(3,2,1,5,4);
        for (int i=0;i<a.length;i++){
            for (int j=0;j<a.length-1-i;j++){
                if (a[j].compareTo(a[j+1]) > 0){
                    Integer t = a[j];
                    a[j] = a[j+1];
                    a[j+1] = t;
                }
            }
        }

        Stream.of(a).forEach(System.out::print);
    }

    public static Integer[] of(Integer... a){
     return a;
    }
}
