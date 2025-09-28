# 📘 Assignment 1 – Divide & Conquer Algorithms

## 🎯 Learning Goals
- Implement classic divide-and-conquer algorithms with **safe recursion patterns**
- Analyze running-time recurrences using **Master Theorem** (3 cases) & **Akra–Bazzi intuition**
- Collect metrics: **time, recursion depth, comparisons, allocations**
- Communicate results with a short **report** and clean **Git history**

---

## ⚡ Implemented Algorithms
1. **MergeSort**
    - Linear merge with reusable buffer
    - Small-n cutoff (Insertion Sort)
    - Depth ~ log₂n

2. **QuickSort (Robust)**
    - Randomized pivot
    - Recurse on smaller partition, iterate larger → bounded stack O(log n) typical

3. **Deterministic Select (Median-of-Medians)**
    - Group of 5, median-of-medians pivot
    - Recurse only on needed side (prefer smaller)
    - Guaranteed linear time

4. **Closest Pair of Points (2D)**
    - Divide-and-conquer with strip method
    - O(n log n) with ~7 neighbor scan in strip

---

## 🏗️ Architecture Notes
- **Metrics**: central tracker for recursion depth, comparisons, and allocations
- **Recursion Control**:
    - QuickSort → smaller-first recursion
    - Select → only into side with target element
- **Buffer Reuse**: MergeSort allocates one temp[] at top level
- **Cutoff**: Insertion sort for very small subarrays
- **Validation**: Closest Pair checked against O(n²) baseline for n ≤ 2000

---

## 📐 Recurrence Analyses

**MergeSort**
- T(n) = 2T(n/2) + Θ(n)
- Master Case 2 → **Θ(n log n)**

**QuickSort (randomized)**
- T(n) = T(k) + T(n-k-1) + Θ(n)
- Expected depth: O(log n)
- Expected runtime → **Θ(n log n)**

**Deterministic Select (Median-of-Medians)**
- T(n) ≤ T(n/5) + T(7n/10) + Θ(n)
- Akra–Bazzi intuition → **Θ(n)**

**Closest Pair of Points**
- T(n) = 2T(n/2) + Θ(n)
- Master Case 2 → **Θ(n log n)**

---

## 📊 Plots & Measurements
*(Replace placeholders with your generated plots)*

- ⏱️ **Time vs n**
- 🌲 **Recursion depth vs n**
- 📉 Comparisons & allocations

Example placeholders:  


---

## 💡 Constant-Factor Effects
- Insertion-sort cutoff improves MergeSort on small n
- Reusable buffer avoids repeated allocations
- QuickSort is cache-sensitive but fast in practice
- Closest Pair has larger constants due to geometry checks
- Occasional JVM GC spikes show in measurements

---

## ✅ Summary
- MergeSort & QuickSort confirm **Θ(n log n)** scaling
- QuickSort faster constant, more variance
- Deterministic Select is **linear**, but with higher constants than QuickSelect
- Closest Pair matches **Θ(n log n)** with manageable depth
- Overall: **Theory aligns with measurements** with minor constant-factor deviations

---

## 🌿 Git Workflow
- **Branches**:
    - `feature/mergesort`, `feature/quicksort`, `feature/select`, `feature/closest`, `feature/metrics`
- **Commit storyline**:
    - `init: maven, junit5, ci, readme`
    - `feat(metrics): counters, depth tracker, CSV writer`
    - `feat(mergesort): baseline + reuse buffer + cutoff + tests`
    - `feat(quicksort): smaller-first recursion, randomized pivot + tests`
    - `refactor(util): partition, swap, shuffle, guards`
    - `feat(select): deterministic select (MoM5) + tests`
    - `feat(closest): divide-and-conquer implementation + tests`
    - `feat(cli): parse args, run algos, emit CSV`
    - `bench(jmh): harness for select vs sort`
    - `docs(report): master cases & AB intuition, initial plots`
    - `fix: edge cases (duplicates, tiny arrays)`
    - `release: v1.0`

---

## 🧪 Testing
- **Sorting**: correctness on random & adversarial arrays; recursion depth bounded
- **Select**: verified against `Arrays.sort(a)[k]` for 100 random trials
- **Closest Pair**: validated against O(n²) baseline for small n

---
