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
    private int depth, N;   // depth of the tiles, num of tiles in depth.
    private double tileWidth, tileHeight;
    private Point ulP, lrP; // upper left coordinate and lower right coordinate of query box.

    public Rasterer() {
        depth = 0;
        N = 1;
        ulP = new Point(0, 0);
        lrP = new Point(0, 0);
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
        double ulLon = params.get("ullon");
        double ulLat = params.get("ullat");
        double lrLon = params.get("lrlon");
        double lrLat = params.get("lrlat");
        double w = params.get("w");

        System.out.println(params);

        // find depth
        depth = findDepth(lrLon, ulLon, w);
        N = (int) Math.pow(2, depth);
        ulP = new Point(lonLevel(ulLon), latLevel(ulLat));
        lrP = new Point(lonLevel(lrLon), latLevel(lrLat));

        Map<String, Object> results = new HashMap<>();
        results.put("depth", depth);
        results.put("render_grid", getGrid());
        results.put("raster_ul_lon", getTileUlP(ulP)[0]);
        results.put("raster_ul_lat", getTileUlP(ulP)[1]);
        results.put("raster_lr_lon", getTileLrP(lrP)[0]);
        results.put("raster_lr_lat", getTileLrP(lrP)[1]);
        results.put("query_success", true);

        return results;
    }

    /**
     * Find appropriate depth image for map.
     *
     * @return depth value.
     */
    private int findDepth(double lrLon, double ulLon, double w) {
        double qbLonDPP = lonDPP(lrLon, ulLon, w);
        double l0LonDPP = lonDPP(MapServer.ROOT_LRLON, MapServer.ROOT_ULLON, MapServer.TILE_SIZE);
        for (int i = 1; i < 8; i++) {
            double liLonDPP = l0LonDPP / Math.pow(2, i);
            if (liLonDPP <= qbLonDPP) {
                return i;
            }
        }
        return 7;
    }

    private static double lonDPP(Double rLon, Double lLon, double width) {
        return (rLon - lLon) / width;
    }

    private int lonLevel(double lon) {
        tileWidth = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / N;
        int res = (int) ((lon - MapServer.ROOT_ULLON) / tileWidth);
        return Math.min(res, N - 1);
    }

    private int latLevel(double lat) {
        tileHeight = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / N;
        int res =  (int) ((MapServer.ROOT_ULLAT - lat) / tileHeight);
        return Math.min(res, N - 1);
    }

    private double[] getTileUlP(Point p) {
        return new double[]{MapServer.ROOT_ULLON + p.x * tileWidth, MapServer.ROOT_ULLAT - p.y * tileHeight};
    }

    private double[] getTileLrP(Point p) {
        return new double[]{MapServer.ROOT_ULLON + (p.x + 1) * tileWidth, MapServer.ROOT_ULLAT - (p.y + 1) * tileHeight};
    }

    private String[][] getGrid() {
        int qbW = lrP.x - ulP.x + 1;
        int qbH = lrP.y - ulP.y + 1;
        String[][] grid = new String[qbH][qbW];

        // this for loop is a little tricky.
        for (int i = 0; i < qbW; i++) {
            for (int j = 0; j < qbH; j++) {
                String adder = "d" + depth
                        + "_x" + (i + ulP.x)
                        + "_y" + (j + ulP.y)
                        + ".png";
                grid[j][i] = adder;
            }
        }

        /* another version
        for (int i = 0; i < qbH; i++) {
            for (int j = 0; j < qbW; j++) {
                String adder = "d" + depth
                        + "_x" + (j + ulP.x)
                        + "_y" + (i + ulP.y)
                        + ".png";
                grid[i][j] = adder;
            }
        }
        */

        return grid;
    }
}
