package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    TERenderer ter = new TERenderer();

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,

        // process string
        input = input.toLowerCase();
        StringBuilder seedStr = new StringBuilder();
        if (input.charAt(0) == 'n') {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == 's') {
                    break;
                }
                seedStr.append(input.charAt(i));
            }
        } else if (input.charAt(0) == 'l') {

        }

        // run the game
        // deal with result

        boolean save = false;
        System.out.println(input);
        Pattern p = Pattern.compile("N(\\d+)S", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        if (!m.matches()) {
            System.out.println("This input string is not valid! Retry!");
            System.exit(1);
        }
        int SEED = Integer.parseInt(m.group(1));
        WorldGenerator wg = new WorldGenerator(SEED);
        return wg.generateWorld(60, 40);
    }
}
