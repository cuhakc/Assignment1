package sort;

import metrics.Metrics;
import java.util.Random;

public class QuickSort {
    private static final Random rand = new Random();

    public static <T extends Comparable<T>> void sort(T[] a, Metrics metrics) {
        sort(a, 0, a.length - 1, metrics);
    }

    private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi, Metrics metrics) {
        while (lo < hi) {
            int p = partition(a, lo, hi, metrics);
            int leftSize = p - lo;
            int rightSize = hi - p;
            if (leftSize < rightSize) {
                metrics.pushDepth();
                sort(a, lo, p - 1, metrics);
                metrics.popDepth();
                lo = p + 1;
            } else {
                metrics.pushDepth();
                sort(a, p + 1, hi, metrics);
                metrics.popDepth();
                hi = p - 1;
            }
        }
    }

    private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi, Metrics metrics) {
        int pivotIndex = lo + rand.nextInt(hi - lo + 1);
        swap(a, pivotIndex, hi);
        T pivot = a[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            metrics.incComparisons(1);
            if (a[j].compareTo(pivot) <= 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, hi);
        return i + 1;
    }

    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }
}
