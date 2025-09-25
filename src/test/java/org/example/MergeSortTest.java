package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void sortsSmallUnorderedArray() {
        int[] array = {5, 2, 9, 1, 7, 3, 8};
        int[] expected = array.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        MergeSort.sort(array, metrics);

        assertArrayEquals(expected, array, "Массив должен быть отсортирован по возрастанию");
        assertTrue(metrics.comparisons > 0, "Счётчик сравнений должен увеличиться");
    }

    @Test
    void handlesEmptyArray() {
        int[] array = {};
        Metrics metrics = new Metrics();
        DepthTracker.reset();

        MergeSort.sort(array, metrics);

        assertArrayEquals(new int[]{}, array, "Пустой массив должен остаться пустым");
        assertEquals(0, metrics.comparisons, "Для пустого массива сравнений быть не должно");
    }

    @Test
    void handlesSingleElement() {
        int[] array = {42};
        Metrics metrics = new Metrics();
        DepthTracker.reset();

        MergeSort.sort(array, metrics);

        assertArrayEquals(new int[]{42}, array, "Один элемент не меняется после сортировки");
        assertEquals(0, metrics.comparisons, "Для одного элемента сравнений быть не должно");
    }

    @Test
    void handlesAlreadySortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expected = array.clone();

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        MergeSort.sort(array, metrics);

        assertArrayEquals(expected, array, "Уже отсортированный массив должен остаться неизменным");
        assertTrue(metrics.comparisons > 0);
    }

    @Test
    void handlesArrayWithDuplicates() {
        int[] array = {5, 5, 3, 3, 1, 1};
        int[] expected = array.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        MergeSort.sort(array, metrics);

        assertArrayEquals(expected, array, "Массив с дубликатами должен сортироваться корректно");
        assertTrue(metrics.comparisons > 0);
    }

    @Test
    void sortsLargeRandomArray() {
        Random random = new Random(123);
        int[] array = random.ints(10_000, -1000, 1000).toArray();
        int[] expected = array.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        MergeSort.sort(array, metrics);

        assertArrayEquals(expected, array, "Большой массив должен быть отсортирован так же, как Arrays.sort");
        assertTrue(metrics.comparisons > 0, "На большом массиве должно быть много сравнений");
    }
}