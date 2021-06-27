package com.homegrown.basic.algorithm.leetcode.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author youyu
 */
public class TwoSums {
    public static void main(String[] args) {
        TwoSums twoSums = new TwoSums();
        int[] a = {3,2,4};
        System.out.println(Arrays.toString(twoSums.second(a,6)));
    }

    private int[] first(int[] nums, int target){
        int first=0,second=0;
        for (int i=0;i< nums.length;i++){
            int left = nums[i];
            for (int j=i+1;j< nums.length;j++){
                int right = nums[j];
                int total = left + right;
                if (total==target){
                    first=i;
                    second=j;
                }
            }
        }
        return new int[]{first,second};
    }

    private int[] second(int[] nums, int target){
        Map<Integer, Integer> cache = new HashMap<>(nums.length);

        for (int i=0;i< nums.length;i++){
           cache.put(nums[i],i);
        }
        for (int i=0;i<nums.length;i++){
            int other = target - nums[i];
            Integer otherPosition = cache.get(other);
            if (otherPosition != null && i != otherPosition){
                return new int[]{i,otherPosition};
            }
        }
        return null;
    }
}
