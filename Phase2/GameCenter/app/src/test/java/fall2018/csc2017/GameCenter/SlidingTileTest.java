package fall2018.csc2017.GameCenter;

import android.content.Context;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

//import fall2018.csc2017.GameCenter.BoardManager;

import static org.junit.Assert.*;

public class SlidingTileTest {
    /**
     * Sliding tile for test.
     */
    private SlidingTile slidingTile1;
    private SlidingTile slidingTile2;
    private SlidingTile slidingTile3;

    public NoSuchElementException thrown= new NoSuchElementException();

    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    private List createTiles(int level) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = level * level;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (tileNum == numTiles - 1) {
                tiles.add(new Tile(0));
            } else {
                tiles.add(new Tile(tileNum + 1));
            }
        }
        return tiles;
    }


    @Before
    public void setUp() throws Exception {
        slidingTile1 = new SlidingTile(createTiles(3), 3);
        slidingTile2 = new SlidingTile(createTiles(4), 4);
        slidingTile3 = new SlidingTile(createTiles(5), 5);

    }

    @After
    public void tearDown() throws Exception {
        slidingTile1 = null;
        slidingTile2 = null;
        slidingTile3 = null;
    }

    @Test
    public void getLevel() {
        assertEquals(3, slidingTile1.getLevel());
        assertEquals(4, slidingTile2.getLevel());
        assertEquals(5, slidingTile3.getLevel());
    }

    @Test
    public void numTiles() {
        assertEquals(9, slidingTile1.numTiles());
        assertEquals(16, slidingTile2.numTiles());
        assertEquals(25, slidingTile3.numTiles());
    }

    @Test
    public void getTileList() {
        assertEquals(3, slidingTile1.getTileList().length);
        assertEquals(4, slidingTile2.getTileList().length);
        assertEquals(5, slidingTile3.getTileList().length);
    }

    @Test
    public void getTile() {
        // size three
        assertEquals(1,slidingTile1.getTile(0,0).getId());
        assertEquals(2,slidingTile1.getTile(0,1).getId());
        assertEquals(3,slidingTile1.getTile(0,2).getId());
        assertEquals(4,slidingTile1.getTile(1,0).getId());
        assertEquals(5,slidingTile1.getTile(1,1).getId());
        assertEquals(6,slidingTile1.getTile(1,2).getId());
        assertEquals(7,slidingTile1.getTile(2,0).getId());
        assertEquals(8,slidingTile1.getTile(2,1).getId());
        assertEquals(0,slidingTile1.getTile(2,2).getId());

        // size four (omit 1-8)
        assertEquals(9,slidingTile2.getTile(2,0).getId());
        assertEquals(10,slidingTile2.getTile(2,1).getId());
        assertEquals(11,slidingTile2.getTile(2,2).getId());
        assertEquals(12,slidingTile2.getTile(2,3).getId());
        assertEquals(13,slidingTile2.getTile(3,0).getId());
        assertEquals(14,slidingTile2.getTile(3,1).getId());
        assertEquals(15,slidingTile2.getTile(3,2).getId());
        assertEquals(0,slidingTile2.getTile(3,3).getId());

        // size five (omit 1-15)
        assertEquals(16,slidingTile3.getTile(3,0).getId());
        assertEquals(17,slidingTile3.getTile(3,1).getId());
        assertEquals(18,slidingTile3.getTile(3,2).getId());
        assertEquals(19,slidingTile3.getTile(3,3).getId());
        assertEquals(20,slidingTile3.getTile(3,4).getId());
        assertEquals(21,slidingTile3.getTile(4,0).getId());
        assertEquals(22,slidingTile3.getTile(4,1).getId());
        assertEquals(23,slidingTile3.getTile(4,2).getId());
        assertEquals(24,slidingTile3.getTile(4,3).getId());
        assertEquals(0,slidingTile3.getTile(4,4).getId());
    }

//    @Test
//    public void swapTiles() {
//    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        assertEquals(1, slidingTile1.getTile(0, 0).getId());
        assertEquals(2, slidingTile1.getTile(0, 1).getId());
        slidingTile1.swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingTile1.getTile(0, 0).getId());
        assertEquals(1, slidingTile1.getTile(0, 1).getId());
        slidingTile1.swapTiles(0, 0, 0, 1);
    }

    @Test
    public void testSwapMiddleTwo(){
        assertEquals(9, slidingTile2.getTile(2, 0).getId());
        assertEquals(2, slidingTile2.getTile(0, 1).getId());
        slidingTile2.swapTiles(2, 0, 0, 1);
        assertEquals(2, slidingTile2.getTile(2, 0).getId());
        assertEquals(9, slidingTile2.getTile(0, 1).getId());
        slidingTile2.swapTiles(2, 0, 0, 1);
    }

    @Test
    public void testSwapLastTwo(){
        assertEquals(9, slidingTile3.getTile(1, 3).getId());
        assertEquals(20, slidingTile3.getTile(3, 4).getId());
        slidingTile3.swapTiles(1, 3, 3, 4);
        assertEquals(20, slidingTile3.getTile(1, 3).getId());
        assertEquals(9, slidingTile3.getTile(3, 4).getId());
        slidingTile3.swapTiles(1, 3, 3, 4);
    }

    @Test
    public void testtoString(){
        String result = "SlidingTile{" +
                "tiles=" + Arrays.toString(slidingTile1.getTileList()) + '}';
        assertEquals(result,slidingTile1.toString());
    }

    @Test
    public void iterator() throws Exception {
        SlidingTile slidingTile = new SlidingTile(createTiles(0), 0);
        Iterator<Tile> iter = slidingTile.iterator();
        assertTrue(slidingTile1.iterator().hasNext());
        assertEquals(1, slidingTile1.iterator().next().getId());
        try{Object a = iter.next();} catch (Exception ex) {
            assertTrue(ex instanceof NoSuchElementException);
        }
    }

}