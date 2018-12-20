package com.demo.test.bio;

import java.util.Scanner;

/**
 * @author jam
 * @description
 * @create 2018-12-04
 **/
public class ScannerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            System.out.println(scanner.nextLine());
        }
    }
}
