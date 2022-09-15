package byog.lab5;

import org.junit.Test;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final int seed = 23479;

    /**
     * Draw a single unit hexagon.
     *
     * @param length Side length of the unit hexagon.
     * @param blX   x position of the bottom left corner.
     * @param blY   y position of the bottom left corner.
     * @param world  the world we draw in.
     */
    public void addHexagon(int length, int blX, int blY, TETile[][] world, TETile tile) {
        int trX = blX + length * 2 + (length - 2) - 1;  //bottom right x coordinate.
        int trY = 2 * length - 1 + blY;            //bottom right y coordinate.
        //check if the Hexagon beyond the window
        if (trX >= WIDTH || trY >= HEIGHT || length <= 0) {
            return;
        }
        drawDownside(length, blX, blY, world);
        drawUpside(length, trX, trY, world);
    }

    /** Default use Nothing Tile.   */
    public void addHexagon(int length, int blX, int blY, TETile[][] world) {
        addHexagon(length, blX, blY, world, Tileset.NOTHING);
    }


    /**
     * Draw upside of the hexagon.
     *
     * @param length side length of hexagon.
     * @param trX   top right x coordinate.
     * @param trY   top right y coordinate.
     * @param world  1   the world we need to draw.
     */
    private static void drawUpside(int length, int trX, int trY, TETile[][] world) {
        int dx = length - 1;
        int startX = trX - dx;
        int endX = startX - length;
        //two for loop, use line by line scan method
        for (int i = trY; i > trY - length; i -= 1) {
            for (int j = startX; j > endX; j -= 1) {
                world[j][i] = Tileset.WALL;
            }
            startX += 1;
            endX -= 1;
        }
    }

    /**
     * Method used for testing DrawUpside method.
     */
    public void testDrawUpside(int length, int trX, int trY) {
        int dx = length - 1;
        int startX = trX - dx;
        int endX = startX - length;
        //two for loop, use line scan
        for (int i = trY; i > trY - length; i -= 1) {
            for (int j = startX; j > endX; j -= 1) {
                System.out.println(i + "," + j + " ");
            }
            startX += 1;
            endX -= 1;
        }
    }

    /**
     * Draw downside of the hexagon.
     *
     * @param length the side length.
     * @param blX   bottom left x coordinate.
     * @param blY   bottom left y coordinate.
     * @param world  the world need to draw.
     */
    private static void drawDownside(int length, int blX, int blY, TETile[][] world) {
        int dx = length - 1;
        int startX = blX + dx;
        int endX = startX + length;
        //two for loop, use line scan
        for (int i = blY; i < blY + length; i += 1) {
            for (int j = startX; j < endX; j += 1) {
                world[j][i] = Tileset.WALL;
            }
            startX -= 1;
            endX += 1;
        }
    }

    /**
     * Method used for testing DrawDownside method.
     */
    public void testDrawDownside(int length, int blX, int blY) {
        int dx = length - 1;
        int startX = blX + dx;
        int endX = startX + length;
        //two for loop, use line scan
        for (int i = blY; i < blY + length; i += 1) {
            for (int j = startX; j < endX; j += 1) {
                System.out.println(i + "," + j + " ");
            }
            startX -= 1;
            endX += 1;
        }
    }

    public static void main(String[] args) {
        // initialize rendering engine
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        HexWorld w = new HexWorld();
        int hexLength = 3;
        w.addHexagon(hexLength, 0, 0, world);

        //  draws the world to the screen
        ter.renderFrame(world);
    }
}
