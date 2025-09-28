package sort;

import metrics.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {
    @Test
    public void testRandomArray() {
        Integer[] arr = new Integer[1000];
        Random r = new Random(43);
        for (int i = 0; i < arr.length; i++) arr[i] = r.nextInt();
        Integer[] expected = arr.clone();
        Arrays.sort(expected);
        Metrics m = new Metrics();
        m.start();
        QuickSort.sort(arr, m);
        m.stop();
        assertArrayEquals(expected, arr);
        assertTrue(m.getMaxDepth() <= 2 * (int)Math.floor(Math.log(arr.length)/Math.log(2)) + 8);
    }
}
