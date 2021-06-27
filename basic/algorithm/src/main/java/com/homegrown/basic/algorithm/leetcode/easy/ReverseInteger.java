package com.homegrown.basic.algorithm.leetcode.easy;

//给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
//
// 如果反转后整数超过 32 位的有符号整数的范围 [−231, 231 − 1] ，就返回 0。
//假设环境不允许存储 64 位整数（有符号或无符号）。
//
//
//
// 示例 1：
//
//
//输入：x = 123
//输出：321
//
//
// 示例 2：
//
//
//输入：x = -123
//输出：-321
//
//
// 示例 3：
//
//
//输入：x = 120
//输出：21
//
//
// 示例 4：
//
//
//输入：x = 0
//输出：0
//
//
//
//
// 提示：
//
//
// -231 <= x <= 231 - 1
//
// Related Topics 数学

import javax.jws.WebParam;
import java.util.Arrays;

/**
 * @author youyu
 */
public class ReverseInteger {
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);

        Integer a = 120;
        System.out.println(reverse2(a));
    }

    private static int reverse2(int a){
        int rev=0,digit;
        while (a!=0){
            //Integer.MIN_VALUE<= rev*10+digit <=Integer.MAX_VALUE
            if (rev<=(Integer.MAX_VALUE/10) && rev>=(Integer.MIN_VALUE/10)){
                //推出末尾数字
                digit = a%10;
                //加入末尾数字
                rev = rev*10+digit;
                //缩短整数
                a = a/10;
            }else {
                return 0;
            }
        }
        return rev;
    }

    private static int reverse(int a){
        if (a==0){
            return 0;
        }
        if (a == -2147483648){
            return 0;
        }
        int b = a;
        if (a<0){
            b = -a;
        }

        StringBuilder sb = new StringBuilder();;
        boolean firstZero = false;
        int j=0;
        while (b!=0){
            int i = b % 10;
            b = b/10;
            j = j+1;

            if (j==1 && i==0){
                firstZero = true;
                continue;
            }

            if (i==0 && firstZero){
                continue;
            }

            sb.append(i);
            firstZero = false;
        }

        String s = sb.toString();

        if (s.length()>10){
            return 0;
        }
        if (s.length() == 10){
            if (a<0 && s.compareTo("2147483648")>0){
                return 0;
            }
            if (a>0 && s.compareTo("2147483647")>0){
                return 0;
            }
        }
        int reversed = Integer.parseInt(s);
        if (a<0){
            return -reversed;
        }else{
            return reversed;
        }
    }
}
