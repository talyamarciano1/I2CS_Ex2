import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.sql.SQLOutput;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Intro2CS, 2026A, this is a very
 */
class MapTest {
    /**
     *
     */
    private int[][] _map_3_3 = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
    private Map2D _m0, _m1, _m3_3;

    @BeforeEach
    public void setuo() {
        _m3_3 = new Map(_map_3_3);
    }

    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int[500][500];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3, 2);
        _m1.fill(p1, 1, true);
    }

    @Test
    void testInit() {
        _m0=new Map(_map_3_3);
        _m1 = new Map(_map_3_3);
        assertEquals(_m0, _m1);
    }

    @Test
    void testEquals() {
        Map2D m0 = new Map(5, 5, 0);
        Map2D m1 = new Map(5, 5, 0);
        assertEquals(m0, m1);
    }
    @Test
    void testEquals2() {
        Map2D m0 = new Map(5, 2, 0);
        Map2D m1 = new Map(5, 4, 0);
        assertNotEquals(m0, m1);
    }
    @Test
    void testSamedim() {
        Map2D m0 = new Map(5, 6, 0);
        Map2D m1 = new Map(5, 8, 0);
        assertNotEquals(m0, m1);
        Map2D m2 = new Map(5, 6, 0);
        assertNotEquals(m2, _m1);
        assertEquals(_m0, _m1);

    }
    @Test
    void testRescale() {
        int[][] b = {{1,2},{3,4}};
        Map2D map = new Map(b);
        int [][] map2 = {{1,1,2,2},{1,1,2,2},{3,3,4,4},{3,3,4,4}};
        Map2D map3 = new Map(map2);
        map.rescale(2,2);
        assertEquals(map,map3);

    }

    @Test
    void testDrawRect() {
        Map t = new Map(20, 20, 1);
        Pixel2D p1 = new Index2D(2, 2);
        Pixel2D p2 = new Index2D(10, 10);
        t.drawRect(p1, p2, 0);
        System.out.println(t);
    }

    @Test
    void testdrawCircle() {
        Map t = new Map(20, 20, 1);
        Pixel2D p1 = new Index2D(8, 8);
        double r = 5;
        t.drawCircle(p1, r, 0);
        System.out.println(t);
    }
    @Test
    void testMul() {
        Map t = new Map(3, 3, 5);
        double scalar = 0.5;
        t.mul(scalar);
        System.out.println(t);
    }
    @Test
    void testMul2() {
        Map t = new Map(3, 3, 4);
        double scalar = 0;
        t.mul(scalar);
        System.out.println(t);
    }
    @Test
    void testAdd() {
        Map t = new Map(3, 3, 1);
        Map n =  new Map(3, 3, -4);
        t.addMap2D(n);
        System.out.println(t);
    }
    @Test
    void testAdd2() {
            int [][] t = {{2,5,4,1},{2,2,7,2},{4,2,3,3},{3,2,2,4}};
            Map N = new Map(t);
            int [][] T = {{1,1,1,1},{2,2,2,2},{3,3,3,3},{4,4,4,4}};
            Map Y = new Map(T);
            N.addMap2D(Y);
            int [][] e = {{3,6,5,2},{4,4,9,4},{7,5,6,6},{7,6,6,8}};
            Map expected = new Map(e);
            assertEquals(expected, N);
    }
    @Test
    void Drawline() {{
            Map2D map = new Map (10);
            map.drawLine(new Index2D(2, 1), new Index2D(2, 3), 1);
            if (map.getPixel(2, 2) == 1) {
                System.out.println("Success!");
            } else {
                System.out.println("Failed!");
            }
        }
    }
    @Test
    void testDrawLine2() {{
            Map2D map = new Map(5);
            Pixel2D p = new Index2D(1, 1);
            map.drawLine(p, p, 9);
            System.out.println(map.getPixel(1, 1) == 9 ? "Pass" : "Fail");
        }
    }

    @Test
    void testfill() {
        int [][] t = {{2,5,4,1},{2,2,7,2},{4,2,3,3},{3,2,2,4}};
        Map N = new Map(t);
        Pixel2D p1 = new Index2D(0, 0);
        assertEquals(6,N.fill(p1,5,false));
    }
    @Test
    void testFill2() {
        _m1 = new Map(15);
        Index2D p1 = new Index2D(10, 0);
        Index2D p2 = new Index2D(11,_m1.getWidth() -1 );
        Index2D p3 = new Index2D(0,0);
        _m1.drawRect(p1,p2,4);
        _m1.fill(p3,5,true);
        assertEquals(_m1.getPixel(p3), _m1.getPixel(14,14));
        }
        @Test
        void testShortestPath() {
            int[][] map = {{1,1,0}, {0,1,0},{0,1,0}};
            Map t = new Map(map);
            Pixel2D p1 = new Index2D(0, 0);
            Pixel2D p2 = new Index2D(2, 1);
            int obsColor = 0;
            Pixel2D[] actual = t.shortestPath(p1,p2,0,false);
            Pixel2D[] expected = {new Index2D(0,0),new Index2D(0,1),new Index2D(1,1),new Index2D(2,1)};
            assertArrayEquals(expected, actual);

            Pixel2D[] actual1 = t.shortestPath(p1,p2,0,true);
            Pixel2D[] expected1 = {new Index2D(0,0),new Index2D(0,1),new Index2D(2,1)};
            assertArrayEquals(expected1, actual1);
        }
        }
