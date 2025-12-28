import java.awt.image.BandedSampleModel;
import java.util.Objects;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.Point;
import java.util.*; //
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable {
    private int w;
    private int h;
    private int v;
    private int[][] map;

    /**
     * Constructs a w*h 2D raster map with an init value v.
     *
     * @param w
     * @param h
     * @param v
     */
    public Map(int w, int h, int v) {
        init(w, h, v);
    }

    /**
     * Constructs a square map (size*size).
     *
     * @param size
     */
    public Map(int size) {
        this(size, size, 0);
    }

    /**
     * Constructs a map from a given 2D array.
     *
     * @param data
     */
    public Map(int[][] data) {
        init(data);


    }

    @Override
    public void init(int w, int h, int v) {
        this.w = w;
        this.h = h;
        map = new int[w][h];
        if (w <= 0 || h <= 0) {
            throw new IllegalArgumentException("Invalid width or height parameter");
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                this.map[i][j] = v;
            }
        }

    }

    @Override
    public void init(int[][] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Invalid array parameter");
        }
        this.w = arr.length;
        this.h = arr[0].length;
        this.map = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                map[i][j] = arr[i][j];
            }
        }

    }

    @Override
    public int[][] getMap() {
        return map;
    }

    @Override
    public int getWidth() {
        return this.w;
    }

    @Override
    public int getHeight() {
        return this.h;
    }

    @Override
    public int getPixel(int x, int y) {
        int ans = -1;
        if (0 <= x && x < this.w && 0 <= y && y < this.h) {
            ans = this.map[x][y];
            return ans;
        }
        return ans;
    }

    @Override
    public int getPixel(Pixel2D p) {
        int ans = -1;
        if (p == null) {
            return ans;
        }
        int x = p.getX();
        int y = p.getY();
        if (0 <= x && x < this.w && 0 <= y && y < this.h) {
            ans = this.map[x][y];
            return ans;
        }
        return ans;
    }

    @Override
    public void setPixel(int x, int y, int v) {
        if (0 <= x && x < this.w && 0 <= y && y < this.h) {
            this.map[x][y] = v;
        }
    }

    @Override
    public void setPixel(Pixel2D p, int v) {
        if (p != null) {
            int x = p.getX();
            int y = p.getY();
            if (0 <= x && x < this.w && 0 <= y && y < this.h) {
                this.map[x][y] = v;
            }
        }
    }

    @Override
    public boolean isInside(Pixel2D p) {
        boolean ans = true;
        int x = p.getX();
        int y = p.getY();
        if (0 <= x && x < this.w && 0 <= y && y < this.h) {
            return ans;
        } else {
            ans = false;
        }
        return ans;
    }

    @Override
    public boolean sameDimensions(Map2D p) {
        boolean ans = true;
        if (p.getWidth() != w || p.getHeight() != h) {
            return false;
        }
        return ans;
    }

    @Override
    public void addMap2D(Map2D p) {
        if (p != null) {
            if (this.sameDimensions(p)) {
                for (int i = 0; i < this.w; i++) {
                    for (int j = 0; j < this.h; j++) {
                        this.map[i][j] = this.map[i][j] + p.getPixel(i, j);
                    }
                }
            }
        }
    }


    @Override
    public void mul(double scalar) {
        double s = scalar;
        for (int i = 0; i < this.w; i++) {
            for (int j = 0; j < this.h; j++) {
                this.map[i][j] *= s;
            }
        }
    }

    @Override
    public void rescale(double sx, double sy) {
        int new_h = (int) (this.getHeight() * sy);
        int new_w = (int) (this.getWidth() * sx);
        int[][] new_map = new int[new_w][new_h];
        for (int i = 0; i < new_w; i++) {
            for (int j = 0; j < new_h; j++) {
                int old_w = (int) (i / sx), old_h = (int) (j / sy);
                new_map[i][j] = this.getPixel(old_w, old_h);
            }
        }
        this.init(new_map);
    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        double r = rad;
        int startX = (int) (center.getX() - r); // go over only about the bounding box.
        int endX = (int) (center.getX() + r);// and both p1 and p2 are within this map
        int startY = (int) (center.getY() - r);
        int endY = (int) (center.getY() + r);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                double DeltaX = Math.abs(i - center.getX());
                double DeltaY = Math.abs(j - center.getY());
                double sum = Math.pow(DeltaX, 2) + Math.pow(DeltaY, 2);
                if (sum <= r * r) {
                    this.map[i][j] = color;
                }
            }

        }
    }


    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();

        int Xmax = Math.max(p1.getX(), p2.getX());
        int Xmin = Math.min(p1.getX(), p2.getX());
        int Ymax = Math.max(p1.getY(), p2.getY());
        int Ymin = Math.min(p1.getY(), p2.getY());

        if (dx == 0) {
            for (int i = Ymin; i <= Ymax; i++) {
                map[p1.getX()][i] = color;
            }
            return;
        }
        double m = dy / dx;
        double b = p1.getY() - m * p1.getX();

        if (Math.abs(dx) <= Math.abs(dy)) {
            for (int i = Ymin; i <= Ymax; i++) {
                int x = (int) Math.round((i - b) / m);
                map[x][i] = color;
            }
        } else {
            for (int i = Xmin; i <= Xmax; i++) {
                int y = (int) Math.round(m * i + b);
                map[i][y] = color;
            }
        }
    }


    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int Newcolor) {
        int startX = Math.min(p1.getX(), p2.getX());
        int endX = Math.max(p1.getX(), p2.getX());
        int startY = Math.min(p1.getY(), p2.getY());
        int endY = Math.max(p1.getY(), p2.getY());
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                this.map[i][j] = Newcolor;
            }
        }
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false;
        boolean ans = false;
        if (this == ob) return true;
        if (!(ob instanceof Map2D)) return false;// check same object
        Map2D m = (Map2D) ob;
        if (!(this.sameDimensions((m)))) return false;  // same dim
        for (int i = 0; i < this.w; i++) {
            for (int j = 0; j < this.h; j++) {
                if (getPixel(i, j) != m.getPixel(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    /**
     * Fills the connected component of a given pixel with a new color value.
     * This method implements a "Flood Fill" algorithm using a Breadth-First Search (BFS)
     * traversal.
     * It identifies all pixels belonging to the same connected component as the starting point and updates their values.
     * Methodology:
     * 1. Determines the original color of the starting pixel 'p'.
     * All pixels sharing this color and connected to 'p' will be modified.
     * 2. Uses a Queue to explore the grid. Two pixels are considered
     * connected if there is a path between them consisting entirely of the original color.
     * 3. If 'cyclic' is true, the algorithm treats the map as a toroid, where connections wrap around the edges.
     * - Otherwise, standard grid boundaries are applied.
     * 4. Population Count: Tracks and returns the total number of pixels that were successfully updated to the new color.
     * 5. Redundancy Check: If the 'new_v' is identical to the original color, the
     * method returns 0 to prevent infinite loops or unnecessary processing.
     * @param p     The starting pixel coordinates for the fill operation.
     * @param new_v The new color value to be applied to the connected component.
     * @param cyclic Determines if the connectivity should wrap around the map boundaries.
     * @return      The total number of pixels whose color was changed.
     */
    public int fill(Pixel2D xy, int new_v, boolean cyclic) {
        int old = getPixel(xy.getX(), xy.getY());
        int count = 1;
        Queue<Pixel2D> q = new LinkedList<>();
        q.add(xy);
        setPixel(xy, new_v);
        while (!q.isEmpty()) {
            Pixel2D p = q.poll();
            int x = p.getX();
            int y = p.getY();

            int nextX = x + 1;
            int nextY = y;
            p = getCyclicPosition(nextX, nextY, cyclic);
            nextX = p.getX();
            nextY = p.getY();
            if (nextX < this.w) {
                if (getPixel(nextX, nextY) == old) {
                    setPixel(nextX, nextY, new_v);
                    q.add(p);
                    count++;
                }
            }
            int nextX2 = x - 1, nextY2 = y;
            p = getCyclicPosition(nextX2, nextY2, cyclic);
            nextX2 = p.getX();
            nextY2 = p.getY();
            if (0 <= nextX2 && nextX2 < this.w) {
                if (getPixel(nextX2, nextY2) == old) {
                    setPixel(nextX2, nextY2, new_v);
                    q.add(p);
                    count++;
                }
            }
            int nextX3 = x, nextY3 = y + 1;
            p = getCyclicPosition(nextX3, nextY3, cyclic);
            nextX3 = p.getX();
            nextY3 = p.getY();
            if (nextY3 < this.h) {
                if (getPixel(nextX3, nextY3) == old) {
                    setPixel(nextX3, nextY3, new_v);
                    q.add(p);
                    count++;
                }
            }
            int nextX4 = x, nextY4 = y - 1;
            p = getCyclicPosition(nextX4, nextY4, cyclic);
            nextX4 = p.getX();
            nextY4 = p.getY();
            if (0 <= nextY4 && nextY4 < this.w) {
                if (getPixel(nextX4, nextY4) == old) {
                    setPixel(nextX4, nextY4, new_v);
                    q.add(p);
                    count++;
                }
            }
        }
        return count;
    }


    @Override
    /**
     *Computes the shortest path between two pixels using the Breadth-First Search (BFS) algorithm.
     * The algorithm proceeds as follows:
     * 1. Pre-check: If the start and end points are identical, the method returns null as no movement is needed.
     * 2. Initialization: A Queue is used to manage the frontier of the search, and a 'parents' matrix
     * is created to track the discovery path for later reconstruction.
     * 3. Exploration: The map is traversed level-by-level. For each pixel:
     * - If the target coordinates are reached, the shortest path is reconstructed and returned.
     * - Otherwise, its four immediate neighbors are evaluated.
     * 4. Topology Handling:
     * - In 'cyclic' mode, neighbors are calculated using modulo arithmetic, allowing the path to
     * wrap around the map edges.
     * - In standard mode, standard boundaries are enforced.
     * 5. Path Validation: A neighbor is added to the queue only if it is not an obstacle, has not
     * been visited yet, and is not the starting point.
     * 6. Reconstruction: By storing the 'parent' of each pixel, the algorithm traces back from
     * the destination to the source to build the final sequence of points.
     * @param p1       Starting point (Pixel2D).
     * @param p2       Destination point (Pixel2D).
     * @param obsColor The color representing impassable obstacles.
     * @param cyclic   Determines if the map edges are connected (wrap-around).
     *@return         An ordered array of Pixel2D points forming the shortest path, or null if unreachable.
     */
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
            int width = map.length;
            int height = map[0].length;

            if (map[p1.getX()][p1.getY()] == obsColor || map[p2.getX()][p2.getY()] == obsColor) {
                return null;
            }
            Queue<Pixel2D> queue = new LinkedList<>();
            Pixel2D[][] parents = new Pixel2D[width][height];
            queue.add(p1);

            while (!queue.isEmpty()) {
                Pixel2D current = queue.poll();

                if (current.getX() == p2.getX() && current.getY() == p2.getY()) {
                    return constructPath(parents, p1, p2);
                }
                int[] dx = {0, 0, 1, -1};
                int[] dy = {1, -1, 0, 0};

                for (int i = 0; i < 4; i++) {
                    int nextX = current.getX() + dx[i];
                    int nextY = current.getY() + dy[i];

                    if (cyclic) {
                        nextX = (nextX + width) % width;
                        nextY = (nextY + height) % height;
                    } else {
                        if (nextX < 0 || nextX >= width || nextY < 0 || nextY >= height) continue;
                    }
                    Pixel2D neighbor = new Index2D(nextX, nextY) ;
                    if (map[nextX][nextY] != obsColor && parents[nextX][nextY] == null && !neighbor.equals(p1)) {
                        parents[nextX][nextY] = current;
                        queue.add(neighbor);
                    }
                }
            }
            return null;
        }

        @Override
        /**
         *Computes a distance map from a source point to all reachable pixels in the map.
         * Logic and Algorithm:
         * 1. Initialization: Creates a 2D integer array (distance matrix) of the same
         * dimensions as the map, initialized with -1 to represent unreachable cells.
         * 2. Starting Point: Sets the distance of the 'start' pixel to 0 and adds it
         * to a Queue for BFS processing.
         * 3. Breadth-First Search (BFS):
         * - The algorithm expands outwards like a "wave" from the start point.
         * - For each pixel, it examines its 4 immediate neighbors (Up, Down, Left, Right).
         * - A neighbor is processed ONLY if it is not an obstacle (matching obsColor)
         * and has not been visited (value is still -1).
         * - Valid neighbors are assigned a distance of (current_pixel_distance + 1).
         * 4. Topology (Cyclic vs. Flat):
         * - If 'cyclic' is true, the algorithm uses modulo arithmetic to "wrap around"
         * the map edges (e.g., moving right from the last column leads to the first column).
         * - If 'cyclic' is false, edges are treated as impassable boundaries.
         * @param start    The starting Pixel2D coordinates (source).
         * @param obsColor The integer value representing an obstacle pixel.
         * @param cyclic   Boolean flag; if true, enables wrap-around movement at map boundaries.
         * @return         A Map2D object where each cell contains its shortest distance
         * from the start, or -1 if the cell is an obstacle or unreachable.
         */

        public Map2D allDistance (Pixel2D start,int obsColor, boolean cyclic) {
                int w = this.getWidth();
                int h = this.getHeight();
                int[][] dists = new int[w][h];

                for (int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        dists[i][j] = -1;
                    }
                }

                if (start == null || this.getPixel(start.getX(), start.getY()) == obsColor) {
                    return new Map(dists);
                }

                Queue<Pixel2D> q = new LinkedList<>();

                dists[start.getX()][start.getY()] = 0;
                q.add(start);

                int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

                while (!q.isEmpty()) {
                    Pixel2D curr = q.poll();
                    int currDist = dists[curr.getX()][curr.getY()];

                    for (int[] d : dirs) {
                        int nextX = curr.getX() + d[0];
                        int nextY = curr.getY() + d[1];

                        if (cyclic) {
                            nextX = (nextX + w) % w;
                            nextY = (nextY + h) % h;
                        }
                        if (nextX >= 0 && nextX < w && nextY >= 0 && nextY < h) {
                            if (this.getPixel(nextX, nextY) != obsColor && dists[nextX][nextY] == -1) {
                                dists[nextX][nextY] = currDist + 1;
                                q.add(new Index2D(nextX, nextY));
                            }
                        }
                    }
                }

                return new Map(dists);
            }
        /// /////////////////// Private Methods ///////////////////////
    /**
     * Normalizes coordinates to support a cyclic (wrap-around) grid topology.
     * This helper method ensures that any coordinate exceeding the map boundaries
     * is mapped back to the opposite side using modulo arithmetic.
     * This allows for seamless movement across edges (e.g., from the last column to the first).
     * @param x
     * @param y
     * @param cyclic
     * @return
     */
        private Pixel2D getCyclicPosition ( int x, int y, boolean cyclic){
            if (cyclic) {
                if (x >= getWidth()) x = 0;
                else if (x < 0) x = getWidth() - 1;

                if (y >= getHeight()) y = 0;
                else if (y < 0) y = getHeight() - 1;
            }
            return new Index2D(x, y);
        }

    /**
     * Reconstructs the shortest path by backtracking from the destination.
     * This method traces the distance map backwards from the target to the source
     * by consistently moving to a neighboring cell with a distance value of (d - 1).
     *  Supports both standard and cyclic map topologies.
     * @param parents
     * @param start
     * @param target
     * @return
     */
    private Pixel2D[] constructPath(Pixel2D[][] parents, Pixel2D start, Pixel2D target) {
        List<Pixel2D> path = new ArrayList<>();
        Pixel2D curr = target;

        while (curr != null) {
            path.add(0, curr);
            // אם הגענו לנקודת ההתחלה, עוצרים
            if (curr.getX() == start.getX() && curr.getY() == start.getY()) {
                return path.toArray(new Pixel2D[0]);
            }
            curr = parents[curr.getX()][curr.getY()];
        }
        return null;
    }
    }


