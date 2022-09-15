package byog.lab5;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    private static final long SEED = 21234789;
    private static final Random RANDOM = new Random(SEED);
    private static final int EXTEND_TIMES = 3;

    /**
     * Generate a random tile.
     */
    private TETile randomTile() {
        int tileNum = RANDOM.nextInt(8);
        return switch (tileNum) {
            case 0 -> Tileset.FLOWER;
            case 1 -> Tileset.GRASS;
            case 2 -> Tileset.FLOOR;
            case 3 -> Tileset.MOUNTAIN;
            case 4 -> Tileset.SAND;
            case 5 -> Tileset.WALL;
            case 6 -> Tileset.WATER;
            case 7 -> Tileset.TREE;
            default -> Tileset.NOTHING;
        };
    }

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
        drawDownside(length, blX, blY, world, tile);
        drawUpside(length, trX, trY, world, tile);
    }

    /** Default use Nothing Tile.   */
    public void addHexagon(int length, int blX, int blY, TETile[][] world) {
        addHexagon(length, blX, blY, world, randomTile());
    }

    /** Recursively draw a hexagon shaped multiple Hexagon.
     *
     * @param length    side length.
     * @param blX       bottom left x.
     * @param blY       bottom left y.
     * @param world     world we draw in.
     * @param tile      tile we use.
     */
    public void addMultiHexagon(int length, int blX, int blY, TETile[][] world, TETile tile) {
        multiHexagonHelper(length, blX, blY, world, tile, EXTEND_TIMES);
    }

    public void addMultiHexagon(int length, int blX, int blY, TETile[][] world) {
        addMultiHexagon(length, blX, blY, world, randomTile());
    }

    private void multiHexagonHelper(int length, int blX, int blY, TETile[][] world, TETile tile, int extendTimes) {
        if (extendTimes < 0) {
            return;
        }
        //try to draw hexagon in all six direction if that pos has no hexagon before
        multiHexagonHelper(length, blX, blY + 2 * length, world, tile, extendTimes - 1);
        multiHexagonHelper(length, blX, blY - 2 * length, world, tile, extendTimes - 1);
        multiHexagonHelper(length, blX + 2 * length - 1, blY + length, world, tile, extendTimes - 1);
        multiHexagonHelper(length, blX + 2 * length - 1, blY - length, world, tile, extendTimes - 1);
        multiHexagonHelper(length, blX - 2 * length + 1, blY + length, world, tile, extendTimes - 1);
        multiHexagonHelper(length, blX - 2 * length + 1, blY - length, world, tile, extendTimes - 1);
        //draw a hexagon in current pos
        addHexagon(length, blX, blY, world);
    }

    /**
     * Draw upside of the hexagon.
     *
     * @param length    side length of hexagon.
     * @param trX       top right x coordinate.
     * @param trY       top right y coordinate.
     * @param world     the world we need to draw.
     */
    private static void drawUpside(int length, int trX, int trY, TETile[][] world, TETile tile) {
        int dx = length - 1;
        int startX = trX - dx;
        int endX = startX - length;
        //two for loop, use line by line scan method
        for (int i = trY; i > trY - length; i -= 1) {
            for (int j = startX; j > endX; j -= 1) {
                world[j][i] = tile;
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
    private static void drawDownside(int length, int blX, int blY, TETile[][] world, TETile tile) {
        int dx = length - 1;
        int startX = blX + dx;
        int endX = startX + length;
        //two for loop, use line scan
        for (int i = blY; i < blY + length; i += 1) {
            for (int j = startX; j < endX; j += 1) {
                world[j][i] = tile;
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

        //  make a HexWorld
        HexWorld w = new HexWorld();
        w.addMultiHexagon(3, 25, 28, world);

        //  draws the world to the screen
        ter.renderFrame(world);
    }
}
