import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Experiment {
    static final long SEED = 42;
    static final int PATTERN_LEN = 50;

    public static void main(String[] args) throws IOException {
        Path out = Path.of(args.length > 0 ? args[0] : "results/results.csv");
        Files.createDirectories(out.toAbsolutePath().getParent());
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(out))) {
            w.println("algorithm,input,n,run,time_ms,ops,mem_cells,result");

            EditDistance rec = new EditDistanceRecursive();
            for (String type : List.of("random", "similar", "disjoint")) {
                for (int n : new int[]{4, 6, 8, 10, 12}) {
                    runEdit(w, rec, type, n, 3);
                }
            }
            runEdit(w, rec, "disjoint", 14, 3);
            EditDistance[] dp = {new EditDistanceTable(), new EditDistanceTwoRows()};
            for (EditDistance ed : dp) {
                for (String type : List.of("random", "similar", "disjoint")) {
                    for (int n : new int[]{250, 500, 1000, 2000, 4000}) {
                        runEdit(w, ed, type, n, 5);
                    }
                }
            }
            StringSearch[] algs = {new NaiveSearch(), new KmpSearch()};
            for (StringSearch s : algs) {
                for (String type : List.of("random", "worst")) {
                    for (int n : new int[]{100_000, 200_000, 400_000, 800_000, 1_600_000}) {
                        runSearch(w, s, type, n, 5);
                    }
                }
            }
        }
        System.out.println("written " + out);
    }

    static void runEdit(PrintWriter w, EditDistance ed, String type, int n, int runs) {
        String[] p = Generator.editPair(type, n, SEED + n);
        ed.distance(p[0], p[1]);
        for (int run = 1; run <= runs; run++) {
            long t0 = System.nanoTime();
            int d = ed.distance(p[0], p[1]);
            double ms = (System.nanoTime() - t0) / 1e6;
            w.printf(java.util.Locale.ROOT, "%s,%s,%d,%d,%.3f,%d,%d,%d%n",
                    ed.name(), type, n, run, ms, ed.ops, ed.memCells, d);
        }
        w.flush();
    }

    static void runSearch(PrintWriter w, StringSearch s, String type, int n, int runs) {
        String[] p = Generator.searchPair(type, n, PATTERN_LEN, SEED + n);
        s.search(p[0], p[1]);
        for (int run = 1; run <= runs; run++) {
            long t0 = System.nanoTime();
            int found = s.search(p[0], p[1]).size();
            double ms = (System.nanoTime() - t0) / 1e6;
            w.printf(java.util.Locale.ROOT, "%s,%s,%d,%d,%.3f,%d,%d,%d%n",
                    s.name(), type, n, run, ms, s.comparisons, 0, found);
        }
        w.flush();
    }
}
