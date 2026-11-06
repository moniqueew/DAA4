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
