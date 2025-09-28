package sort;

import metrics.Metrics;

public class MergeSort {
    public static <T extends Comparable<T>> void sort(T[] array, Metrics metrics, int cutoff) {
        @SuppressWarnings("unchecked")
        T[] buffer = (T[]) new Comparable[array.length];
        metrics.incAllocations(1);
        sort(array, buffer, 0, array.length - 1, metrics, cutoff);
    }

    private static <T extends Comparable<T>> void sort(T[] a, T[] buf, int lo, int hi, Metrics metrics, int cutoff) {
        if (hi - lo + 1 <= cutoff) {
            insertionSort(a, lo, hi, metrics);
            return;
        }
        int mid = (lo + hi) >>> 1;
        metrics.pushDepth();
        sort(a, buf, lo, mid, metrics, cutoff);
        sort(a, buf, mid + 1, hi, metrics, cutoff);
        merge(a, buf, lo, mid, hi, metrics);
        metrics.popDepth();
    }

    private static <T extends Comparable<T>> void insertionSort(T[] a, int lo, int hi, Metrics metrics) {
        for (int i = lo + 1; i <= hi; i++) {
            T key = a[i];
            int j = i - 1;
            while (j >= lo && a[j].compareTo(key) > 0) {
                metrics.incComparisons(1);
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    private static <T extends Comparable<T>> void merge(T[] a, T[] buf, int lo, int mid, int hi, Metrics metrics) {
        System.arraycopy(a, lo, buf, lo, hi - lo + 1);
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            metrics.incComparisons(1);
            if (buf[i].compareTo(buf[j]) <= 0) {
                a[k++] = buf[i++];
            } else {
                a[k++] = buf[j++];
            }
        }
        while (i <= mid) a[k++] = buf[i++];
        while (j <= hi) a[k++] = buf[j++];
    }
}