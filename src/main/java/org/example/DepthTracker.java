package org.example;

public class DepthTracker {
    private static final ThreadLocal<Integer> currentDepth = ThreadLocal.withInitial(() -> 0);
    private static final ThreadLocal<Integer> maxDepth = ThreadLocal.withInitial(() -> 0);

    public static void enter() {
        int depth = currentDepth.get() + 1;
        currentDepth.set(depth);
        if (depth > maxDepth.get()) {
            maxDepth.set(depth);
        }
    }

    public static void exit() {
        currentDepth.set(currentDepth.get() - 1);
    }

    public static int getMaxDepth() {
        return maxDepth.get();
    }

    public static void reset() {
        currentDepth.set(0);
        maxDepth.set(0);
    }
}