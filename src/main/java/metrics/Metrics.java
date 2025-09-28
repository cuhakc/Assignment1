package metrics;

public class Metrics {
    private long comparisons;
    private long swaps;
    private long allocations;
    private int currentDepth;
    private int maxDepth;
    private long startTime;
    private long elapsedNs;

    public void start() {
        comparisons = swaps = allocations = 0;
        currentDepth = maxDepth = 0;
        startTime = System.nanoTime();
    }

    public void stop() {
        elapsedNs = System.nanoTime() - startTime;
    }

    public void incComparisons(long c) { comparisons += c; }
    public void incSwaps(long s) { swaps += s; }
    public void incAllocations(long a) { allocations += a; }
    public void pushDepth() {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;
    }
    public void popDepth() { currentDepth--; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getAllocations() { return allocations; }
    public int getMaxDepth() { return maxDepth; }
    public long getElapsedNs() { return elapsedNs; }
}
