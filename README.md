# assignment1_daa

## Architecture Notes

This project implements MergeSort, QuickSort, DeterministicSelect, and ClosestPair2D algorithms. Recursion depth is tracked via the `DepthTracker` class. Memory allocations are manually counted: MergeSort allocates new arrays, while QuickSort and DeterministicSelect are in-place (allocations = 0). All metrics (time, depth, allocations) are recorded using `CsvSink` for further analysis.

## Recurrence Analysis and Proofs

**MergeSort**

- Recurrence: T(n) = 2T(n/2) + Θ(n)
- By the Master Theorem (a=2, b=2, f(n)=Θ(n)), since f(n)=Θ(n^log_b a), T(n)=Θ(n log n).
- **Proof:** log_b a = log_2 2 = 1, f(n)=Θ(n), so T(n)=Θ(n log n).

**QuickSort**

- Recurrence (average case): T(n) = T(k) + T(n-k-1) + Θ(n), k random
- Expected: T(n) = 2T(n/2) + Θ(n) (average split)
- By Master Theorem: T(n)=Θ(n log n)
- **Proof:** For random pivots, expected splits are balanced, so recurrence matches MergeSort.

**DeterministicSelect (Median of Medians)**

- Recurrence: T(n) = T(n/5) + T(7n/10) + Θ(n)
- By Akra–Bazzi Theorem, T(n)=Θ(n)
- **Proof:** Akra–Bazzi applies for recurrences of the form T(n) = Σ a_i T(b_i n) + f(n). Here, a_1=1, b_1=1/5; a_2=1, b_2=7/10; f(n)=Θ(n). The solution is linear.

**ClosestPair2D**

- Recurrence: T(n) = 2T(n/2) + Θ(n log n)
- By Master Theorem (a=2, b=2, f(n)=Θ(n log n)), since f(n)=Θ(n log n) > Θ(n), T(n)=Θ(n log n)
- **Proof:** f(n) dominates, so T(n)=Θ(n log n) by case 2 of Master Theorem.

## Plots

- **Time vs n:** All algorithms have plots of runtime vs input size. MergeSort and QuickSort show n log n growth, DeterministicSelect is linear, ClosestPair2D is n log n.
- **Depth vs n:** Recursion depth grows logarithmically for MergeSort, QuickSort (average), DeterministicSelect, and ClosestPair2D.
- **Constant-factor effects:** Practical runtime is affected by caching, garbage collection (GC), and memory allocation. MergeSort is slower due to extra allocations; QuickSort is faster thanks to in-place operation and better cache usage.

## Summary

Theoretical time and depth estimates match experimental results. MergeSort and ClosestPair2D show expected Θ(n log n) growth, QuickSort is close to average case but can have outliers due to unlucky pivots. DeterministicSelect confirms linear complexity. Constant factors (cache, GC, allocations) affect absolute timings but not asymptotic behavior.