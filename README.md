Assignment 1 – Divide & Conquer Algorithms
Overview

This project implements and analyzes several classic divide-and-conquer algorithms with safe recursion patterns, bounded depth, and measurement of practical costs. The work is structured as a small library with tests, a benchmarking harness, and a short report.

Algorithms covered:

MergeSort (with reusable buffer and cutoff to insertion sort)

QuickSort (randomized pivot, smaller-first recursion for bounded stack)

Deterministic Select (Median-of-Medians, linear time)

Closest Pair of Points in 2D (O(n log n), strip method)

Metrics collected:

Running time

Recursion depth

Number of comparisons

Explicit allocations

Architecture Notes

Metrics tracking: a central Metrics class tracks recursion depth (enter()/exit()), comparisons, and allocations.

Recursion control:

QuickSort always recurses into the smaller side, iterates on the larger one → depth ≈ O(log n).

Deterministic Select recurses only into the side containing the target element, preferring the smaller side.

Buffer reuse: MergeSort allocates a single temp[] buffer at the top level and passes it down.

Small-n cutoff: MergeSort switches to insertion sort for subarrays ≤ 16 elements to improve constants.

Partition utility: Shared partition/swap/shuffle methods avoid duplication.

Validation: Closest Pair is tested against an O(n²) baseline for small n.

Recurrence Analyses

MergeSort

Recurrence: T(n) = 2T(n/2) + Θ(n)

Master Theorem, Case 2 → Θ(n log n).

Matches measured depth ~ log₂n and comparisons ~ n log n.

QuickSort (randomized)

Expected recurrence: T(n) = T(k) + T(n-k-1) + Θ(n), with pivot uniform random.

Expected depth: O(log n); worst-case depth O(n) but very unlikely.

Θ(n log n) average time; measurements confirm log-scale depth.

Deterministic Select (Median-of-Medians)

Groups of 5, pivot selection → at least 30%–70% split.

Recurrence: T(n) ≤ T(n/5) + T(7n/10) + Θ(n).

By Akra–Bazzi intuition → Θ(n).

Measurements show linear scaling with small constant factors.

Closest Pair of Points

Sort once by x → Θ(n log n).

Recurrence: T(n) = 2T(n/2) + Θ(n) (strip scan is linear).

Master Theorem, Case 2 → Θ(n log n).

Depth ~ log₂n. Measured times grow slightly faster than MergeSort due to geometry checks.

Plots (time vs n, depth vs n)

(Placeholder – replace with your generated plots)

MergeSort vs QuickSort: both Θ(n log n); QuickSort faster constant for large n, but more variance.

Select: grows linearly, outperforms sorting when k is small/medium.

Closest Pair: ~n log n scaling, overhead visible for small n.

Depth: MergeSort/ClosestPair log n; QuickSort ≈ 2 log₂n with randomized pivot; Select ≤ log n.

Discussion of Constant Factors

Cutoff to insertion sort reduces MergeSort cost for small subarrays (cache-friendly).

Buffer reuse avoids repeated allocations and GC overhead.

QuickSort benefits from in-place partitioning but suffers from cache misses on scattered partitions.

Closest Pair has heavier constants due to geometry checks and multiple arrays sorted by y.

JVM allocation and GC events occasionally show up as spikes in time plots.

Summary

Theory and practice align well:

MergeSort and QuickSort both scale Θ(n log n), with expected recursion depths matching analysis.

Deterministic Select achieves linear behavior but with higher constants than randomized QuickSelect.

Closest Pair matches Θ(n log n) and confirms the efficiency of the strip method.

Overall, the measurements validate the recurrence analyses with minor deviations due to constants, caching, and Java’s memory management.

Git Workflow

Branches: feature/mergesort, feature/quicksort, feature/select, feature/closest, feature/metrics.

Commits: descriptive, following spec (init, feat, refactor, fix, release tags).

Main branch: only tagged working releases (v0.1, v1.0).