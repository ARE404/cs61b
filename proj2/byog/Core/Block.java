package byog.Core;

import byog.TileEngine.TETile;

import java.awt.Point;
import java.util.ArrayList;


public class Block {
    public static final int BLOCK_LENGTH = 10;
    private final Point pos;
    private Room room;
    private boolean connected = false;

    public Block(Point pos, Room room) {
        this.pos = pos;
        this.room = room;
    }

    public static ArrayList<ArrayList<Block>> computeBlockList(WorldGenerator wg) {
        ArrayList<ArrayList<Block>> blocks = new ArrayList<>();
        for (int i = 0; i < wg.blockNumX; i++) {
            ArrayList<Block> colBlocks = new ArrayList<>();
            for (int j = 0; j < wg.blockNumY; j++) {
                Point pos = new Point(i * Block.BLOCK_LENGTH, j * Block.BLOCK_LENGTH);
                Block newBlock = new Block(pos, null);
                Room newRoom = Room.generateRoom(newBlock, wg.RANDOM);
                if (newRoom != null && newRoom.outOfBoundary()) {
                    newRoom = null;
                }
                newBlock.setRoom(newRoom);
                colBlocks.add(newBlock);
            }
            blocks.add(colBlocks);
        }
        return blocks;
    }

    public static void drawBlock(TETile[][] world, Block block) {
        if (!block.hasRoom() || !block.isConnected()) {
            return;
        }
        Room.drawRoom(world, block.getRoom());
    }

    public boolean hasRoom() {
        return room != null;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Point getPos() {
        return pos;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Block{"
                + "pos=" + pos
                + ", room=" + room
                + ", connected=" + connected
                + '}';
    }
}
