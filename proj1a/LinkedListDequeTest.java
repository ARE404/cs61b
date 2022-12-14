/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

    /**
     * Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     * <p>
     * && is the "and" operation.
     */
    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");
        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("middle");
        passed = checkSize(2, lld1.size()) && passed;

        lld1.addLast("back");
        passed = checkSize(3, lld1.size()) && passed;

        System.out.println("Printing out deque: ");
        lld1.printDeque();

        printTestStatus(passed);
    }

    /**
     * Adds an item, then removes an item, and ensures that dll is empty afterwards.
     */
    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        // should be empty
        passed = checkEmpty(true, lld1.isEmpty()) && passed;

        printTestStatus(passed);
    }

    public static void getRecursiveTest() {
        LinkedListDeque<Integer> testlist = new LinkedListDeque<>();
        boolean passed = checkEmpty(true, testlist.isEmpty());

        testlist.addFirst(10);
        testlist.addFirst(20);
        testlist.addFirst(30);
        testlist.addFirst(40);
        testlist.addFirst(50);
        passed = checkEmpty(false, testlist.isEmpty()) && passed;

        int getReturn = testlist.getRecursive(3);

        printTestStatus(passed);
    }

    public static void test() {
//        ArrayDeque<Integer> testlist = new ArrayDeque<Integer>();
//        testlist.addFirst(0);
//        testlist.addLast(1);
//        testlist.addFirst(2);
//        testlist.addFirst(3);
//        testlist.addFirst(4);
//        testlist.get(2)      ;
//        testlist.addFirst(6);
//        testlist.removeLast()      ;
//        testlist.removeLast()      ;
//        testlist.removeLast()      ;
//        testlist.addLast(10);
//        testlist.addLast(11);
//        testlist.addLast(12);
//        testlist.addFirst(13);
//        testlist.addFirst(14);
//        testlist.get(5)      ;
//        testlist.addLast(16);
//        testlist.addLast(17);
//        testlist.addLast(18);
//        testlist.removeLast()      ;
//        testlist.addFirst(20);
//        testlist.get(2)      ;
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
//addIsEmptySizeTest();
//addRemoveTest();
        test();
        getRecursiveTest();
    }
}
