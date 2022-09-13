public class Palindrome {
    /** return a char Deque which the characters appear in the same order as in the String */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> l = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            l.addLast(word.charAt(i));
        }
        return l;
    }

    public boolean helper(String word, int index) {
//        if (word.length() % 2 == 0 && index == word.length() / 2 - 1) {
//            return word.charAt(index) == word.charAt(index + 1);
//        }
//        if (word.length() % 2 != 0 && index == word.length() / 2) {
//            return true;
//        }
        if (index > word.length() / 2) {
            return true;
        }
        int symmetryIndex = word.length() - index - 1;
        return helper(word, index + 1) && (word.charAt(index) == word.charAt(symmetryIndex));
    }

    /** judge if String word is Palindrome */
    public boolean isPalindrome(String word) {
        return helper(word, 0);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        }
        int forwardIndex = 0;
        int backwardIndex = word.length() - 1;
        while(forwardIndex < backwardIndex) {
            if (!cc.equalChars(word.charAt(forwardIndex), word.charAt(backwardIndex))) {
                return false;
            }
            forwardIndex += 1;
            backwardIndex -= 1;
        }
        return true;
    }
}
