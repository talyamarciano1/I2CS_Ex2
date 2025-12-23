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
     */
    private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private Map2D _m0, _m1, _m3_3;
    @BeforeEach
    public void setuo() {
        _m3_3 = new Map(_map_3_3);
    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [500][500];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        _m1.fill(p1,1, true);
    }

    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }
    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
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
        Map t = new Map(3, 3, 1);
        double scalar = 5;
        t.mul(scalar);
        System.out.println(t);
    }
    @Test
    void testMul2() {
        Map t = new Map(3, 3, 4);
        double scalar = -0.5;
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
    void testfill() {
        int [][] t = {{2,5,4,1},{2,2,7,2},{4,2,3,3},{3,2,2,4}};
        Map N = new Map(t);
        Pixel2D p1 = new Index2D(0, 0);
        assertEquals(6,N.fill(p1,5,false));
    }
    @Test
    void testFill2() {
        int [][] t = {{2,5,4,1},{2,2,7,2},{4,2,3,3},{3,2,2,4}};
        Map N = new Map(t);
        Map T = new Map(t);
        Pixel2D p1 = new Index2D(0, 0);
        assertEquals(6,N.fill(p1,5,false));
        assertEquals(7,T.fill(p1,5,true)); // not working! you got this!


    }
}