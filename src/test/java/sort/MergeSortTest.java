package sort;

import metrics.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class MergeSortTest {
    @Test
    public void testRandomArray() {
        Integer[] arr = new Integer[1000];
        Random r = new Random(42);
        for (int i = 0; i < arr.length; i++) arr[i] = r.nextInt();
        Integer[] expected = arr.clone();
        Arrays.sort(expected);
        Metrics m = new Metrics();
        m.start();
        MergeSort.sort(arr, m, 16);
        m.stop();
        assertArrayEquals(expected, arr);
        assertTrue(m.getMaxDepth() > 0);
    }
}
