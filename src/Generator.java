import java.util.Random;

/** Свой генератор данных с фиксированным seed: одна и та же команда даёт те же числа. */
public class Generator {
    private static final String LETTERS = "ACGT";

    public static String random(Random r, int n, int alphabet) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) sb.append(LETTERS.charAt(r.nextInt(alphabet)));
        return sb.toString();
    }

    /** Копия s, где примерно rate символов заменено на случайные. */
    public static String mutate(Random r, String s, double rate) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            if (r.nextDouble() < rate) sb.setCharAt(i, LETTERS.charAt(r.nextInt(4)));
        }
        return sb.toString();
    }

    public static String repeat(char c, int n) {
        return String.valueOf(c).repeat(n);
    }

    /** Пары строк для Edit Distance. */
    public static String[] editPair(String type, int n, long seed) {
        Random r = new Random(seed);
        switch (type) {
            case "random":
                return new String[]{random(r, n, 4), random(r, n, 4)};
            case "similar": {
                String a = random(r, n, 4);
                return new String[]{a, mutate(r, a, 0.10)};
            }
            case "disjoint":
                return new String[]{repeat('A', n), repeat('B', n)};
            default:
                throw new IllegalArgumentException(type);
        }
    }

    /** Пары (текст, паттерн) для поиска. */
    public static String[] searchPair(String type, int n, int m, long seed) {
        Random r = new Random(seed);
        switch (type) {
            case "random":
                return new String[]{random(r, n, 4), random(r, m, 4)};
            case "worst":
                return new String[]{repeat('A', n), repeat('A', m - 1) + "B"};
            default:
                throw new IllegalArgumentException(type);
        }
    }
}
