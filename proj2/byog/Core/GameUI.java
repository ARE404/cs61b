package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.HashMap;

/**
 * Draw UI interface with a HUD and a world
 */
public class GameUI {
    private final int width;
    private final int length;
    private final HashMap<String, Font> fontDic;

    public GameUI(int length, int width) {
        this.width = width;
        this.length = length;
        this.fontDic = new HashMap<>();
        fontDic.put("MonacoBold30", new Font("Monaco", Font.BOLD, 30));
        fontDic.put("MonacoBold24", new Font("Monaco", Font.BOLD, 24));
        StdDraw.setCanvasSize(this.length * 16, this.width * 16);
        StdDraw.setFont(fontDic.get("MonacoBold24"));
        StdDraw.setXscale(0, this.length);
        StdDraw.setYscale(0, this.width);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        TERenderer ter = new TERenderer();
        ter.initialize();
    }

    public void drawStartMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(fontDic.get("MonacoBold30"));
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(length / 2., width / 2. + 4, "CS61B: BYOG");
        StdDraw.setFont(fontDic.get("MonacoBold24"));
        StdDraw.text(length / 2., width / 2. - 2, "(N) new game");
        StdDraw.text(length / 2., width / 2. - 4, "(L) load game");
        StdDraw.text(length / 2., width / 2. - 6, "(Q) quit game");
        StdDraw.show();
    }

    public void drawHUD(World world) {
        drawMousePointAt(world.getCurrentState());
        StdDraw.show();
    }

    public void drawGraphic(World world, TERenderer ter) {
        StdDraw.clear(Color.BLACK);
        drawHUD(world);
        ter.renderFrame(world.getCurrentState());
        StdDraw.show();
    }

    public void drawMousePointAt(TETile[][] world) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(fontDic.get("MonacoBold24"));
        StdDraw.setPenColor(Color.WHITE);
        int mouseX = ((int) StdDraw.mouseX()) % 16;
        int mouseY = ((int) StdDraw.mouseY()) % 16;
        String mousePointAt = world[mouseX][mouseY].toString();
        StdDraw.text(80, width - 12, mousePointAt);

    }
}
