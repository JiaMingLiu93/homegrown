package com.homegrown.basic.algorithm.leetcode.easy;

import java.util.Arrays;

/**
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
 *
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 *
 * 示例 1：
 *
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 * 示例 2：
 *
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4]
 * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
 *  
 *
 * 提示：
 *
 * 0 <= nums.length <= 3 * 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 已按升序排列
 */
public class RemoveDuplicates {
    public static void main(String[] args) {
//        int[] nums = new int[]{0,0,1,1,1,2,2,3,3,4};
//        int[] nums = new int[]{-1,0,0,0,0,3,3};
//        int[] nums = new int[]{-3,-1,0,0};
        int[] nums = new int[]{-3,-3,-2,-1,-1,0,0,0,0,0};
        RemoveDuplicates removeDuplicates = new RemoveDuplicates();
        int i = removeDuplicates.removeDuplicates2(nums);
        System.out.println(i);
        System.out.println(Arrays.toString(nums));

        int[] nums1 = new int[]{-3,-3,-2,-1,-1,0,0,0,0,0};
        int j = removeDuplicates.removeDuplicateTest3(nums1,2);
        System.out.println(j);
        System.out.println(Arrays.toString(nums1));
    }
    public int removeDuplicates(int[] nums) {
        if (nums.length==0){
            return 0;
        }

        int temp = nums[0];
        int zeroStartPosition = 0;
        for (int i=1;i<nums.length;i++){
            if (temp == nums[i]){
                nums[i] = 0;
                continue;
            }
            temp = nums[i];
            if (temp == 0){
                zeroStartPosition = i;
            }
        }
        int k = 1;
        for (int j=1;j<nums.length;j++){
            if (j == zeroStartPosition){
                nums[k] = nums[j];
                k++;
                continue;
            }
            if (nums[j] == 0){
                continue;
            }
            nums[k] = nums[j];
            k++;
        }
        return k;
    }

    public int removeDuplicates2(int[] nums) {
        int n = nums.length;
        if (n == 0){
            return 0;
        }
        int slow=1,fast=1;
        while (fast<n){
            if (nums[fast] != nums[fast-1]){
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    public int removeDuplicateTest3(int[] nums, int k){
        int n = nums.length;
        if (n == 0){
            return 0;
        }
        int slow=1,fast=1;
        while (fast<n){
            if (fast<k || nums[fast] != nums[fast-k]){
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }
}
