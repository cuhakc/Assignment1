# Assignment1
Assignment 1 — Divide & Conquer Algorithms

This repository template and report scaffold helps you implement, measure, test, and document four classic divide-and-conquer algorithms for the assignment.

⸻

Repository layout

assignment1/
├── pom.xml                       # Maven project
├── README.md                     # This file (report scaffold + instructions)
├── src/main/java/...             # algorithms + utils + CLI
├── src/test/java/...             # JUnit tests
├── bench/                        # JMH microbenchmarks (optional)
├── scripts/                      # run/plot helpers (bash/python)
├── metrics-output/               # CSV outputs from runs
└── docs/                         # generated plots & final report


⸻

Implementation checklist (per-algorithm)

MergeSort
•	Stable mergesort using a single reusable auxiliary buffer (allocated once per run).
•	Linear merge procedure copying into buffer then back or merging into buffer then swapping references.
•	Small-n cutoff: switch to insertion sort for n <= C (experiment with C = 16, 32).
•	Track metrics: comparisons, array allocations (should be O(1) extra), recursion depth, time.

QuickSort (robust)
•	Randomized pivot: swap random element into pivot position.
•	Partition in-place (Lomuto or Hoare — prefer Hoare-style for fewer swaps; ensure correctness with duplicates).
•	Always recurse on the smaller partition and loop on the larger to bound stack depth.
•	Track metrics: comparisons, swaps, max recursion depth, time.

Deterministic Select (Median-of-Medians)
•	Group elements in blocks of 5, compute each group median (insertion sort on block of 5), find median-of-medians recursively.
•	Use in-place partition; recurse only on the needed side; prefer recursing into smaller side to bound depth.
•	Track metrics: comparisons, recursive calls, time.

Closest Pair of Points (2D)
•	Sort points by x once up front; keep secondary arrays sorted by y when merging.
•	Classic divide & conquer: split by median x, recursive calls, build strip of points within d of midline and scan neighbors by y (up to ~7 checks per point).
•	Track metrics: comparisons (distance computations), recursion depth, allocations (temporary arrays), time.

⸻

Safety & recursion patterns
•	Use recurseSmallerThenLoop idiom for QuickSort/Select: always recurse on smaller half and while-loop the larger to ensure O(log n) stack in expectation.
•	For deterministic select, make sure groups-of-5 median extraction is iterative per group to avoid deep temporary recursion.
•	For Closest Pair, limit temporary arrays by reusing buffers and passing slices (indices) rather than copying arrays when possible.

⸻

Metrics & instrumentation
•	Central Metrics class (singleton-per-run) capturing:
•	long comparisons, long swaps, long allocations, int maxDepth, int currentDepth, long elapsedNs.
•	Expose simple APIs to pushDepth()/popDepth() to safely update currentDepth and maxDepth.
•	CSV writer: one row per run with fields: algo, n, seed, elapsed_ns, comparisons, swaps, allocations, maxDepth, cutoff.
•	CLI should support repeating each input R times for averaging and provide raw CSV.

⸻

Testing plan
•	Unit tests (JUnit5): correctness on random arrays and special adversarial arrays (sorted, reverse, many duplicates).
•	QuickSort: assert maxDepth <= 2 * floor(log2(n)) + 8 for many randomized seeds (statistical check, not absolute proof).
•	Select: compare select(a, k) against Arrays.copyOf(a) + Arrays.sort() for 100 random trials with varying n.
•	Closest Pair: for n ≤ 2000, brute-force O(n^2) check; assert distances equal and also coordinates pairs match within tolerance.

⸻

Recurrence analysis (to put in README.md / report — 2–6 sentences each)

MergeSort

Master theorem case 2: recurrence T(n) = 2T(n/2) + Θ(n) gives T(n) = Θ(n log n). The linear merge step dominates per level and there are log n levels.

QuickSort (randomized)

Randomized QuickSort: typical-case recurrence T(n) = T(n/2) + T(n/2) + Θ(n) in expectation reduces to Θ(n log n) by Master Theorem (case 2). Using randomized pivot yields balanced partitions on average; recursion-on-smaller ensures stack depth is typically O(log n).

Deterministic Select (Median-of-Medians)

Akra–Bazzi / recurrence intuition: grouping by 5 guarantees the pivot leaves at least a constant fraction of elements on each side, producing recurrence T(n) ≤ T(n/5) + T(7n/10) + Θ(n) which solves to Θ(n).

Closest Pair (2D)

Divide-and-conquer recurrence T(n) = 2T(n/2) + Θ(n) (sorting/merge-by-y and strip scan are linear per level) leads to Θ(n log n). The strip check examines a constant number (≤ 7–8) of neighbors per point.

⸻

Plotting & experiments
•	Collect data points: choose n in geometric progression (e.g., 1e3, 2e3, 4e3, … up to memory limits).
•	For each n, run R=5..20 trials, record mean and standard deviation for elapsed_ns and maxDepth.
•	Use a Python script (scripts/plot.py) to read CSV and plot time vs n (log-log) and depth vs n (log-linear). Save PNGs to docs/.
•	Discussion items: effect of cutoff size for MergeSort (smaller constant factors), cache effects, JVM warmup & GC noise (use JMH or warmup runs to reduce variance).

⸻

Example CSV row

algo,n,seed,trial,elapsed_ns,comparisons,swaps,allocations,maxDepth,cutoff
mergesort,100000,12345,1,12345678,1500000,0,1,18,16


⸻

GitHub workflow & commit storyline (suggested)
•	Branches: feature/mergesort, feature/quicksort, feature/select, feature/closest, feature/metrics.
•	Commit messages (examples):
•	init: maven, junit5, ci, readme
•	feat(metrics): counters, depth tracker, CSV writer
•	feat(mergesort): baseline + reuse buffer + cutoff + tests
•	feat(quicksort): smaller-first recursion, randomized pivot + tests
•	refactor(util): partition, swap, shuffle, guards
•	feat(select): deterministic select (MoM5) + tests
•	feat(closest): divide-and-conquer implementation + tests
•	feat(cli): parse args, run algos, emit CSV
•	bench(jmh): harness for select vs sort
•	docs(report): master cases & AB intuition, initial plots
•	fix: edge cases (duplicates, tiny arrays)
•	release: v1.0

Tag releases on main: v0.1, v1.0 for final stable submission.

⸻

CLI (suggested options)

Usage: java -jar assignment1.jar --algo mergesort --n 100000 --seed 42 --trials 10 --cutoff 16 --out metrics-output/mergesort.csv

Options: --algo, --n, --seed, --trials, --cutoff, --out, --warmup.

⸻

JMH harness (optional but recommended)
•	Use JMH to compare deterministic select vs sort for various n; ensure proper JVM warmup and iterations to obtain robust timings.

⸻