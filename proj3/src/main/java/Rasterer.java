import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double ullon, ullat, lrlon, lrlat, w;
    private int depth;   // depth of the tiles.
    private int N;          // num of tiles in one side.
    private double mapHeight, mapWidth;
    private double tileHeight, tileWidth;
    private Point ulP, lrP;

    public Rasterer() {
        // YOUR CODE HERE
        depth = 0;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        ullon = params.get("ullon");
        ullat = params.get("ullat");
        lrlon = params.get("lrlon");
        lrlat = params.get("lrlat");
        w = params.get("w");
        mapHeight = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        mapWidth = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;

        System.out.println(params);


        // find appropriate scale level
        depth = findDepth();
        N = (int) Math.pow(2, depth);
        tileHeight = mapHeight / N;
        tileWidth = mapWidth / N;
        ulPlrP();


        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", getGrid());
        results.put("raster_ul_lon", MapServer.ROOT_ULLON + ulP.x * tileWidth);
        results.put("raster_ul_lat", MapServer.ROOT_ULLAT - ulP.y * tileHeight);
        results.put("raster_lr_lon", MapServer.ROOT_ULLON + (lrP.x + 1) * tileWidth);
        results.put("raster_lr_lat", MapServer.ROOT_ULLAT - (lrP.y + 1) * tileHeight);
        results.put("depth", depth);
        results.put("query_success", true);
        System.out.println("test");
        return results;
    }

    /**
     * Find appropriate depth image for map.
     *
     * @return depth value.
     */
    private int findDepth() {
        double qbLonDPP = lonDPP(lrlon, ullon, w);
        double l0LonDPP = lonDPP(MapServer.ROOT_LRLON, MapServer.ROOT_ULLON, MapServer.TILE_SIZE);

        int res = 7;
        for (int i = 1; i < 8; i++) {
            double lLonDPP = l0LonDPP / Math.pow(2, i);
            if (lLonDPP <= qbLonDPP) {
                return i;
            }
        }
        return res;
    }

    /**
     * LonDPP calculator.
     */
    private static double lonDPP(Double rLon, Double lLon, double width) {
        return (rLon - lLon) / width;
    }

    /**
     * return level of i.
     */
    private int lonLevel(double lon) {
        double length = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / N;
        return level(lon - MapServer.ROOT_ULLON, length);
    }

    private int latLevel(double lat) {
        double length = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / N;
        return level(MapServer.ROOT_ULLAT - lat, length);
    }

    private int level(double value, double length) {
        int res = 0;
        for (int i = 0; i < N; i++) {
            if (value <= length * (i + 1)) {
                res = i;
                break;
            }
        }
        return res;
    }

    /**
     * set ulP and lrP.
     */
    private void ulPlrP() {
        // ulP
        ulP = new Point(lonLevel(ullon), latLevel(ullat));
        lrP = new Point(lonLevel(lrlon), latLevel(lrlat));
    }

    private String[][] getGrid() {
        int boundWidth = lrP.x - ulP.x + 1;
        int boundHeight  = lrP.y - ulP.y + 1;
        String[][] grid = new String[boundHeight][boundWidth];
        for (int i = 0; i < boundHeight; i++) {
            for (int j = 0; j < boundWidth; j++) {
                String adder = "d" + depth + "_x" + (j + ulP.x) + "_y" + (i + ulP.y) + ".png";
                grid[i][j] = adder;
            }
        }
        return grid;
    }
}
