import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SearchTest {
    private final NaiveSearch naive = new NaiveSearch();
    private final KmpSearch kmp = new KmpSearch();

    @Test
    void lpsReferenceArrays() {
        assertArrayEquals(new int[]{0, 1, 2, 0, 1, 2, 3, 3}, kmp.lps("AAACAAAA"));
        assertArrayEquals(new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4}, kmp.lps("ABABCABAB"));
    }

    @Test
    void referenceMatches() {
        for (StringSearch s : new StringSearch[]{naive, kmp}) {
            assertEquals(List.of(10), s.search("ABABDABACDABABCABAB", "ABABCABAB"), s.name());
            assertEquals(List.of(0, 1, 2, 3), s.search("AAAAA", "AA"), s.name());
            assertEquals(List.of(0, 2, 4), s.search("ABABABA", "ABA"), s.name());
        }
    }

    @Test
    void noMatchGivesEmptyList() {
        for (StringSearch s : new StringSearch[]{naive, kmp}) {
            assertTrue(s.search("ABCDEF", "XYZ").isEmpty(), s.name());
        }
    }

    @Test
    void edgeCases() {
        for (StringSearch s : new StringSearch[]{naive, kmp}) {
            assertThrows(IllegalArgumentException.class, () -> s.search("ABC", ""), s.name());
            assertTrue(s.search("AB", "ABC").isEmpty(), s.name());
        }
    }

    @Test
    void naiveAndKmpAgreeOnRandomTexts() {
        Random r = new Random(2);
        for (int k = 0; k < 100; k++) {
            String t = Generator.random(r, 1 + r.nextInt(60), 2);
            String p = Generator.random(r, 1 + r.nextInt(5), 2);
            assertEquals(naive.search(t, p), kmp.search(t, p), t + " / " + p);
        }
    }
}
