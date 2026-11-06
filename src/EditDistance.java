/** Общий интерфейс для трёх версий Edit Distance: эксперимент — один цикл. */
public abstract class EditDistance {
    /** Счётчик работы: вызовы рекурсии (A) или вычисленные клетки (B, C). */
    public long ops;
    /** Сколько клеток памяти выделено под таблицу. */
    public long memCells;

    public abstract String name();

    protected abstract int compute(String a, String b);

    public final int distance(String a, String b) {
        ops = 0;
        memCells = 0;
        return compute(a, b);
    }
}
