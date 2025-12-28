import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 */
public class Ex2_GUI {
    public static void drawMap(Map2D map) {
        int w = map.getWidth();
        int h = map.getHeight();

        StdDraw.setXscale(-0.5, w - 0.5);
        StdDraw.setYscale(h - 0.5, -0.5);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int v = map.getPixel(x, y);

                if( v == 0) StdDraw.setPenColor(StdDraw.WHITE);
                else if ( v == 1) StdDraw.setPenColor(StdDraw.BLACK);
                else if ( v == 2) StdDraw.setPenColor(StdDraw.YELLOW);
                else if ( v == 3) StdDraw.setPenColor(StdDraw.MAGENTA);
                else if ( v == 4) StdDraw.setPenColor(StdDraw.RED);
                else if ( v == 5) StdDraw.setPenColor(StdDraw.CYAN);
                else if ( v == 6) StdDraw.setPenColor(StdDraw.GREEN);
                else StdDraw.setPenColor(StdDraw.PINK);
                StdDraw.filledSquare(x,y,0.5);
            }
         }
        StdDraw.show();
    }

    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName){
        try{
        File myFile = new File("mapFileName");
        Scanner reader = new Scanner(myFile);
        int rows = reader.nextInt();
        int cols = reader.nextInt();
        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (reader.hasNextInt()) {
                    matrix[i][j] = reader.nextInt();
                }
            }
        }
        reader.close();
        Map n = new Map(matrix);
        return n;
    }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return null;
    }
    /**
     *
     * @param map
     * @param mapFileName
     */
  public static void saveMap(Map2D map, String mapFileName) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(mapFileName))) {

          int width = map.getWidth();
          int height = map.getHeight();

          writer.write(width + " " + height);
          writer.newLine();
          for (int y = 0; y < height; y++) {
              for (int x = 0; x < width; x++) {
                  // שיניתי כאן מ-getTile ל-getPixel בהתאם לשמות המקובלים במחלקה שלך
                  String tileValue = String.valueOf(map.getPixel(x, y));
                  writer.write(tileValue + " ");
              }
              writer.newLine();
          }
          System.out.println("Map successfully saved to " + mapFileName);
      } catch (IOException e) {
          System.err.println("Error saving map: " + e.getMessage());
          e.printStackTrace();
      }
  }

    public static void main(String[] a) {
        String mapFile = "map.txt";
        Map2D map = Ex2_GUI.loadMap(mapFile);
        Pixel2D p1 = new Index2D(0,0);
        Pixel2D p2 =  new Index2D(7,5);
        Map2D map1 = new Map(8);
        int [][] n = {{0,0,0,0,0,0,0,0},{0,0,0,0,1,0,0,0},{0,1,1,1,1,0,0,0},{0,1,0,0,0,0,0,0},{0,1,0,0,0,0,0,0},{0,1,0,1,0,0,1,0},{0,1,0,0,0,1,1,0},{0,1,1,1,1,0,1,0},{0,0,0,0,0,0,0,0}};
        Map N = new Map(n);
        Pixel2D[] path = N.shortestPath(p1, p2, 1, true);
        if (path != null) {
            for (Pixel2D p : path) {
                N.setPixel(p.getX(), p.getY(), 3);
            }
        } else {
            System.out.println("No path found!");
        }
        drawMap(N);
    }
    /// ///////////// Private functions ///////////////
}


