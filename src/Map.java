import java.awt.image.BandedSampleModel;
import java.util.Objects;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
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
        init(w,h,v);
    }
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {
        this(size,size, 0);
    }
	/**
	 * Constructs a map from a given 2D array.
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
       if (w <= 0 || h <= 0 ){
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
        int ans =-1;
        if (0<=x && x<this.w && 0<=y && y<this.h) {
            ans = this.map[x][y];
            return ans;
        }
        return ans ;
    }
	@Override
	public int getPixel(Pixel2D p) {
        int ans = -1;
        if (p == null) {
            return ans;
        }
        int x = p.getX();
        int y = p.getY();
        if (0<=x && x<this.w && 0<=y && y<this.h) {
            ans = this.map[x][y];
            return ans;
        }
        return ans;
	}
	@Override
	public void setPixel(int x, int y, int v) {
        if (0<=x && x<this.w && 0<=y && y<this.h) {
        this.map[x][y] = v;}
    }
	@Override
	public void setPixel(Pixel2D p, int v) {
        if (p != null) {
            int x = p.getX();
            int y = p.getY();
            if (0<=x && x<this.w && 0<=y && y<this.h) {
                this.map[x][y] = v;
            }
        }
	}

    @Override
    public boolean isInside(Pixel2D p) {
        boolean ans = true;
        int x = p.getX();
        int y = p.getY();
        if (0<=x && x<this.w && 0<=y && y<this.h) {
            return ans;
        }
        else  {
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

    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        double r = rad;
        int startX =(int)(center.getX() - r); // go over only about the bounding box.
        int endX = (int) (center.getX()+ r);// and both p1 and p2 are within this map
        int startY = (int) (center.getY() - r);
        int endY = (int)(center.getY() + r);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                double DeltaX = Math.abs(i - center.getX());
                double DeltaY = Math.abs(j - center.getY());
                double sum = Math.pow(DeltaX, 2) + Math.pow(DeltaY, 2);
                if (sum <= r*r) {
                    this.map[i][j] = color;
                }
            }

        }
    }


    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {

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
        boolean ans = false;
        if (this == ob) return true;
        if(!(ob instanceof Map2D)) return false;// check same object
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
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
        int old = getPixel(xy.getX(), xy.getY());
        int count = 1;
        Queue<Pixel2D> q = new LinkedList<>();
        q.add(xy);
        setPixel(xy, new_v);
        while (!q.isEmpty()) {
            Pixel2D p = q.poll();
            int x = p.getX();
            int y = p.getY();

            if (cyclic) {
                if (p.getX() == -1) { x = getWidth() -1;}
                if (p.getY() == -1) { y = getHeight() -1;}
                if (p.getX() == getWidth() -1 ) { x = 0;}
                if (p.getY() == getHeight() -1 ) { y = 0;}
            }
            int nextX = x + 1;
            int nextY = y;
            if (nextX < this.w) {
                if (getPixel(nextX, nextY) == old) {
                    setPixel(nextX, nextY, new_v);
                    q.add(new Index2D(nextX, nextY));
                    count++;
                }}
                int nextX2 = x - 1;
                int nextY2 = y;
                if (0 <= nextX2 && nextX2 < this.w) {
                    if (getPixel(nextX2, nextY2) == old) {
                        setPixel(nextX2, nextY2, new_v);
                        q.add(new Index2D(nextX2, nextY2));
                        count++;
                    }}
                    int nextX3 = x;
                    int nextY3 = y + 1;
                    if (nextY3 < this.h) {
                        if (getPixel(nextX3, nextY3) == old) {
                            setPixel(nextX3, nextY3, new_v);
                            q.add(new Index2D(nextX3, nextY3));
                            count++;
                        }}
                        int nextX4 = x;
                        int nextY4 = y - 1;
                        if (0 <= nextY4 && nextY4 < this.w) {
                            if (getPixel(nextX4, nextY4) == old) {
                                setPixel(nextX4, nextY4, new_v);
                                q.add(new Index2D(nextX4, nextY4));
                                count++;}}
            }
        return count;
    }


	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.

		return ans;
	}
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }
	////////////////////// Private Methods ///////////////////////


}
