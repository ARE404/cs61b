package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.Point;
import java.util.Random;

public class Room {
    private static final double ROOM_GENERATE_FACTOR = 0.8;
    private Point roomPos;
    private Point roomTopRightPos;
    private int length;
    private int width;

    public Room() {
    }

    public Room(Point roomPos, int length, int width) {
        this.roomPos = roomPos;
        this.length = length;
        this.width = width;
        this.roomTopRightPos = new Point(roomPos.x + length, roomPos.y + width);
    }

    public static Room generateRoom(Block block, Random random) {
        if (RandomUtils.bernoulli(random, ROOM_GENERATE_FACTOR)) {
            int posX, posY, width, length;
            Point blockPos = block.getPos();
            // determine position
            posX = blockPos.x + RandomUtils.uniform(random, Block.BLOCK_LENGTH / 2);
            posY = blockPos.y + RandomUtils.uniform(random, Block.BLOCK_LENGTH / 2);
            width = RandomUtils.uniform(random, Block.BLOCK_LENGTH / 2, Block.BLOCK_LENGTH);
            length = RandomUtils.uniform(random, Block.BLOCK_LENGTH / 2, Block.BLOCK_LENGTH);
            return new Room(new Point(posX, posY), width, length);
        } else {
            return null;
        }
    }

    public static void drawRoom(TETile[][] world, Room room) {
        Utils.drawSquare(world, room.getRoomPos(), room.getWidth(), room.getLength(), Tileset.WALL);
    }

    public Point getRoomPos() {
        return roomPos;
    }

    public void setRoomPos(Point roomPos) {
        this.roomPos = roomPos;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Point getRoomTopRightPos() {
        return roomTopRightPos;
    }

    @Override
    public String toString() {
        return "Room{"
                + "roomPos=" + roomPos
                + ", roomTopRightPos=" + roomTopRightPos
                + ", length=" + length
                + ", width=" + width
                + '}';
    }

    public boolean outOfBoundary() {
        return roomPos.x + length >= WorldGenerator.getWorldLength()
               || roomPos.y + width >= WorldGenerator.getWorldWidth();
    }
}
