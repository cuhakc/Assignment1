package cli;

import metrics.Metrics;
import sort.MergeSort;
import sort.QuickSort;
import select.DeterministicSelect;
import geometry.ClosestPair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Assignment1CLI {
    public static void main(String[] args) throws IOException {
        if (args.length < 5) {
            System.out.println("Usage: java -cp target/classes cli.Assignment1CLI <algo> <n> <trials> <seed> <out.csv> [cutoff] [maxValue]");
            System.out.println("  <algo>: mergesort | quicksort | select | closest");
            System.out.println("  <n>: size of input");
            System.out.println("  <trials>: number of repetitions");
            System.out.println("  <seed>: random seed");
            System.out.println("  <out.csv>: output CSV file");
            System.out.println("  [cutoff]: optional cutoff for mergesort (default 16)");
            System.out.println("  [maxValue]: optional max absolute value for random array elements (default Integer.MAX_VALUE)");
            System.exit(1);
        }
        String algo = args[0];
        int n = Integer.parseInt(args[1]);
        int trials = Integer.parseInt(args[2]);
        long seed = Long.parseLong(args[3]);
        String outFile = args[4];
        int cutoff = args.length > 5 ? Integer.parseInt(args[5]) : 16;
        int maxValue = args.length > 6 ? Integer.parseInt(args[6]) : Integer.MAX_VALUE;

        boolean newFile = !new File(outFile).exists();
        try (FileWriter writer = new FileWriter(outFile, true)) {
            // write header if new file
            if (newFile) {
                writer.write("algo,n,seed,trial,elapsed_ns,comparisons,swaps,allocations,maxDepth,cutoff\n");
                writer.flush();
            }
            Random rand = new Random(seed);
            for (int t = 0; t < trials; t++) {
                Metrics m = new Metrics();
                if (algo.equalsIgnoreCase("mergesort")) {
                    Integer[] arr = randArray(n, rand, maxValue);
                    m.start();
                    MergeSort.sort(arr, m, cutoff);
                    m.stop();
                    writeRow(writer, algo, n, seed, t, m, cutoff);
                } else if (algo.equalsIgnoreCase("quicksort")) {
                    Integer[] arr = randArray(n, rand, maxValue);
                    m.start();
                    QuickSort.sort(arr, m);
                    m.stop();
                    writeRow(writer, algo, n, seed, t, m, cutoff);
                } else if (algo.equalsIgnoreCase("select")) {
                    Integer[] arr = randArray(n, rand, maxValue);
                    int k = rand.nextInt(n);
                    m.start();
                    DeterministicSelect.select(arr, k, m);
                    m.stop();
                    writeRow(writer, algo, n, seed, t, m, cutoff);
                } else if (algo.equalsIgnoreCase("closest")) {
                    ClosestPair.Point[] pts = randPoints(n, rand);
                    m.start();
                    ClosestPair.closestPair(pts, m);
                    m.stop();
                    writeRow(writer, algo, n, seed, t, m, cutoff);
                } else {
                    System.err.println("Unknown algo: " + algo);
                    System.exit(1);
                }
            }
        }
    }

    private static Integer[] randArray(int n, Random rand, int maxValue) {
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            int val = maxValue == Integer.MAX_VALUE ? rand.nextInt() : rand.nextInt(maxValue);
            arr[i] = val;
        }
        return arr;
    }

    private static ClosestPair.Point[] randPoints(int n, Random rand) {
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) pts[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        return pts;
    }

    private static void writeRow(FileWriter writer, String algo, int n, long seed, int trial, Metrics m, int cutoff) throws IOException {
        writer.write(String.format("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",
                algo,
                n,
                seed,
                trial,
                m.getElapsedNs(),
                m.getComparisons(),
                m.getSwaps(),
                m.getAllocations(),
                m.getMaxDepth(),
                cutoff));
        writer.flush();
    }
}