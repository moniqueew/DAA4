import java.util.ArrayList;
import java.util.List;

/** Общий интерфейс поиска: все вхождения, по возрастанию, с перекрытиями. */
public abstract class StringSearch {
    /** Число сравнений символов. */
    public long comparisons;

    public abstract String name();

    protected abstract List<Integer> doSearch(String t, String p);

    public final List<Integer> search(String t, String p) {
        if (p.isEmpty()) throw new IllegalArgumentException("pattern is empty");
        comparisons = 0;
        if (p.length() > t.length()) return new ArrayList<>();
        return doSearch(t, p);
    }
}
