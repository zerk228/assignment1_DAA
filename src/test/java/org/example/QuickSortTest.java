package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void sortsSmallUnorderedArray() {
        int[] array = {9, 4, 7, 1, 3, 6};
        int[] expected = array.clone();
        Arrays.sort(expected);

        QuickSort.sort(array);

        assertArrayEquals(expected, array, "Массив должен быть отсортирован по возрастанию");
    }

    @Test
    void handlesAlreadySortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = array.clone();

        QuickSort.sort(array);

        assertArrayEquals(expected, array, "Уже отсортированный массив не должен измениться");
    }

    @Test
    void handlesArrayWithDuplicates() {
        int[] array = {5, 5, 5, 5};
        int[] expected = array.clone();

        QuickSort.sort(array);

        assertArrayEquals(expected, array, "Массив из одинаковых элементов должен оставаться неизменным");
    }

    @Test
    void handlesEmptyArray() {
        int[] array = {};
        QuickSort.sort(array);

        assertArrayEquals(new int[]{}, array, "Пустой массив должен остаться пустым");
    }

    @Test
    void handlesSingleElement() {
        int[] array = {42};
        QuickSort.sort(array);

        assertArrayEquals(new int[]{42}, array, "Один элемент не должен измениться");
    }

    @Test
    void sortsLargeRandomArray() {
        Random random = new Random(42);
        int[] array = random.ints(10_000, -1000, 1000).toArray();
        int[] expected = array.clone();
        Arrays.sort(expected);

        QuickSort.sort(array);

        assertArrayEquals(expected, array, "Большой массив должен сортироваться корректно");
    }

    @Test
    void recursionDepthIsLogN() {
        int n = 1 << 15; // 32768 элементов
        int[] array = new Random(42).ints(n, -1000, 1000).toArray();
        int[] expected = array.clone();
        Arrays.sort(expected);

        DepthTracker.reset();
        QuickSort.sort(array);
        int maxDepth = DepthTracker.getMaxDepth();

        assertArrayEquals(expected, array, "QuickSort должен сортировать корректно");

        int bound = 2 * (31 - Integer.numberOfLeadingZeros(n));
        assertTrue(maxDepth <= bound + 10,
                "Глубина рекурсии должна быть O(log n), но была " + maxDepth);
    }

}