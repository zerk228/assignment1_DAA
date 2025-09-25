package org.example;

import java.util.*;

public class ClosestPair2D {

    public record Point(double x, double y) {}
    public record Pair(Point first, Point second, double distance) {}

    public static Pair findClosestPair(List<Point> points, Metrics metrics) {
        if (points == null || points.size() < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }

        List<Point> pointsSortedByX = new ArrayList<>(points);
        pointsSortedByX.sort(Comparator.comparingDouble(Point::x));

        List<Point> pointsSortedByY = new ArrayList<>(points);
        pointsSortedByY.sort(Comparator.comparingDouble(Point::y));

        return recursiveClosestPair(pointsSortedByX, pointsSortedByY, 0, points.size() - 1, metrics);
    }

    private static Pair recursiveClosestPair(List<Point> pointsSortedByX,
                                             List<Point> pointsSortedByY,
                                             int leftIndex,
                                             int rightIndex,
                                             Metrics metrics) {
        DepthTracker.enter();
        try{
            int numberOfPoints = rightIndex - leftIndex + 1;

            if (numberOfPoints <= 3) {
                return bruteForce(pointsSortedByX, leftIndex, rightIndex, metrics);
            }

            int middleIndex = (leftIndex + rightIndex) / 2;
            double middleX = pointsSortedByX.get(middleIndex).x();

            List<Point> leftY = new ArrayList<>();
            metrics.allocations++;
            List<Point> rightY = new ArrayList<>();
            metrics.allocations++;
            for (Point point : pointsSortedByY) {
                if (point.x() <= middleX) {
                    leftY.add(point);
                } else {
                    rightY.add(point);
                }
            }

            Pair leftPair = recursiveClosestPair(pointsSortedByX, leftY, leftIndex, middleIndex, metrics);
            Pair rightPair = recursiveClosestPair(pointsSortedByX, rightY, middleIndex + 1, rightIndex, metrics);

            Pair bestPair = leftPair.distance() < rightPair.distance() ? leftPair : rightPair;
            double bestDistance = bestPair.distance();

            List<Point> strip = new ArrayList<>();
            metrics.allocations++;
            for (Point point : pointsSortedByY) {
                if (Math.abs(point.x() - middleX) < bestDistance) {
                    strip.add(point);
                }
            }

            for (int i = 0; i < strip.size(); i++) {
                for (int j = i + 1; j < strip.size() && (strip.get(j).y() - strip.get(i).y()) < bestDistance; j++) {
                    metrics.comparisons++;
                    double currentDistance = euclideanDistance(strip.get(i), strip.get(j));
                    if (currentDistance < bestDistance) {
                        bestDistance = currentDistance;
                        bestPair = new Pair(strip.get(i), strip.get(j), currentDistance);
                    }
                }
            }

            return bestPair;

        }finally{
            DepthTracker.exit();
        }

    }

    private static Pair bruteForce(List<Point> points, int leftIndex, int rightIndex, Metrics metrics) {
        Pair bestPair = new Pair(null, null, Double.POSITIVE_INFINITY);
        for (int i = leftIndex; i <= rightIndex; i++) {
            for (int j = i + 1; j <= rightIndex; j++) {
                metrics.comparisons++;
                double currentDistance = euclideanDistance(points.get(i), points.get(j));
                if (currentDistance < bestPair.distance()) {
                    bestPair = new Pair(points.get(i), points.get(j), currentDistance);
                }
            }
        }
        return bestPair;
    }

    private static double euclideanDistance(Point a, Point b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.hypot(dx, dy);
    }
}