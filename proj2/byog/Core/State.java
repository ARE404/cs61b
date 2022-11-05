package byog.Core;

import byog.TileEngine.TETile;

import java.awt.*;

/**
 * Store and manage game state.
 */
public class State {
    private int worldLength;
    private int worldWidth;
    private long seed;
    private TETile[][] initialState;
    private TETile[][] currentState;
    private Player player;

    public TETile[][] getInitialState() {
        return initialState;
    }

    public void setInitialState(TETile[][] initialState) {
        this.initialState = initialState;
    }

    public TETile[][] getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TETile[][] currentState) {
        this.currentState = currentState;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public State(TETile[][] initialState, long seed) {
        this.initialState = initialState;
        this.currentState = initialState;
        this.seed = seed;
    }

    public void updateState(TETile[][] newState) {
        this.currentState = newState;
    }

}
