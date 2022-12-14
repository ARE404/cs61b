package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

/**
 * This is the main entry point for the program. This class simply parses
 * the command line inputs, and lets the byog.Core.Game class take over
 * in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        }
        Game game = new Game();
        if (args.length == 1) {
            // play with input string
            game.playWithInputString(args[0]);
        } else {
            // play with keyboard
            game.playWithKeyboard();
        }
    }
}
