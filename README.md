# DAA Assignment 4 — Edit Distance and String Search

## Project layout
- `src/main/java/daa` — Edit Distance (`EditDistanceRecursive`, `EditDistanceTable`, `EditDistanceTwoRows`), Search (`NaiveSearch`, `KmpSearch`), `Generator` (seed fixed), `Main`.
- `src/test/java/daa` — JUnit 5 тесты.
- `results/results.csv`, `results/plots/*.png`, `REPORT.pdf`.

## Run the Benchmark
Runs the automated benchmark for all five algorithm implementations (EditDistanceRecursive, EditDistanceTable, EditDistanceTwoRows, NaiveSearch, and KmpSearch) across different input types (random, similar, disjoint, worst).
All datasets are generated deterministically using new Random(SEED) (SEED = 42) to guarantee identical operation counters across executions. Benchmark results are written to results/results.csv containing timing (time_ms), operation counts (ops/comparisons), and allocated memory cells (mem_cells)
## Design Highlights
Abstract Base Classes: EditDistance and StringSearch encapsulate metric collection (ops, memCells, comparisons) and ensure standardized execution contracts.   
Edit Distance Implementations:EditDistanceRecursive: Pure recursive brute-force reference implementation ($O(3^{\min(n,m)})$ time complexity).   
EditDistanceTable: Full 2D dynamic programming grid maintaining $(n+1) \times (m+1)$ memory cells for full matrix visualization.   
EditDistanceTwoRows: Space-optimized DP reducing auxiliary memory from $O(n \cdot m)$ to $O(m)$ by swapping only two rows (prev and cur).   
String Search Implementations:NaiveSearch: Standard brute-force window sliding character comparison.   
KmpSearch: Linear-time Knuth-Morris-Pratt algorithm utilizing a precomputed Longest Prefix Suffix (lps) array to eliminate redundant text pointer backtracks.   
Exact Operational Metrics:ops: Tracks exact dynamic programming table cell computations.   
memCells: Counts total integer memory allocations in DP state tables.   
comparisons: Counts exact character comparisons performed during string matching and LPS preprocessing.
