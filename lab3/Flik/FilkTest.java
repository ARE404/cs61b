import static org.junit.Assert.*;
import org.junit.Test;

public class FilkTest {
    @Test
    public void testIsSameNumber() {
        assertTrue(Flik.isSameNumber(1,1));
        assertTrue(Flik.isSameNumber(2,2));
        assertTrue(Flik.isSameNumber(3,3));
        assertFalse(Flik.isSameNumber(2,1));
//        assertTrue(Flik.isSameNumber(128, 128));
        assertTrue(Flik.isSameNumber(129, 129));
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("all", FilkTest.class);
    }
}
