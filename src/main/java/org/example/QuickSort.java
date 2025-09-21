package org.example;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int leftIndex, int rightIndex) {
        while (leftIndex < rightIndex) {
            DepthTracker.enter();
            int pivotIndex = partition(array, leftIndex, rightIndex);
            DepthTracker.exit();
            int leftPartitionSize = pivotIndex - leftIndex;
            int rightPartitionSize = rightIndex - pivotIndex;

            if (leftPartitionSize < rightPartitionSize) {
                quickSort(array, leftIndex, pivotIndex - 1);
                leftIndex = pivotIndex + 1;
            } else {
                quickSort(array, pivotIndex + 1, rightIndex);
                rightIndex = pivotIndex - 1;
            }
        }
    }

    private static int partition(int[] array, int leftIndex, int rightIndex) {
        int pivotIndex = ThreadLocalRandom.current().nextInt(leftIndex, rightIndex + 1);
        swap(array, pivotIndex, rightIndex);

        int pivotValue = array[rightIndex];
        int i = leftIndex;

        for (int j = leftIndex; j < rightIndex; j++) {
            if (array[j] <= pivotValue) {
                swap(array, i, j);
                i++;
            }
        }

        swap(array, i, rightIndex);
        return i;
    }

    private static void swap(int[] array, int firstIndex, int secondIndex) {
        if (firstIndex == secondIndex) return;
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}