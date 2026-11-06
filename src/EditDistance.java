public abstract class EditDistance {
    public long ops;
    public long memCells;

    public abstract String name();

    protected abstract int compute(String a, String b);

    public final int distance(String a, String b) {
        ops = 0;
        memCells = 0;
        return compute(a, b);
    }
}
