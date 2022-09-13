public class OffByN implements CharacterComparator {
    private int diff;

    public OffByN(int i) {
        diff = i;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == diff;
    }
}
