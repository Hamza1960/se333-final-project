package com.se333.demo;

import java.util.Arrays;

/**
 * Utility class for array manipulation and analysis.
 */
public class ArrayUtils {

    /**
     * Find the maximum value in an integer array.
     *
     * @throws IllegalArgumentException if the array is null or empty
     */
    public int findMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    /**
     * Find the minimum value in an integer array.
     *
     * @throws IllegalArgumentException if the array is null or empty
     */
    public int findMin(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    /**
     * Calculate the average of an integer array.
     *
     * @throws IllegalArgumentException if the array is null or empty
     */
    public double average(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        long sum = 0;
        for (int val : arr) {
            sum += val;
        }
        return (double) sum / arr.length;
    }

    /**
     * Sort the array in ascending order and return a new sorted array.
     */
    public int[] sortAscending(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);
        return sorted;
    }

    /**
     * Check if an array contains a target value.
     */
    public boolean contains(int[] arr, int target) {
        if (arr == null) {
            return false;
        }
        for (int val : arr) {
            if (val == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove duplicate values and return a new array.
     */
    public int[] removeDuplicates(int[] arr) {
        if (arr == null) {
            return null;
        }
        if (arr.length == 0) {
            return new int[0];
        }
        return Arrays.stream(arr).distinct().toArray();
    }

    /**
     * Perform binary search on a sorted array.
     * Returns the index of target, or -1 if not found.
     */
    public int binarySearch(int[] sortedArr, int target) {
        if (sortedArr == null || sortedArr.length == 0) {
            return -1;
        }
        int low = 0;
        int high = sortedArr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sortedArr[mid] == target) {
                return mid;
            } else if (sortedArr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Reverse an array in-place and return it.
     */
    public int[] reverseArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
        return arr;
    }
}
