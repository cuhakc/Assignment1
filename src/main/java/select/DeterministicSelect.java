package select;

import metrics.Metrics;
import java.util.Arrays;

public class DeterministicSelect {
    public static <T extends Comparable<T>> T select(T[] a, int k, Metrics metrics) {
        return select(a, 0, a.length - 1, k, metrics);
    }

    private static <T extends Comparable<T>> T select(T[] a, int lo, int hi, int k, Metrics metrics) {
        while (true) {
            if (lo == hi) return a[lo];
            int pivotIndex = pivot(a, lo, hi, metrics);
            pivotIndex = partition(a, lo, hi, pivotIndex, metrics);
            int rank = pivotIndex - lo;
            if (k == rank) return a[pivotIndex];
            else if (k < rank) hi = pivotIndex - 1;
            else {
                k -= rank + 1;
                lo = pivotIndex + 1;
            }
        }
    }

    private static <T extends Comparable<T>> int pivot(T[] a, int lo, int hi, Metrics metrics) {
        int n = hi - lo + 1;
        if (n < 5) return lo + n/2;
        int groups = (n + 4) / 5;
        for (int i = 0; i < groups; i++) {
            int subLo = lo + i*5;
            int subHi = Math.min(subLo + 4, hi);
            Arrays.sort(a, subLo, subHi+1); // insertion sort could be faster
            int medianIndex = subLo + (subHi - subLo)/2;
            swap(a, lo + i, medianIndex);
        }
        return pivot(a, lo, lo + groups - 1, metrics);
    }

    private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi, int pivotIndex, Metrics metrics) {
        T pivotValue = a[pivotIndex];
        swap(a, pivotIndex, hi);
        int storeIndex = lo;
        for (int i = lo; i < hi; i++) {
            metrics.incComparisons(1);
            if (a[i].compareTo(pivotValue) < 0) {
                swap(a, storeIndex, i);
                storeIndex++;
            }
        }
        swap(a, storeIndex, hi);
        return storeIndex;
    }

    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }
}