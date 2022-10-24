package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

/**
 * This is the main entry point for the program. This class simply parses
 * the command line inputs, and lets the byog.Core.Game class take over
 * in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            // play with input string
            Game game = new Game();
            TETile[][] world = game.playWithInputString(args[0]);
            TERenderer ter = new TERenderer();
            ter.initialize(WorldGenerator.getWorldLength(), WorldGenerator.getWorldWidth());
            ter.renderFrame(world);
        } else {
            // play with keyboard
            Game game = new Game();
            game.playWithKeyboard();
        }
    }
}
