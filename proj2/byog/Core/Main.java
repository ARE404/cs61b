package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            // play with input string
            Game game = new Game();
            TETile[][] worldState = game.playWithInputString(args[0]);
            // draw game state in terminal
            System.out.println(TETile.toString(worldState));


            // render for debug
            TERenderer ter = new TERenderer();
            ter.initialize(WorldGenerator.WORLD_LENGTH, WorldGenerator.WORLD_WIDTH);
            ter.renderFrame(worldState);
        } else {
            // play with keyboard
            Game game = new Game();
            game.playWithKeyboard();
        }
    }
}
