import java.util.ArrayList;
import java.util.List;

/** KMP: сначала массив LPS, потом поиск, где индекс в тексте только растёт. */
public class KmpSearch extends StringSearch {
    @Override
    public String name() {
        return "kmp";
    }

    /** lps[i] = длина самого длинного собственного префикса p[0..i], который также его суффикс. */
    public int[] lps(String p) {
        int m = p.length();
        int[] lps = new int[m];
        int len = 0, i = 1;
        while (i < m) {
            comparisons++;
            if (p.charAt(i) == p.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
        return lps;
    }

    @Override
    protected List<Integer> doSearch(String t, String p) {
        List<Integer> res = new ArrayList<>();
        int n = t.length(), m = p.length();
        int[] lps = lps(p);
        int i = 0, j = 0; // i в тексте никогда не уменьшается
        while (i < n) {
            comparisons++;
            if (t.charAt(i) == p.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    res.add(i - j);
                    j = lps[j - 1];
                }
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return res;
    }
}
