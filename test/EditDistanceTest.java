import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import org.junit.jupiter.api.Test;

class EditDistanceTest {
    private final EditDistance[] all = {
            new EditDistanceRecursive(), new EditDistanceTable(), new EditDistanceTwoRows()};

    @Test
    void referenceValues() {
        for (EditDistance ed : all) {
            assertEquals(3, ed.distance("kitten", "sitting"), ed.name());
            assertEquals(2, ed.distance("flaw", "lawn"), ed.name());
            assertEquals(5, ed.distance("intention", "execution"), ed.name());
            assertEquals(3, ed.distance("", "abc"), ed.name());
            assertEquals(0, ed.distance("abc", "abc"), ed.name());
        }
    }

    @Test
    void emptyStrings() {
        for (EditDistance ed : all) {
            assertEquals(0, ed.distance("", ""), ed.name());
            assertEquals(3, ed.distance("abc", ""), ed.name());
        }
    }

    @Test
    void threeVersionsAgreeOnRandomShortStrings() {
        Random r = new Random(1);
        for (int k = 0; k < 100; k++) {
            String a = Generator.random(r, r.nextInt(9), 3);
            String b = Generator.random(r, r.nextInt(9), 3);
            int expected = all[1].distance(a, b);
            assertEquals(expected, all[0].distance(a, b), a + " / " + b);
            assertEquals(expected, all[2].distance(a, b), a + " / " + b);
        }
    }
}
