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

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        QuickSort.sort(array, metrics);

        assertArrayEquals(expected, array, "Массив должен быть отсортирован по возрастанию");
        assertTrue(metrics.comparisons > 0);
    }

    @Test
    void handlesAlreadySortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = array.clone();

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        QuickSort.sort(array, metrics);

        assertArrayEquals(expected, array, "Уже отсортированный массив не должен измениться");
        assertTrue(metrics.comparisons > 0);
    }

    @Test
    void handlesArrayWithDuplicates() {
        int[] array = {5, 5, 5, 5};
        int[] expected = array.clone();

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        QuickSort.sort(array, metrics);

        assertArrayEquals(expected, array, "Массив из одинаковых элементов должен оставаться неизменным");
        assertTrue(metrics.comparisons > 0);
    }

    @Test
    void handlesEmptyArray() {
        int[] array = {};
        Metrics metrics = new Metrics();
        DepthTracker.reset();

        QuickSort.sort(array, metrics);

        assertArrayEquals(new int[]{}, array, "Пустой массив должен остаться пустым");
        assertEquals(0, metrics.comparisons, "Для пустого массива сравнений быть не должно");
    }

    @Test
    void handlesSingleElement() {
        int[] array = {42};
        Metrics metrics = new Metrics();
        DepthTracker.reset();

        QuickSort.sort(array, metrics);

        assertArrayEquals(new int[]{42}, array, "Один элемент не должен измениться");
        assertEquals(0, metrics.comparisons, "Для одного элемента сравнений быть не должно");
    }

    @Test
    void sortsLargeRandomArray() {
        Random random = new Random(42);
        int[] array = random.ints(10_000, -1000, 1000).toArray();
        int[] expected = array.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        QuickSort.sort(array, metrics);

        assertArrayEquals(expected, array, "Большой массив должен сортироваться корректно");
        assertTrue(metrics.comparisons > 0);
    }

    @Test
    void recursionDepthIsLogN() {
        int n = 1 << 15; // 32768 элементов
        int[] array = new Random(42).ints(n, -1000, 1000).toArray();
        int[] expected = array.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        QuickSort.sort(array, metrics);
        int maxDepth = DepthTracker.getMaxDepth();

        assertArrayEquals(expected, array, "QuickSort должен сортировать корректно");

        int bound = 2 * (31 - Integer.numberOfLeadingZeros(n));
        assertTrue(maxDepth <= bound + 10,
                "Глубина рекурсии должна быть O(log n), но была " + maxDepth);

        assertTrue(metrics.comparisons > 0, "На 32k элементов должно быть много сравнений");
    }
}