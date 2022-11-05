package byog.Core;

import byog.TileEngine.Tileset;

import java.awt.*;

public class Player {
    private Point playerPos;

    public Player(Point playerPos) {
        this.playerPos = playerPos;
    }

    public Point getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(Point playerPos) {
        this.playerPos = playerPos;
    }

    public void movePlayer(World world, char direction) {
        Point newPos = playerPos;

        switch (direction) {
            case 'w':
                newPos.translate(0, 1);
            case 's':
                newPos.translate(0, -1);
            case 'a':
                newPos.translate(-1, 0);
            case 'd':
                newPos.translate(1, 0);
        }

        if (world.getCurrentState()[newPos.x][newPos.y] == Tileset.FLOOR) {
            playerPos = newPos;
        }
    }
}
