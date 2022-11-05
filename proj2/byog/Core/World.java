package byog.Core;

import byog.TileEngine.TETile;

public class World {
    private int worldLength;
    private int worldWidth;
    private long seed;
    private TETile[][] initialState;
    private TETile[][] currentState;
    private Player player;

    public World(int worldLength, int worldWidth) {
        this.worldLength = worldLength;
        this.worldWidth = worldWidth;
    }

    public World() {
    }

    public World(int worldLength, int worldWidth, long SEED, TETile[][] initialState, Player player) {
        this.worldLength = worldLength;
        this.worldWidth = worldWidth;
        this.seed = SEED;
        this.initialState = initialState;
        this.currentState = initialState;
        this.player = player;
    }

    public int getWorldLength() {
        return worldLength;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public TETile[][] getInitialState() {
        return initialState;
    }

    public TETile[][] getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TETile[][] currentState) {
        this.currentState = currentState;
    }

    public void updateWorldState(TETile[][] newState) {
        this.currentState = newState;
    }
}
