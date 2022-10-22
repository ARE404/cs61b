package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
    public static void drawLine(TETile[][] world, Point startPos, boolean vertical, int length, TETile set) {
        int startX = startPos.x;
        int startY = startPos.y;
        if (vertical) {
            for (int i = 0; i <= length; ++i) {
                world[startX][startY + i] = set;
            }
        } else {
            for (int i = 0; i <= length; ++i) {
                world[startX + i][startY] = set;
            }
        }
    }

    public static void drawSquare(TETile[][] world, Point bottomLeftPos, int width, int length, TETile set) {
        int leftX = bottomLeftPos.x;
        int bottomY = bottomLeftPos.y;
        int rightX = leftX + length;
        int topY = bottomY + width;

        if (rightX >= WorldGenerator.WORLD_LENGTH || topY >= WorldGenerator.WORLD_WIDTH) {
            return;
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                world[leftX + i][bottomY + j] = Tileset.FLOOR;
            }
        }

        Point bottomRightPos = new Point(rightX, bottomY);
        Point topLeftPos = new Point(leftX, topY);

        drawLine(world, bottomLeftPos, false, length, set);
        drawLine(world, topLeftPos, false, length, set);
        drawLine(world, bottomLeftPos, true, width, set);
        drawLine(world, bottomRightPos, true, width, set);
    }

    public static void dealConflict(TETile[][] world) {
        removePublicWall(world);
        removeIndividualWall(world);
    }

    public static void removePublicWall(TETile[][] world) {
        for (int i = 0; i < WorldGenerator.WORLD_LENGTH; i++) {
            for (int j = 0; j < WorldGenerator.WORLD_WIDTH; j++) {
                boolean isMapEdge = (i == 0 || j == 0 || i == WorldGenerator.WORLD_LENGTH - 1 || j == WorldGenerator.WORLD_WIDTH - 1);
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

    public static void removeIndividualWall(TETile[][] world) {
        for (int i = 0; i < WorldGenerator.WORLD_LENGTH; i++) {
            for (int j = 0; j < WorldGenerator.WORLD_WIDTH; j++) {
                boolean isMapEdge = (i == 0 || j == 0 || i == WorldGenerator.WORLD_LENGTH - 1 || j == WorldGenerator.WORLD_WIDTH - 1);
                if (!isMapEdge) {
                    boolean isWall = world[i][j].equals(Tileset.WALL);
                    boolean horizontalPublicWall = world[i][j - 1].equals(Tileset.FLOOR)
                            && world[i][j + 1].equals(Tileset.FLOOR);
                    boolean verticalPublicWall = world[i - 1][j].equals(Tileset.FLOOR)
                            && world[i + 1][j].equals(Tileset.FLOOR);
                    boolean individualWall = horizontalPublicWall && verticalPublicWall;
                    if (isWall && individualWall) {
                        world[i][j] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    public void saveGame() throws IOException {
        // try creating file
        File f = new File("savedGame.txt");
        f.createNewFile();
        // write to file
        FileWriter writer = new FileWriter("savedGame.txt");
        String savedWorld = WorldGenerator.world.toString();
        writer.write(savedWorld);
        writer.close();
    }

    public void loadGame() {

    }

    public void endGame() {
        System.exit(0);
    }
}
