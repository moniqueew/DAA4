import java.util.ArrayList;
import java.util.List;

/** Naive: пробуем каждую стартовую позицию и сравниваем посимвольно. */
public class NaiveSearch extends StringSearch {
    @Override
    public String name() {
        return "naive";
    }

    @Override
    protected List<Integer> doSearch(String t, String p) {
        List<Integer> res = new ArrayList<>();
        int n = t.length(), m = p.length();
        for (int s = 0; s + m <= n; s++) {
            int k = 0;
            while (k < m) {
                comparisons++;
                if (t.charAt(s + k) != p.charAt(k)) break;
                k++;
            }
            if (k == m) res.add(s);
        }
        return res;
    }
}
