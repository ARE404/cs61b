package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class generates a world to verify the random algorithm.
 * routine:
 * the length of vertical direction called width.
 * the length of horizontal direction called length.
 */
public class WorldGenerator implements Serializable {
    private static long SEED;
    private static Random RANDOM;
    private static int WORLD_WIDTH;
    private static int WORLD_LENGTH;

    private static TETile[][] world;
    static int blockNumX;
    static int blockNumY;
    private static ArrayList<ArrayList<Block>> blockList;

    public WorldGenerator() {
    }

    public WorldGenerator(long s) {
        setSEED(s);
        setRANDOM(new Random(s));
    }

    public static long getSEED() {
        return SEED;
    }

    public static void setSEED(long SEED) {
        WorldGenerator.SEED = SEED;
    }

    public static Random getRANDOM() {
        return RANDOM;
    }

    public static void setRANDOM(Random RANDOM) {
        WorldGenerator.RANDOM = RANDOM;
    }

    public static int getWorldWidth() {
        return WORLD_WIDTH;
    }

    public static void setWorldWidth(int worldWidth) {
        WORLD_WIDTH = worldWidth;
    }

    public static int getWorldLength() {
        return WORLD_LENGTH;
    }

    public static void setWorldLength(int worldLength) {
        WORLD_LENGTH = worldLength;
    }

    public static TETile[][] getWorld() {
        return world;
    }

    public static void setWorld(TETile[][] world) {
        WorldGenerator.world = world;
    }

    public static int getBlockNumX() {
        return blockNumX;
    }

    public static void setBlockNumX(int blockNumX) {
        WorldGenerator.blockNumX = blockNumX;
    }

    public static int getBlockNumY() {
        return blockNumY;
    }

    public static void setBlockNumY(int blockNumY) {
        WorldGenerator.blockNumY = blockNumY;
    }

    public static ArrayList<ArrayList<Block>> getBlockList() {
        return blockList;
    }

    public static void setBlockList(ArrayList<ArrayList<Block>> blockList) {
        WorldGenerator.blockList = blockList;
    }

    public TETile[][] generateWorld(int worldLength, int worldWidth) {
        // Initialize
        setWorldWidth(worldWidth);
        setWorldLength(worldLength);
        setWorld(new TETile[getWorldLength()][getWorldWidth()]);
        for (int i = 0; i < getWorldLength(); i++) {
            for (int j = 0; j < getWorldWidth(); j++) {
                getWorld()[i][j] = Tileset.NOTHING;
            }
        }

        // Compute Block
        setBlockList(Block.computeBlockList());
        ArrayList<Room> hallWayList = HallWay.computeHallWay(getBlockList());

        // draw
        for (ArrayList<Block> b1 : getBlockList()) {
            for (Block b2 : b1) {
                Block.drawBlock(getWorld(), b2);
            }
        }
        // compute Hallway
        HallWay.drawHallWay(getWorld(), hallWayList);

        // draw Hallway
        // conflict
        Utils.dealConflict(getWorld());
        Utils.setDoor(getWorld());
        return getWorld();
    }
}
