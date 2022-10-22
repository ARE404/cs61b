package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.xml.transform.sax.SAXTransformerFactory;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * the length of vertical direction called width.
 * the length of horizontal direction called length.
 */
/** This class generates a world to verify the random algorithm. */
public class WorldGenerator {
    public static long SEED;
    public static Random RANDOM;
    public static int WORLD_WIDTH;
    public static int WORLD_LENGTH;
    public static TETile[][] world;
    public static int blockNumX;
    public static int blockNumY;
    public static ArrayList<ArrayList<Block>> blockList;
    public WorldGenerator(long s) {
        SEED = s;
        RANDOM = new Random(s);
    }

    public TETile[][] generateWorld(int worldLength, int worldWidth) {
        // Initialize
        WORLD_WIDTH = worldWidth;
        WORLD_LENGTH = worldLength;
        world = new TETile[WORLD_LENGTH][WORLD_WIDTH];
        for (int i = 0; i < WORLD_LENGTH; i++) {
            for (int j = 0; j < WORLD_WIDTH; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        // Compute Block
        blockList = Block.computeBlockList(world);
        ArrayList<Room> hallWayList = HallWay.computeHallWay(blockList);

        // draw
        for (ArrayList<Block> b1: blockList) {
            for (Block b2: b1) {
                Block.drawBlock(world, b2);
            }
        }
        // compute Hallway
        HallWay.drawHallWay(world, hallWayList);

        // draw Hallway
        // conflict
        Utils.dealConflict(world);
        return world;
    }
}
