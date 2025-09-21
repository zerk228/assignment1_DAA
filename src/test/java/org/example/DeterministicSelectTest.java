package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {

    @Test
    void findsMedianInOddSizedArray() {
        int[] array = {7, 2, 5, 9, 1};
        int[] sorted = array.clone();
        Arrays.sort(sorted);

        int medianIndex = sorted.length / 2;
        int value = DeterministicSelect.select(array.clone(), medianIndex);

        assertEquals(sorted[medianIndex], value,
                "Median должен совпадать с отсортированным массивом");
    }

    @Test
    void findsMinimumElement() {
        int[] array = {10, 20, 5, 30};
        int[] sorted = array.clone();
        Arrays.sort(sorted);

        int min = DeterministicSelect.select(array.clone(), 0);

        assertEquals(sorted[0], min, "Минимум должен совпадать");
    }

    @Test
    void findsMaximumElement() {
        int[] array = {10, 20, 5, 30};
        int[] sorted = array.clone();
        Arrays.sort(sorted);

        int max = DeterministicSelect.select(array.clone(), sorted.length - 1);

        assertEquals(sorted[sorted.length - 1], max, "Максимум должен совпадать");
    }

    @Test
    void matchesSortedArrayOnRandomTrials() {
        Random rnd = new Random(42);

        for (int t = 0; t < 20; t++) {
            int n = 50;
            int[] array = rnd.ints(n, -1000, 1000).toArray();
            int[] sorted = array.clone();
            Arrays.sort(sorted);

            int k = rnd.nextInt(n);
            int result = DeterministicSelect.select(array.clone(), k);

            assertEquals(sorted[k], result,"DeterministicSelect должен совпадать с Arrays.sort");
        }
    }

    @Test
    void throwsExceptionOnInvalidK() {
        int[] array = {1, 2, 3};
        assertThrows(IllegalArgumentException.class,
                () -> DeterministicSelect.select(array, -1));
        assertThrows(IllegalArgumentException.class,
                () -> DeterministicSelect.select(array, 3));
    }

    @Test
    void throwsExceptionOnEmptyArray() {
        int[] array = {};
        assertThrows(IllegalArgumentException.class,
                () -> DeterministicSelect.select(array, 0));
    }

    @Test
    void matchesSortedArrayOn100RandomTrials() {
        Random rnd = new Random(123);

        for (int t = 0; t < 100; t++) {
            int n = 200;
            int[] array = rnd.ints(n, -1000, 1000).toArray();
            int[] sorted = array.clone();
            Arrays.sort(sorted);

            int k = rnd.nextInt(n);
            int result = DeterministicSelect.select(array.clone(), k);

            assertEquals(sorted[k], result,
                    "DeterministicSelect должен совпадать с Arrays.sort");
        }
    }

}