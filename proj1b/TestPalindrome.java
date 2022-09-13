import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("abba"));
        assertTrue(palindrome.isPalindrome("abcddcba"));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("abcd"));
        assertFalse(palindrome.isPalindrome("saflkajskldf"));
        assertFalse(palindrome.isPalindrome("Aba"));
    }

    @Test
    public void testNewIsPalindrome() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("abab", cc));
        assertTrue(palindrome.isPalindrome("&&%%", cc));
        assertTrue(palindrome.isPalindrome("z{", cc));
        assertTrue(palindrome.isPalindrome("12", cc));
        assertTrue(palindrome.isPalindrome("-.", cc));
        assertFalse(palindrome.isPalindrome("AaAa", cc));
        assertFalse(palindrome.isPalindrome("aaaa", cc));
        assertFalse(palindrome.isPalindrome("AAAAA", cc));
        assertFalse(palindrome.isPalindrome("Aa", cc));
        assertFalse(palindrome.isPalindrome("13",cc));
        assertFalse(palindrome.isPalindrome("11111",cc));
    }
}
