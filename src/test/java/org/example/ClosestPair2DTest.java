package org.example;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClosestPair2DTest {

    @Test
    void simpleTriangle() {
        List<ClosestPair2D.Point> points = List.of(
                new ClosestPair2D.Point(0, 0),
                new ClosestPair2D.Point(1, 0),
                new ClosestPair2D.Point(0, 1)
        );

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        ClosestPair2D.Pair result = ClosestPair2D.findClosestPair(points, metrics);

        assertEquals(1.0, result.distance(), 1e-9);
        assertTrue(metrics.comparisons > 0, "Должны быть выполнены сравнения");
    }

    @Test
    void bruteForceValidationSmallSet() {
        Random random = new Random(42);
        List<ClosestPair2D.Point> points = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            points.add(new ClosestPair2D.Point(random.nextDouble(), random.nextDouble()));
        }

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        ClosestPair2D.Pair fast = ClosestPair2D.findClosestPair(points, metrics);

        double bruteForce = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double dx = points.get(i).x() - points.get(j).x();
                double dy = points.get(i).y() - points.get(j).y();
                bruteForce = Math.min(bruteForce, Math.hypot(dx, dy));
            }
        }

        assertEquals(bruteForce, fast.distance(), 1e-9);
        assertTrue(metrics.comparisons > 0, "Метрика сравнений должна увеличиваться");
    }

    @Test
    void handlesTwoPoints() {
        List<ClosestPair2D.Point> points = List.of(
                new ClosestPair2D.Point(3, 4),
                new ClosestPair2D.Point(6, 8)
        );

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        ClosestPair2D.Pair result = ClosestPair2D.findClosestPair(points, metrics);

        assertEquals(5.0, result.distance(), 1e-9);
        assertTrue(metrics.comparisons > 0);
    }

    @Test
    void throwsOnInvalidInput() {
        Metrics metrics = new Metrics();
        assertThrows(IllegalArgumentException.class, () ->
                ClosestPair2D.findClosestPair(Collections.emptyList(), metrics));
        assertThrows(IllegalArgumentException.class, () ->
                ClosestPair2D.findClosestPair(List.of(new ClosestPair2D.Point(0, 0)), metrics));
    }

    @Test
    void bruteForceComparisonAt2000Points() {
        Random rnd = new Random(123);
        List<ClosestPair2D.Point> points = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            points.add(new ClosestPair2D.Point(rnd.nextDouble(), rnd.nextDouble()));
        }

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        ClosestPair2D.Pair fast = ClosestPair2D.findClosestPair(points, metrics);

        double bruteForce = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double dx = points.get(i).x() - points.get(j).x();
                double dy = points.get(i).y() - points.get(j).y();
                bruteForce = Math.min(bruteForce, Math.hypot(dx, dy));
            }
        }

        assertEquals(bruteForce, fast.distance(), 1e-9);
        assertTrue(metrics.comparisons > 0);
    }

    @Test
    void handlesLargeInputWithoutBruteForce() {
        Random rnd = new Random(42);
        List<ClosestPair2D.Point> points = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            points.add(new ClosestPair2D.Point(rnd.nextDouble(), rnd.nextDouble()));
        }

        Metrics metrics = new Metrics();
        DepthTracker.reset();

        ClosestPair2D.Pair fast = ClosestPair2D.findClosestPair(points, metrics);

        assertNotNull(fast, "Алгоритм должен вернуть пару даже на 100k точках");
        assertTrue(fast.distance() >= 0, "Расстояние не может быть отрицательным");
        assertTrue(metrics.comparisons > 0, "При 100k точках должны быть сравнения");
    }
}