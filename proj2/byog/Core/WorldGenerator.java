package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
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
    private int WORLD_WIDTH;
    private int WORLD_LENGTH;
    private long seed;
    public Random RANDOM;
    private TETile[][] world;
    int blockNumX;
    int blockNumY;
    public ArrayList<ArrayList<Block>> blockList;

    public WorldGenerator() {
    }

    public WorldGenerator(int WORLD_WIDTH, int WORLD_LENGTH, long seed) {
        this.WORLD_WIDTH = WORLD_WIDTH;
        this.WORLD_LENGTH = WORLD_LENGTH;
        this.world = new TETile[WORLD_LENGTH][WORLD_WIDTH];
        this.seed = seed;
        this.RANDOM = new Random(seed);
        this.blockNumX = WORLD_LENGTH / Block.BLOCK_LENGTH;
        this.blockNumY = WORLD_WIDTH / Block.BLOCK_LENGTH;

        for (int i = 0; i < WORLD_LENGTH; i++) {
            for (int j = 0; j < WORLD_WIDTH; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    public World generateWorld() {
        // Compute Block
        blockList = Block.computeBlockList(this);
        ArrayList<Room> hallWayList = HallWay.computeHallWay(this);

        // draw
        for (ArrayList<Block> b1 : blockList) {
            for (Block b2 : b1) {
                Block.drawBlock(world, b2);
            }
        }
        // compute Hallway
        HallWay.drawHallWay(world, hallWayList);

        // draw Hallway
        // conflict
        Utils.dealConflict(world);
        Utils.setDoor(world);


        Player player = computePlayer();
        drawPlayer(player);

        return new World(WORLD_LENGTH, WORLD_WIDTH, seed, world, player);
    }

    private Player computePlayer() {
        while (true) {
            int x = RandomUtils.uniform(RANDOM, 0, WORLD_LENGTH);
            int y = RandomUtils.uniform(RANDOM, 0, WORLD_WIDTH);
            if (world[x][y] == Tileset.FLOOR) {
                return new Player(new Point(x, y));
            }
        }
    }

    private void drawPlayer(Player player) {
        world[player.getPlayerPos().x][player.getPlayerPos().y] = Tileset.PLAYER;
    }
}
