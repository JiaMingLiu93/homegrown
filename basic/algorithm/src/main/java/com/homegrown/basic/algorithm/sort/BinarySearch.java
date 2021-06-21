package com.homegrown.basic.algorithm.sort;

import java.util.Arrays;

/**
 * @author youyu
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] a = {9, 3, 1, 34, 5, 2, 8, 3};
        QuickSort.quickSort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));
        System.out.println(binarySearch(a,8));
    }

    public static int binarySearch(int[] a,int target){
        return binarySearch(a,0,a.length-1,target);
    }

    private static int binarySearch(int[] a,int left,int right,int target){
        if (left==right){
            if ( a[left]==target){
                return left;
            }else {
                return -1;
            }
        }
        if (left>right){
            return -1;
        }
        int mid = (left+right)/2;
        int midValue = a[mid];
        if (target<midValue){
            return binarySearch(a,left,mid-1,target);
        }else if (target==midValue){
            return mid;
        }{
            return binarySearch(a,mid+1,right,target);
        }
    }
}
