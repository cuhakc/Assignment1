package geometry;

import metrics.Metrics;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closestPair(Point[] pts, Metrics metrics) {
        Point[] px = pts.clone();
        Point[] py = pts.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));
        return closest(px, py, 0, pts.length - 1, metrics);
    }

    private static double closest(Point[] px, Point[] py, int lo, int hi, Metrics metrics) {
        int n = hi - lo + 1;
        if (n <= 3) {
            return bruteForce(px, lo, hi, metrics);
        }
        int mid = (lo + hi) / 2;
        double midX = px[mid].x;
        metrics.pushDepth();
        double dl = closest(px, py, lo, mid, metrics);
        double dr = closest(px, py, mid + 1, hi, metrics);
        metrics.popDepth();
        double d = Math.min(dl, dr);

        // build strip
        Point[] strip = new Point[n];
        int j = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(py[i].x - midX) < d) {
                strip[j++] = py[i];
            }
        }
        return Math.min(d, stripClosest(strip, j, d, metrics));
    }

    private static double bruteForce(Point[] pts, int lo, int hi, Metrics metrics) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = lo; i <= hi; i++) {
            for (int j = i + 1; j <= hi; j++) {
                metrics.incComparisons(1);
                double dist = dist(pts[i], pts[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double stripClosest(Point[] strip, int size, double d, Metrics metrics) {
        double min = d;
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; ++j) {
                metrics.incComparisons(1);
                double dist = dist(strip[i], strip[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double dist(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
//hello
//privet