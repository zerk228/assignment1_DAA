package org.example;

import java.util.Arrays;

public class DeterministicSelect {

    public static int select(int[] array, int orderStatisticIndex) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        if (orderStatisticIndex < 0 || orderStatisticIndex >= array.length) {
            throw new IllegalArgumentException("Invalid k index: " + orderStatisticIndex);
        }

        return select(array, 0, array.length - 1, orderStatisticIndex);
    }

    private static int select(int[] array, int leftIndex, int rightIndex, int k) {
        while (true) {
            if (leftIndex == rightIndex) {
                return array[leftIndex];
            }

            int pivotValue = medianOfMedians(array, leftIndex, rightIndex);
            int pivotIndex = partition(array, leftIndex, rightIndex, pivotValue);

            int rank = pivotIndex - leftIndex;

            if (k == rank) {
                return array[pivotIndex];
            } else if (k < rank) {
                rightIndex = pivotIndex - 1;
            } else {
                k = k - (rank + 1);
                leftIndex = pivotIndex + 1;
            }
        }
    }

    private static int medianOfMedians(int[] array, int leftIndex, int rightIndex) {
        int n = rightIndex - leftIndex + 1;

        if (n <= 5) {
            Arrays.sort(array, leftIndex, rightIndex + 1);
            return array[leftIndex + n / 2];
        }

        int numGroups = 0;
        for (int i = leftIndex; i <= rightIndex; i += 5) {
            int groupEnd = Math.min(i + 4, rightIndex);
            Arrays.sort(array, i, groupEnd + 1);
            int medianIndex = i + (groupEnd - i) / 2;
            swap(array, leftIndex + numGroups, medianIndex);
            numGroups++;
        }

        return medianOfMedians(array, leftIndex, leftIndex + numGroups - 1);
    }

    private static int partition(int[] array, int leftIndex, int rightIndex, int pivotValue) {
        int pivotIndex = leftIndex;
        for (int i = leftIndex; i <= rightIndex; i++) {
            if (array[i] == pivotValue) {
                pivotIndex = i;
                break;
            }
        }

        swap(array, pivotIndex, rightIndex);

        int storeIndex = leftIndex;
        for (int i = leftIndex; i < rightIndex; i++) {
            if (array[i] < pivotValue) {
                swap(array, storeIndex, i);
                storeIndex++;
            }
        }

        swap(array, storeIndex, rightIndex);
        return storeIndex;
    }

    private static void swap(int[] array, int firstIndex, int secondIndex) {
        if (firstIndex == secondIndex) return;
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}