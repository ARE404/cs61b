package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.IOException;

public class Game {

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
        input = input.toLowerCase();
        WorldGenerator wg = new WorldGenerator();
        if (input.charAt(0) == 'l') {
            try {
                wg = Utils.loadGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return WorldGenerator.world;
        } else if (input.charAt(0) == 'n') {
            input = input.substring(1);
            StringBuilder seed = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == 's') {
                    break;
                }
                seed.append(input.charAt(i));
            }
            String seedStr = seed.toString();
            if (seedStr.length() > 9) {
                seedStr = seedStr.substring(0, 8);
            }
            int SEED = Integer.parseInt(seedStr);
            wg = new WorldGenerator(SEED);
        } else {
            System.exit(0);
        }
        if (input.endsWith(":q")) {
            try {
                Utils.saveGame(wg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return wg.generateWorld(60, 40);
    }
}
