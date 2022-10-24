package byog.Core;

import byog.TileEngine.TETile;

import java.awt.*;
import java.util.ArrayList;

import static byog.Core.WorldGenerator.blockNumX;
import static byog.Core.WorldGenerator.blockNumY;

public class Block {
    public static final int BLOCK_LENGTH = 10;
    private final Point pos;
    private Room room;
    private boolean connected = false;

    public Block(Point pos, Room room) {
        this.pos = pos;
        this.room = room;
    }

    public static ArrayList<ArrayList<Block>> computeBlockList() {
        blockNumX = WorldGenerator.getWorldLength() / Block.BLOCK_LENGTH;
        blockNumY = WorldGenerator.getWorldWidth() / Block.BLOCK_LENGTH;

        ArrayList<ArrayList<Block>> blocks = new ArrayList<>();
        for (int i = 0; i < blockNumX; i++) {
            ArrayList<Block> colBlocks = new ArrayList<>();
            for (int j = 0; j < blockNumY; j++) {
                Point pos = new Point(i * Block.BLOCK_LENGTH, j * Block.BLOCK_LENGTH);
                Block newBlock = new Block(pos, null);
                Room newRoom = Room.generateRoom(newBlock, WorldGenerator.getRANDOM());
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
