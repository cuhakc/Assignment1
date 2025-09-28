package select;

import metrics.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    @Test
    public void testSelectMatchesSorted() {
        Random r = new Random(44);
        for (int trial = 0; trial < 50; trial++) {
            int n = 100 + r.nextInt(400);
            Integer[] arr = new Integer[n];
            for (int i = 0; i < n; i++) arr[i] = r.nextInt(1000);
            int k = r.nextInt(n);
            Integer[] sorted = arr.clone();
            Arrays.sort(sorted);
            Metrics m = new Metrics();
            m.start();
            Integer sel = DeterministicSelect.select(arr.clone(), k, m);
            m.stop();
            assertEquals(sorted[k], sel);
        }
    }
}