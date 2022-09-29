package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.sql.rowset.serial.SQLOutputImpl;
import java.awt.*;
import java.util.IllegalFormatWidthException;
import java.util.LinkedList;
import java.util.Random;

/** This class generates a world to verify the random algorithm. */
public class MapGenerator {
    private static Random RANDOM;
    private static int WIDTH;
    private static int HEIGHT;
    private static final int BLOCK_WIDTH = 10;
    private static final int BLOCK_HEIGHT = 10;
    private LinkedList<Point> blockPointList;
    private static class Room {
        private Point pos;
        private int length;
        private int width;
        private static final int MAX_WIDTH = 12;
        private static final int MIN_WIDTH = 4;
        private static final int MAX_HEIGHT = 12;
        private static final int MIN_HEIGHT = 4;
        private static final int ROOM_NUM = 50;
        private static Random random;

        public Room() {

        }

        public Room(Point p, int l, int w) {
            pos = p;
            length = l;
            width = w;
        }

        public Point getPos() {
            return pos;
        }

        public int getLength() {
            return length;
        }

        public int getWidth() {
            return width;
        }
    }


    public MapGenerator() {
    }

    public MapGenerator(long s) {
        RANDOM = new Random(s);
    }

    public TETile[][] generateMap(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        //Initialize
        TETile[][] world = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.GRASS;
            }
        }

        blockPointList = computeBlockPoint(world);
        for (Point p: blockPointList) {
            drawBlock(world, p);
        }

        removePublicWall(world);

//
//        //generate and draw hallway
//        int hallwayNum = RANDOM.nextInt();
//        for (int i = 0; i < hallwayNum; ++i) {
//            int posX = RandomUtils.uniform(RANDOM, width);
//            int posY = RandomUtils.uniform(RANDOM, height);
//            int w = 1;
//            int h = 1;
//            if (RandomUtils.bernoulli(RANDOM)) {
//                h = RandomUtils.uniform(RANDOM, Room.MAX_HEIGHT);
//            } else {
//                w = RandomUtils.uniform(RANDOM, Room.MAX_WIDTH);
//            }
//            Point p = new Point(posX, posY);
//            Room r = new Room(p, w, h);
//            drawRoom(world, r);
//        }

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(world);

        return world;
    }

//    private LinkedList<Room> generateHallWayList() {
//        int hallwayNum = RANDOM.nextInt();
//        LinkedList<Room> hallwayList = new LinkedList<>();
//        for (int i = 0; i < hallwayNum; ++i) {
//            int posX = RandomUtils.uniform(RANDOM, );
//            int posY = RandomUtils.uniform(RANDOM, HEIGHT);
//            int w = 1;
//            int h = 1;
//            if (RandomUtils.bernoulli(RANDOM)) {
//                h = RandomUtils.uniform(RANDOM, Room.MAX_HEIGHT);
//            } else {
//                w = RandomUtils.uniform(RANDOM, Room.MAX_WIDTH);
//            }
//            Point p = new Point(posX, posY);
//            Room r = new Room(p, w, h);
//            hallwayList.add(r);
//        }
//        return hallwayList;
//    }

    private void drawRoom(TETile[][] world, Room room) {
        int BottomLeftX = room.getPos().x;
        int BottomLeftY = room.getPos().y;
        int width = room.getWidth();
        int length = room.getLength();
        int topRightX = BottomLeftX + width;
        int topRightY = BottomLeftY + length;
        if (topRightX >= WIDTH || topRightY >= HEIGHT || BottomLeftX < 0 || BottomLeftY < 0) {
            return;
        }
        System.out.println(BottomLeftX + " " + BottomLeftY);
        for (int i = 0; i < room.getWidth(); ++i) {
            for (int j = 0; j < room.getLength(); ++j) {
                int posX = BottomLeftX + i;
                int posY = BottomLeftY + j;
                boolean roomEdge = (i == 0 || j == 0 || i == width - 1 || j == length - 1);
                // place wall
//                if (roomEdge) {
//                    boolean mapEdge = (posX == 0 || posY == 0 || posX == WIDTH - 1 || posY == WIDTH - 1);
//                    boolean publicWall = false;
//                    // wall conflict
//                    // not map edge, consider public wall
//                    if (!mapEdge) {
//                        boolean isWall = world[posX][posY].equals(Tileset.WALL);
//                        boolean horizontalPublicWall = world[posX][posY - 1].equals(Tileset.FLOOR)
//                                && world[posX][posY + 1].equals(Tileset.FLOOR);
//                        boolean verticalPublicWall = world[posX - 1][posY].equals(Tileset.FLOOR)
//                                && world[posX + 1][posY].equals(Tileset.FLOOR);
//                        publicWall = isWall && (horizontalPublicWall || verticalPublicWall);
//                        if (publicWall) {
//                            world[posX][posY] = Tileset.FLOOR;
//                        }
//                    }
//                    // if previous tile is floor, do nothing
//                    if (!publicWall && !world[posX][posY].equals(Tileset.FLOOR)) {
//                        world[posX][posY] = Tileset.WALL;
//                    }
//                } else {
//                    world[posX][posY] = Tileset.FLOOR;
//                }
//                boolean corner = (i == 0 && j == 0)
//                        || (i == 0 && j == length - 1)
//                        || (i == width - 1 && j == 0)
//                        || (i == width - 1 && j == length - 1);
//                boolean publicRow =

                // at edge should draw a wall
                if (roomEdge) {
                    world[posX][posY] = Tileset.WALL;
                } else {
                    world[posX][posY] = Tileset.FLOOR;
                }
            }
        }
    }

    private LinkedList<Point> computeBlockPoint(TETile[][] world) {
        LinkedList<Point> points = new LinkedList<>();
        int pieceNumX = WIDTH / BLOCK_WIDTH;
        int pieceNumY = HEIGHT / BLOCK_HEIGHT;
        for (int i = 0; i < pieceNumX; i++) {
            for (int j = 0; j < pieceNumY; j++) {
                points.add(new Point(i * BLOCK_WIDTH, j * BLOCK_HEIGHT));
                world[i * BLOCK_WIDTH][j * BLOCK_HEIGHT] = Tileset.TREE;
            }
        }
        System.out.println(points);
        return points;
    }

    private void drawBlock(TETile[][] world, Point point) {
        //draw a room in this block with probability 0.7
        if (RandomUtils.bernoulli(RANDOM, 0.9)) {
            //0.7 chance point locate in left down corner
            int posX;
            int posY;
            if (RandomUtils.bernoulli(RANDOM, 0.7)) {
                posX = point.x + RandomUtils.uniform(RANDOM, BLOCK_WIDTH / 2);
                posY = point.y + RandomUtils.uniform(RANDOM, BLOCK_HEIGHT / 2);
            } else {
                posX = point.x + RandomUtils.uniform(RANDOM, BLOCK_WIDTH);
                posY = point.y + RandomUtils.uniform(RANDOM, BLOCK_HEIGHT);
            }

            int width = RandomUtils.uniform(RANDOM, Room.MIN_WIDTH, Room.MAX_WIDTH);
            int height = RandomUtils.uniform(RANDOM, Room.MIN_HEIGHT, Room.MAX_HEIGHT);
            drawRoom(world, new Room(new Point(posX, posY), width, height));
        }
    }

    private void removePublicWall(TETile[][] world) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                boolean isMapEdge = (i == 0 || j == 0 || i == WIDTH - 1 || j == WIDTH - 1);
                if (!isMapEdge) {
                    boolean isWall = world[i][j].equals(Tileset.WALL);
                    boolean horizontalPublicWall = world[i][j - 1].equals(Tileset.FLOOR)
                            && world[i][j + 1].equals(Tileset.FLOOR);
                    boolean verticalPublicWall = world[i - 1][j].equals(Tileset.FLOOR)
                            && world[i + 1][j].equals(Tileset.FLOOR);
                    boolean publicWall = horizontalPublicWall || verticalPublicWall;
                    if (isWall && publicWall) {
                        world[i][j] = Tileset.FLOOR;
                    }

                }
            }

        }
    }
}
