package com.homegrown.basic.algorithm.sort;

import java.util.Arrays;

/**
 * quickSort(a,p,r) = quickSort(a,p,q-1)+quickSort(a,q+1,q)
 * p>=r
 * @author youyu
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] a = {9, 3, 1, 34, 5, 2, 8, 3};
        System.out.println(Arrays.toString(a));
        quickSort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] a,int p,int r){
        if (p>=r){
            return;
        }
        int q = partition(a, p, r);
        quickSort(a,p,q-1);
        quickSort(a,q+1,r);
    }

    private static int partition(int[] a,int p,int r){
        int pivot = a[r];
        int j = p;
        for (int i=p;i<r;i++){
            if (a[i]<pivot){
               swap(a,i,j);
               j++;
            }
        }
        swap(a,j,r);
        return j;
    }

    private static void swap(int[] a,int i,int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
