package hw3.hash;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestComplexOomage {

    /**
     * Calls tests for SimpleOomage.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Your code here.
//        ArrayList<Integer> deadlyInt = new ArrayList<>(Arrays.asList(12, 23, 43, 23));
        deadlyList.add(new ComplexOomage(Arrays.asList(12, 23, 34, 44)));
        deadlyList.add(new ComplexOomage(Arrays.asList(231, 12, 23, 34, 44)));
        deadlyList.add(new ComplexOomage(Arrays.asList(123, 12, 23, 34, 44)));
        deadlyList.add(new ComplexOomage(Arrays.asList(112, 12, 23, 34, 44)));
        deadlyList.add(new ComplexOomage(Arrays.asList(133, 12, 23, 34, 44)));
        deadlyList.add(new ComplexOomage(Arrays.asList(22, 12, 23, 34, 44)));

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }
}
