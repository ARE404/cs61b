package byog.Core;

import byog.TileEngine.TETile;

import java.util.ArrayList;

public class World {
    public static final int LENGTH = 60;
    public static final int WIDTH = 30;
    public TETile[][] worldState;
    public int blockNumX;
    public int blockNumY;
    public ArrayList<ArrayList<Block>> blockList;

    public World(TETile[][] worldState) {
        this.worldState = worldState;
    }
}
