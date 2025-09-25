package org.example;

public class MergeSort {

    private static final int SMALL_ARRAY_THRESHOLD = 16;

    public static void sort(int[] array, Metrics metrics) {
        if (array == null || array.length < 2) {
            return;
        }
        int[] buffer = new int[array.length];
        metrics.allocations++;
        mergeSort(array, buffer, 0, array.length - 1, metrics);
    }

    private static void mergeSort(int[] array, int[] buffer, int leftIndex, int rightIndex, Metrics metrics) {
        DepthTracker.enter();
        try {
            int length = rightIndex - leftIndex + 1;
            if (length <= SMALL_ARRAY_THRESHOLD) {
                insertionSort(array, leftIndex, rightIndex, metrics);
                return;
            }

            if (leftIndex >= rightIndex) {
                return;
            }

            int middleIndex = (leftIndex + rightIndex) / 2;

            mergeSort(array, buffer, leftIndex, middleIndex, metrics);
            mergeSort(array, buffer, middleIndex + 1, rightIndex, metrics);

            metrics.comparisons++;
            if (array[middleIndex] <= array[middleIndex + 1]) {
                return;
            }

            merge(array, buffer, leftIndex, middleIndex, rightIndex, metrics);
        } finally {
            DepthTracker.exit();
        }
    }

    private static void merge(int[] array, int[] buffer, int leftIndex, int middleIndex, int rightIndex, Metrics metrics) {
        int i = leftIndex;
        int j = middleIndex + 1;
        int k = leftIndex;

        while (i <= middleIndex && j <= rightIndex) {
            metrics.comparisons++;
            if (array[i] <= array[j]) {
                buffer[k++] = array[i++];
            } else {
                buffer[k++] = array[j++];
            }
        }

        while (i <= middleIndex) {
            buffer[k++] = array[i++];
        }

        while (j <= rightIndex) {
            buffer[k++] = array[j++];
        }

        for (int t = leftIndex; t <= rightIndex; t++) {
            array[t] = buffer[t];
        }
    }

    private static void insertionSort(int[] array, int leftIndex, int rightIndex, Metrics metrics) {
        for (int i = leftIndex + 1; i <= rightIndex; i++) {
            int currentValue = array[i];
            int j = i - 1;
            while (j >= leftIndex) {
                metrics.comparisons++;
                if (array[j] > currentValue) {
                    array[j + 1] = array[j];
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = currentValue;
        }
    }
}