package byog.Core;

import byog.TileEngine.TERenderer;

import java.io.*;

public class Game {
    private static final int UI_LENGTH = 60;
    private static final int UI_WIDTH = 50;
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
     *
     * @param input the input string to feed to your program
     */
    public void playWithInputString(String input) throws InterruptedException {
        GameUI gameui = new GameUI(UI_LENGTH, UI_WIDTH);
        World world = new World();
        input = input.toLowerCase();


        // get initial world
        if (input.charAt(0) == 'l') {
            try {
                world = loadGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (input.charAt(0) == 'n') {
            world = newGame(input.substring(1));
        } else {
            System.exit(0);
        }

        ter.initialize(world.getWorldLength(), world.getWorldWidth());
        // apply operations
        while (true) {
            gameui.drawGraphic(world);
        }
    }

    public World newGame(String input) {
        // get world seed
        int seed = getSeed(input);
        WorldGenerator wg = new WorldGenerator(60, 40, seed);
        return wg.generateWorld();
    }

    /**
     * Parse a string start with seed number and end with s.
     */
    public int getSeed(String s) {
        StringBuilder seed = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 's') {
                break;
            }
            seed.append(s.charAt(i));
        }
        String seedStr = seed.toString();
        if (seedStr.length() > 9) {
            seedStr = seedStr.substring(0, 8);
        }
        return Integer.parseInt(seedStr);
    }

    public static void saveGame(WorldGenerator wg) throws IOException {
        FileOutputStream fos = new FileOutputStream("savedGame.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(wg);
        oos.close();
        fos.close();
        System.exit(0);
    }

    public static World loadGame() throws IOException {
        FileInputStream fis = new FileInputStream("savedGame.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        World world;
        try {
            world = (World) ois.readObject();
        } catch (ClassNotFoundException e) {
            System.exit(0);
            throw new RuntimeException(e);
        }
        ois.close();
        fis.close();
        return world;
    }

    public void endGame() {
        System.exit(0);
    }
}

