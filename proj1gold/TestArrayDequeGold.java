import org.junit.Test;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

public class TestArrayDequeGold {
    @Test
    public void someTest() {
        ArrayDequeSolution<Integer> l0 = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> l1 = new StudentArrayDeque<>();
        String errorMessage = new String("\n");

        for (int i = 0; i < 1000; i += 1) {
            int var = StdRandom.discrete(IntStream.range(1, 6).toArray());
            int randomNum = StdRandom.discrete(IntStream.range(1, 51).toArray());
            switch (var) {
                case 1:
                    l1.addFirst(randomNum);
                    l0.addFirst(randomNum);
                    errorMessage += "addFirst(" + randomNum + ")\n";
                    assertEquals(errorMessage, l0.get(0), l1.get(0));
                    break;
                case 2:
                    l0.addLast(randomNum);
                    l1.addLast(randomNum);
                    errorMessage += "addLast(" + randomNum + ")\n";
                    assertEquals(errorMessage, l0.get(l0.size() - 1), l1.get(l1.size() - 1));
                    break;
                case 3:
                    if (l0.size() > 0 && l1.size() > 0) {
                        errorMessage += "removeFirst()\n";
                        assertEquals(errorMessage, l0.removeFirst(), l1.removeFirst());
                    }
                    break;
                case 4:
                    if (l0.size() > 0 && l1.size() > 0) {
                        errorMessage += "removeLast()\n";
                        assertEquals(errorMessage, l0.removeLast(), l1.removeLast());
                    }
                    break;
            }
        }
    }
}
