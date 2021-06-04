package com.homegrown.basic.algorithm.sort;

import java.util.Arrays;

/**
 * mergeSort(a,p,r) = merge(mergeSort(a,p,(r+p)/2)+mergeSort(a,(r+p)/2+1,r))
 * @author youyu
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] a = {9, 3, 1, 34, 5, 2, 8, 3};
        System.out.println(Arrays.toString(a));
        mergeSort(a, 0, a.length-1);
        System.out.println(Arrays.toString(a));
    }

    private static void mergeSort(int[] a,int p,int r){
        if (p>=r){
            return;
        }
        int i = (p+r)/2;
        mergeSort(a, p, i);
        mergeSort(a, i + 1, r);
        merge(a,p,i,r);
    }
    private static int[] merge(int[] a,int p,int i,int r){
        int ai=p,bi=i+1,ti=0;
        int[] temp = new int[r-p+1];

        while (ai<=i && bi<=r){
            if (a[ai]<=a[bi]){
                temp[ti++] = a[ai++];
            }else {
                temp[ti++] = a[bi++];
            }
        }

        while (ai<=i){
            temp[ti++] = a[ai++];
        }
        while (bi<=r){
            temp[ti++] = a[bi++];
        }

        int k = p;
        for (int value : temp) {
            a[k++] = value;
        }
        return temp;
    }
}
