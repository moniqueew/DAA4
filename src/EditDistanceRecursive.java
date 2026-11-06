/**
 * Версия A: рекурсия без памяти.
 * Состояние:  d(i, j) = расстояние между первыми i символами a и первыми j символами b.
 * Переход:    если a[i-1]==b[j-1]: d(i-1, j-1);
 *             иначе 1 + min(d(i-1, j) [удаление], d(i, j-1) [вставка], d(i-1, j-1) [замена]).
 * База:       d(0, j) = j, d(i, 0) = i.
 */
public class EditDistanceRecursive extends EditDistance {
    @Override
    public String name() {
        return "recursive";
    }

    @Override
    protected int compute(String a, String b) {
        return rec(a, b, a.length(), b.length());
    }

    private int rec(String a, String b, int i, int j) {
        ops++;
        if (i == 0) return j;
        if (j == 0) return i;
        if (a.charAt(i - 1) == b.charAt(j - 1)) return rec(a, b, i - 1, j - 1);
        return 1 + Math.min(rec(a, b, i - 1, j),
                   Math.min(rec(a, b, i, j - 1), rec(a, b, i - 1, j - 1)));
    }
}
