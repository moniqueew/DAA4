/**
 * Версия B: таблица (n+1) x (m+1) снизу вверх, время O(n*m), память O(n*m).
 * Состояние:  d[i][j] = расстояние между a[0..i) и b[0..j).
 * Переход:    d[i][j] = min(d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + (a[i-1]==b[j-1] ? 0 : 1)).
 * База:       d[i][0] = i, d[0][j] = j.
 */
public class EditDistanceTable extends EditDistance {
    @Override
    public String name() {
        return "table";
    }

    @Override
    protected int compute(String a, String b) {
        int n = a.length(), m = b.length();
        int[][] d = new int[n + 1][m + 1];
        memCells = (long) (n + 1) * (m + 1);
        for (int i = 0; i <= n; i++) d[i][0] = i;
        for (int j = 0; j <= m; j++) d[0][j] = j;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                ops++;
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + cost);
            }
        }
        return d[n][m];
    }
}
