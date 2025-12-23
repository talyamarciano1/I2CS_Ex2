import java.awt.*;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 *
 */
public class Ex2_GUI {
    public static void drawMap(Map2D map) {
        int w = map.getWidth();
        int h = map.getHeight();

        StdDraw.setXscale(-0.5, w - 0.5);
        StdDraw.setYscale(h - 0.5, -0.5); // Flip Y so [0][0] is top-left

         for (int i = 0; i < map.getWidth(); i++) {
            for(int j = 0; j < map.getHeight(); j++) {
                int v = map.getMap()[i][j];

                if( v == 0) StdDraw.setPenColor(StdDraw.GRAY);
                else if ( v == 1) StdDraw.setPenColor(StdDraw.BLACK);
                else if ( v == 2) StdDraw.setPenColor(StdDraw.PINK);
                else if ( v == 3) StdDraw.setPenColor(StdDraw.BLUE);
                else if ( v == 4) StdDraw.setPenColor(StdDraw.RED);
                else if ( v == 5) StdDraw.setPenColor(StdDraw.CYAN);
                else if ( v == 6) StdDraw.setPenColor(StdDraw.GREEN);
                else StdDraw.setPenColor(StdDraw.PINK);
                StdDraw.filledSquare(i,j,0.5);
            }
         }
        StdDraw.show();
    }

    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName) {
        Map2D ans = null;

        return ans;
    }

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {


    }
    public static void main(String[] a) {
        String mapFile = "map.txt";
        //Map2D map = loadMap(mapFile);
        Map2D map = new Map(100);
        map.drawCircle(new Index2D(20,20),20,5);
        map.drawRect(new Index2D(30,20),new Index2D(50,60),6);
        drawMap(map);

    }


    /// ///////////// Private functions ///////////////
}


