package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.sql.rowset.serial.SQLOutputImpl;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

/** This class generates a world to verify the random algorithm. */
public class MapGenerator {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static Random RANDOM;

    private static class Room {
        private static Point pos;
        private static int length;
        private static int width;
        private static final int MAX_WIDTH = 8;
        private static final int MAX_HEIGHT = 8;
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
        //Initialize
        TETile[][] world = new TETile[width][height];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.GRASS;
            }
        }

        //generate room list
        LinkedList<Room> roomL = generateRoomList();
        System.out.println(roomL);
        Room r = new Room(new Point(4, 5), 3, 4);
        roomL.add(r);
        //generate hallway list
        LinkedList<Room> hallWayL = generateHallWayList();
        //place room and hallway
        for (Room room : roomL) {
            drawRoom(world, room);
        }
        //handle overlapped

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);

        return world;
    }

    private LinkedList<Room> generateRoomList() {
        LinkedList<Room> roomList = new LinkedList<>();
        int roomNum = RandomUtils.uniform(RANDOM, 20);
        for (int i = 0; i < roomNum; ++i) {
            int posX = RandomUtils.uniform(RANDOM, WIDTH);
            int posY = RandomUtils.uniform(RANDOM, HEIGHT);
            int w = RandomUtils.uniform(RANDOM, Room.MAX_WIDTH);
            int h = RandomUtils.uniform(RANDOM, Room.MAX_HEIGHT);
            System.out.println("" + posX + posY + w + h);
            roomList.add(new Room(new Point(posX, posY), w, h));
        }
        return roomList;
    }

    private LinkedList<Room> generateHallWayList() {
        int hallwayNum = RANDOM.nextInt();
        LinkedList<Room> hallwayList = new LinkedList<>();
        for (int i = 0; i < hallwayNum; ++i) {
            int posX = RandomUtils.uniform(RANDOM, WIDTH);
            int posY = RandomUtils.uniform(RANDOM, HEIGHT);
            int w = 1;
            int h = 1;
            if (RandomUtils.bernoulli(RANDOM)) {
                h = RandomUtils.uniform(RANDOM, Room.MAX_HEIGHT);
            } else {
                w = RandomUtils.uniform(RANDOM, Room.MAX_WIDTH);
            }
            Point p = new Point(posX, posY);
            Room r = new Room(p, w, h);
            hallwayList.add(r);
        }
        return hallwayList;
    }

    private void drawRoom(TETile[][] world, Room room) {
        for (int i = 0; i < room.getWidth(); ++i) {
            for (int j = 0; j < room.getLength(); ++j) {
                if (i == 0 || j == 0) {
                    world[room.getPos().x + i][room.getPos().y + j] = Tileset.WALL;
                } else {
                    world[room.getPos().x + i][room.getPos().y + j] = Tileset.FLOOR;
                }
            }
        }
    }
}
