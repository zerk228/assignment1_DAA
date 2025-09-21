package org.example;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClosestPair2DTest{

    @Test
    void simpleTriangle() {
        List<ClosestPair2D.Point> points = List.of(
                new ClosestPair2D.Point(0, 0),
                new ClosestPair2D.Point(1, 0),
                new ClosestPair2D.Point(0, 1)
        );

        ClosestPair2D.Pair result = ClosestPair2D.findClosestPair(points);
        assertEquals(1.0, result.distance(), 1e-9);
    }

    @Test
    void bruteForceValidationSmallSet() {
        Random random = new Random(42);
        List<ClosestPair2D.Point> points = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            points.add(new ClosestPair2D.Point(random.nextDouble(), random.nextDouble()));
        }

        ClosestPair2D.Pair fast = ClosestPair2D.findClosestPair(points);

        double bruteForce = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double dx = points.get(i).x() - points.get(j).x();
                double dy = points.get(i).y() - points.get(j).y();
                bruteForce = Math.min(bruteForce, Math.hypot(dx, dy));
            }
        }

        assertEquals(bruteForce, fast.distance(), 1e-9);
    }

    @Test
    void handlesTwoPoints() {
        List<ClosestPair2D.Point> points = List.of(
                new ClosestPair2D.Point(3, 4),
                new ClosestPair2D.Point(6, 8)
        );

        ClosestPair2D.Pair result = ClosestPair2D.findClosestPair(points);
        assertEquals(5.0, result.distance(), 1e-9);
    }

    @Test
    void throwsOnInvalidInput() {
        assertThrows(IllegalArgumentException.class, () ->
                ClosestPair2D.findClosestPair(Collections.emptyList()));
        assertThrows(IllegalArgumentException.class, () ->
                ClosestPair2D.findClosestPair(List.of(new ClosestPair2D.Point(0, 0))));
    }

    @Test
    void bruteForceComparisonAt2000Points() {
        Random rnd = new Random(123);
        List<ClosestPair2D.Point> points = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            points.add(new ClosestPair2D.Point(rnd.nextDouble(), rnd.nextDouble()));
        }

        ClosestPair2D.Pair fast = ClosestPair2D.findClosestPair(points);

        double bruteForce = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double dx = points.get(i).x() - points.get(j).x();
                double dy = points.get(i).y() - points.get(j).y();
                bruteForce = Math.min(bruteForce, Math.hypot(dx, dy));
            }
        }

        assertEquals(bruteForce, fast.distance(), 1e-9);
    }

    @Test
    void handlesLargeInputWithoutBruteForce() {
        Random rnd = new Random(42);
        List<ClosestPair2D.Point> points = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            points.add(new ClosestPair2D.Point(rnd.nextDouble(), rnd.nextDouble()));
        }

        ClosestPair2D.Pair fast = ClosestPair2D.findClosestPair(points);

        assertNotNull(fast, "Алгоритм должен вернуть пару даже на 100k точках");
        assertTrue(fast.distance() >= 0, "Расстояние не может быть отрицательным");
    }

}