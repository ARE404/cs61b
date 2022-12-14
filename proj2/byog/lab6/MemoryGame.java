package byog.lab6;

import byog.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String res = "";
        while (res.length() < n) {
            int index = RandomUtils.uniform(rand, 0, 26);
            res += CHARACTERS[index];
        }
        return res;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        // clear canvas
        StdDraw.clear(Color.BLACK);
        // draw
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(width / 2., height / 2., s);
        // display
        StdDraw.show();
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters) throws InterruptedException {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); ++i) {
            // display a char
            char c = letters.charAt(i);
            drawFrame(Character.toString(c));
            // wait 1 sec
            Thread.sleep(1000);
            // disappear
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            // wait 0.5 for another
            Thread.sleep(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String res = "";
        int count = 0;
        while (count != n) {
            // if there have key
            if (StdDraw.hasNextKeyTyped()) {
                Character key = StdDraw.nextKeyTyped();
                // store key value
                res = res.concat(key.toString());
                // display
                drawFrame(res);
                count++;
            }
        }
        return res;
    }

    public void startGame() throws InterruptedException {
        //TODO: Set any relevant variables before the game starts
        Integer turnNum = 1;
        String randomString;
        String inputStr;
        //TODO: Establish Game loop
        while (true) {
            //display round message
            drawFrame("Round: " + turnNum);
            Thread.sleep(2000);
            //generate String
            randomString = generateRandomString(turnNum);
            //flash string
            flashSequence(randomString);
            //wait for input
            inputStr = solicitNCharsInput(turnNum);
            Thread.sleep(500);
            // check
            if (randomString.equals(inputStr)) {
                turnNum++;
            } else {
                drawFrame("YOU DEAD");
                return;
            }
        }
    }

}
