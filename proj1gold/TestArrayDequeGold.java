import org.junit.Test;
import static org.junit.Assert.*;

import java.util.stream.IntStream;

public class TestArrayDequeGold {
    @Test
    public void someTest() {
        StudentArrayDeque<Integer> l1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> l0 = new ArrayDequeSolution<>();
        int[] range = IntStream.range(1, 6).toArray();

        for (int i = 0; i < 10; i += 1) {
            int var = StdRandom.discrete(range);
            int randomNum = StdRandom.discrete(IntStream.range(1, 101).toArray());
            switch (var) {
                case 1:
                    l1.addFirst(randomNum);
                    l0.addFirst(randomNum);
                    assertArrayEquals(l1, l0);
                    break;
                case 2:

            }
        }
    }
}
