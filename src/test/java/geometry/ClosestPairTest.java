package geometry;

import java.util.Arrays;

public class ClosestPairTest {
    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closestPair(Point[] points, metrics.Metrics m) {
        if (points.length < 2) return Double.POSITIVE_INFINITY;
        Point[] ptsByX = points.clone();
        Arrays.sort(ptsByX, (a,b) -> Double.compare(a.x, b.x));
        Point[] ptsByY = ptsByX.clone();
        Arrays.sort(ptsByY, (a,b) -> Double.compare(a.y, b.y));
        return closestPairRec(ptsByX, ptsByY, 0, ptsByX.length);
    }

    private static double closestPairRec(Point[] ptsByX, Point[] ptsByY, int lo, int hi) {
        int n = hi - lo;
        if (n <= 3) {  // brute force for tiny
            double min = Double.POSITIVE_INFINITY;
            for (int i = lo; i < hi; i++) {
                for (int j = i + 1; j < hi; j++) {
                    double d = dist(ptsByX[i], ptsByX[j]);
                    if (d < min) min = d;
                }
            }
            Arrays.sort(ptsByY, lo, hi, (a,b) -> Double.compare(a.y, b.y));
            return min;
        }

        int mid = lo + n/2;
        double midX = ptsByX[mid].x;

        // Divide Y-array into leftY and rightY (preserve y-order)
        Point[] leftY = new Point[mid - lo];
        Point[] rightY = new Point[hi - mid];
        int li = 0, ri = 0;
        for (int i = 0; i < ptsByY.length; i++) {
            Point p = ptsByY[i];
            if (p.x < midX || (p.x == midX && li < leftY.length)) {
                if (p.x >= ptsByX[lo].x && p.x <= ptsByX[mid-1].x) {
                    leftY[li++] = p;
                }
            } else {
                if (p.x >= ptsByX[mid].x && p.x <= ptsByX[hi-1].x) {
                    rightY[ri++] = p;
                }
            }
        }

        double dl = closestPairRec(ptsByX, leftY, lo, mid);
        double dr = closestPairRec(ptsByX, rightY, mid, hi);
        double d = Math.min(dl, dr);

        // Build strip from ptsByY (already sorted by y)
        Point[] strip = new Point[n];
        int s = 0;
        for (Point p : ptsByY) {
            if (Math.abs(p.x - midX) < d) strip[s++] = p;
        }

        // Check strip — at most 7 ahead
        for (int i = 0; i < s; i++) {
            for (int j = i+1; j < s && (strip[j].y - strip[i].y) < d; j++) {
                double dist = dist(strip[i], strip[j]);
                if (dist < d) d = dist;
            }
        }

        return d;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}