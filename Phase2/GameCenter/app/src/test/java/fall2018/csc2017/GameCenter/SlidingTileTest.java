package fall2018.csc2017.GameCenter;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

//import fall2018.csc2017.GameCenter.BoardManager;

import static org.junit.Assert.*;

public class SlidingTileTest {
    /**
     * Sliding tile for test.
     */
    private SlidingTile slidingTile;
    /**
     * The tiles on the slidingTile in row-major order.
     */
    private List<Tile> tiles;

    @Before
    public void setUp() throws Exception {
        slidingTile = new SlidingTile(this.tiles, 3);

    }

    @After
    public void tearDown() throws Exception {
        slidingTile = null;
        tiles = null;
    }

    @Test
    public void getLevel() {
        assertEquals(3, slidingTile.getLevel());
        slidingTile = new SlidingTile(tiles, 4);
        assertEquals(4, slidingTile.getLevel());
        slidingTile = new SlidingTile(tiles, 5);
        assertEquals(5, slidingTile.getLevel());
        slidingTile = new SlidingTile(tiles, 3);
    }

    @Test
    public void numTiles() {
        assertEquals(9, slidingTile.numTiles());
        slidingTile = new SlidingTile(tiles, 4);
        assertEquals(16, slidingTile.numTiles());
        slidingTile = new SlidingTile(tiles, 5);
        assertEquals(25, slidingTile.numTiles());
        slidingTile = new SlidingTile(tiles, 3);
    }

    @Test
    public void getTileList() {
    }

    @Test
    public void getTile() {
    }

    @Test
    public void swapTiles() {
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
//        assertEquals(1, board.getTile(0, 0).getId());
//        assertEquals(2, board.getTile(0, 1).getId());
//        boardManager.getBoard().swapTiles(0, 0, 0, 1);
//        assertEquals(2, board.getTile(0, 0).getId());
//        assertEquals(1, board.getTile(0, 1).getId());
    }
}