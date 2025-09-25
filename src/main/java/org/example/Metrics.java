package org.example;

public class Metrics {
    public long comparisons = 0;
    public long allocations = 0;
    public long runtimeNanos = 0;

    public void reset() {
        comparisons = 0;
        allocations = 0;
        runtimeNanos = 0;
    }
}