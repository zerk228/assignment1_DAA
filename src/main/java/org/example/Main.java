package org.example;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int n = 10000;
        int[] baseArray = new Random(42).ints(n, -1000, 1000).toArray();

        try (CsvSink sink = new CsvSink(new File("results.csv"))) {

            //MergeSort
            Metrics m1 = new Metrics();
            DepthTracker.reset();
            int[] arr1 = baseArray.clone();

            long start1 = System.nanoTime();
            MergeSort.sort(arr1, m1);
            long end1 = System.nanoTime();

            m1.runtimeNanos = end1 - start1;

            sink.write("MergeSort", m1, DepthTracker.getMaxDepth());


            // QuickSort
            Metrics m2 = new Metrics();
            DepthTracker.reset();
            int[] arr2 = baseArray.clone();
            long start2 = System.nanoTime();
            QuickSort.sort(arr2, m2);
            long end2 = System.nanoTime();
            m2.runtimeNanos = end2 - start2;
            m2.allocations = 0; // inplace
            sink.write("QuickSort", m2, DepthTracker.getMaxDepth());


            // DeterministicSelect
            Metrics m3 = new Metrics();
            DepthTracker.reset();
            int[] arr3 = baseArray.clone();
            int k = arr3.length / 2;

            long start3 = System.nanoTime();
            int median = DeterministicSelect.select(arr3, k, m3);
            long end3 = System.nanoTime();

            m3.runtimeNanos = end3 - start3;
            m3.allocations = 0; // инplace
            sink.write("DeterministicSelect", m3, DepthTracker.getMaxDepth());


            // Closest Pair of Points
            Metrics m4 = new Metrics();
            DepthTracker.reset();
            List<ClosestPair2D.Point> points = new ArrayList<>();
            Random rnd = new Random(42);
            for (int i = 0; i < n; i++) {
                points.add(new ClosestPair2D.Point(rnd.nextDouble(), rnd.nextDouble()));
            }

            long start4 = System.nanoTime();
            ClosestPair2D.Pair closest = ClosestPair2D.findClosestPair(points, m4);
            long end4 = System.nanoTime();

            m4.runtimeNanos = end4 - start4;
            sink.write("ClosestPair2D", m4, DepthTracker.getMaxDepth());

            System.out.println("Results written to results.csv");
        }
    }
}