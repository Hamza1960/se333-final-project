package com.se333.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ArrayUtils class.
 */
class ArrayUtilsTest {

    private ArrayUtils utils;

    @BeforeEach
    void setUp() {
        utils = new ArrayUtils();
    }

    // ── findMax ────────────────────────────────────

    @Test
    @DisplayName("findMax: normal array")
    void testFindMax() {
        assertEquals(9, utils.findMax(new int[]{3, 1, 9, 4, 7}));
    }

    @Test
    @DisplayName("findMax: single element")
    void testFindMaxSingle() {
        assertEquals(5, utils.findMax(new int[]{5}));
    }

    @Test
    @DisplayName("findMax: all same values")
    void testFindMaxAllSame() {
        assertEquals(3, utils.findMax(new int[]{3, 3, 3}));
    }

    @Test
    @DisplayName("findMax: negative values")
    void testFindMaxNegative() {
        assertEquals(-1, utils.findMax(new int[]{-5, -1, -3}));
    }

    @Test
    @DisplayName("findMax: null throws exception")
    void testFindMaxNull() {
        assertThrows(IllegalArgumentException.class, () -> utils.findMax(null));
    }

    @Test
    @DisplayName("findMax: empty throws exception")
    void testFindMaxEmpty() {
        assertThrows(IllegalArgumentException.class, () -> utils.findMax(new int[]{}));
    }

    // ── findMin ────────────────────────────────────

    @Test
    @DisplayName("findMin: normal array")
    void testFindMin() {
        assertEquals(1, utils.findMin(new int[]{3, 1, 9, 4, 7}));
    }

    @Test
    @DisplayName("findMin: single element")
    void testFindMinSingle() {
        assertEquals(5, utils.findMin(new int[]{5}));
    }

    @Test
    @DisplayName("findMin: null throws exception")
    void testFindMinNull() {
        assertThrows(IllegalArgumentException.class, () -> utils.findMin(null));
    }

    @Test
    @DisplayName("findMin: empty throws exception")
    void testFindMinEmpty() {
        assertThrows(IllegalArgumentException.class, () -> utils.findMin(new int[]{}));
    }

    // ── average ────────────────────────────────────

    @Test
    @DisplayName("average: normal array")
    void testAverage() {
        assertEquals(3.0, utils.average(new int[]{1, 2, 3, 4, 5}), 0.001);
    }

    @Test
    @DisplayName("average: single element")
    void testAverageSingle() {
        assertEquals(10.0, utils.average(new int[]{10}), 0.001);
    }

    @Test
    @DisplayName("average: null throws exception")
    void testAverageNull() {
        assertThrows(IllegalArgumentException.class, () -> utils.average(null));
    }

    @Test
    @DisplayName("average: empty throws exception")
    void testAverageEmpty() {
        assertThrows(IllegalArgumentException.class, () -> utils.average(new int[]{}));
    }

    // ── sortAscending ──────────────────────────────

    @Test
    @DisplayName("sortAscending: unsorted array")
    void testSortAscending() {
        assertArrayEquals(new int[]{1, 3, 5, 7, 9}, utils.sortAscending(new int[]{9, 3, 7, 1, 5}));
    }

    @Test
    @DisplayName("sortAscending: already sorted")
    void testSortAscendingAlreadySorted() {
        assertArrayEquals(new int[]{1, 2, 3}, utils.sortAscending(new int[]{1, 2, 3}));
    }

    @Test
    @DisplayName("sortAscending: null returns null")
    void testSortAscendingNull() {
        assertNull(utils.sortAscending(null));
    }

    @Test
    @DisplayName("sortAscending: does not modify original")
    void testSortAscendingDoesNotModifyOriginal() {
        int[] original = {5, 3, 1};
        utils.sortAscending(original);
        assertArrayEquals(new int[]{5, 3, 1}, original);
    }

    // ── contains ───────────────────────────────────

    @Test
    @DisplayName("contains: element exists")
    void testContainsTrue() {
        assertTrue(utils.contains(new int[]{1, 2, 3, 4, 5}, 3));
    }

    @Test
    @DisplayName("contains: element does not exist")
    void testContainsFalse() {
        assertFalse(utils.contains(new int[]{1, 2, 3}, 99));
    }

    @Test
    @DisplayName("contains: null array returns false")
    void testContainsNull() {
        assertFalse(utils.contains(null, 5));
    }

    // ── removeDuplicates ───────────────────────────

    @Test
    @DisplayName("removeDuplicates: array with duplicates")
    void testRemoveDuplicates() {
        int[] result = utils.removeDuplicates(new int[]{1, 2, 2, 3, 3, 3});
        assertEquals(3, result.length);
    }

    @Test
    @DisplayName("removeDuplicates: no duplicates")
    void testRemoveDuplicatesNoDuplicates() {
        int[] result = utils.removeDuplicates(new int[]{1, 2, 3});
        assertEquals(3, result.length);
    }

    @Test
    @DisplayName("removeDuplicates: null returns null")
    void testRemoveDuplicatesNull() {
        assertNull(utils.removeDuplicates(null));
    }

    @Test
    @DisplayName("removeDuplicates: empty array")
    void testRemoveDuplicatesEmpty() {
        int[] result = utils.removeDuplicates(new int[]{});
        assertEquals(0, result.length);
    }

    // ── binarySearch ───────────────────────────────

    @Test
    @DisplayName("binarySearch: element found")
    void testBinarySearchFound() {
        assertEquals(2, utils.binarySearch(new int[]{1, 3, 5, 7, 9}, 5));
    }

    @Test
    @DisplayName("binarySearch: element not found")
    void testBinarySearchNotFound() {
        assertEquals(-1, utils.binarySearch(new int[]{1, 3, 5, 7, 9}, 4));
    }

    @Test
    @DisplayName("binarySearch: null array")
    void testBinarySearchNull() {
        assertEquals(-1, utils.binarySearch(null, 5));
    }

    @Test
    @DisplayName("binarySearch: empty array")
    void testBinarySearchEmpty() {
        assertEquals(-1, utils.binarySearch(new int[]{}, 5));
    }

    @Test
    @DisplayName("binarySearch: first element")
    void testBinarySearchFirst() {
        assertEquals(0, utils.binarySearch(new int[]{1, 3, 5, 7, 9}, 1));
    }

    @Test
    @DisplayName("binarySearch: last element")
    void testBinarySearchLast() {
        assertEquals(4, utils.binarySearch(new int[]{1, 3, 5, 7, 9}, 9));
    }

    // ── reverseArray ───────────────────────────────

    @Test
    @DisplayName("reverseArray: normal array")
    void testReverseArray() {
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, utils.reverseArray(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    @DisplayName("reverseArray: single element")
    void testReverseArraySingle() {
        assertArrayEquals(new int[]{1}, utils.reverseArray(new int[]{1}));
    }

    @Test
    @DisplayName("reverseArray: null returns null")
    void testReverseArrayNull() {
        assertNull(utils.reverseArray(null));
    }

    @Test
    @DisplayName("reverseArray: two elements")
    void testReverseArrayTwo() {
        assertArrayEquals(new int[]{2, 1}, utils.reverseArray(new int[]{1, 2}));
    }
}
