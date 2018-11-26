package fall2018.csc2017.GameCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Test for Mine board class.
 */
public class MineBoardTest {
    /**
     * The mine board for test.
     */
    private MineBoard mineBoard;

    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    private List<MineTile> createTiles() {
        List<MineTile> mineTiles = new ArrayList<>();
        int numTiles = MineBoard.getSize() * MineBoard.getSize();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            mineTiles.add(new MineTile(0, false));
        }
        return mineTiles;
    }

    /**
     * Set up the mine board for test.
     */
    @Before
    public void setUp() {
        mineBoard = new MineBoard(createTiles(), 26, new Random());
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        mineBoard = null;
    }

    /**
     * Test whether getSize works.
     */
    @Test
    public void testGetSize() {
        assertEquals(16, MineBoard.getSize());
    }

    /**
     * Test whether getNumber works.
     */
    @Test
    public void testGetNumBoom() {
        assertEquals(26, mineBoard.getNumBoom());
    }

    /**
     * Test whether getMineTile works.
     */
    @Test
    public void testGetMineTile() {
        int row = 8;
        int col = 8;
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
    }

    /**
     * Test whether setNumBoom works.
     */
    @Test
    public void testSetNumBoom() {
        assertEquals(26, mineBoard.getNumBoom());
        mineBoard.setNumBoom(52);
        assertEquals(52, mineBoard.getNumBoom());
    }

    /**
     * Test whether touchOpen works.
     */
    @Test
    public void testTouchOpen() {
        mineBoard = new MineBoard(createTiles(), 1, new Random());
        mineBoard.touchOpen(0, true);
        assertTrue(mineBoard.getMineTile(0,0).getIsOpened());
        mineBoard.displayAllBoom();
        mineBoard.iterator();
        mineBoard.notifyObservers();
    }

    /**
     * Test whether replaceToFlag works.
     */
    @Test
    public void testReplaceToFlag() {
        int row = 8;
        int col = 8;
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertEquals(R.drawable.tile_closed, mineBoard.getMineTile(row, col).getBackground());
        mineBoard.replaceToFlag(row, col);
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertEquals(R.drawable.tile_flagged, mineBoard.getMineTile(row, col).getBackground());
        mineBoard.replaceToFlag(row, col);
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertEquals(R.drawable.tile_closed, mineBoard.getMineTile(row, col).getBackground());
    }
}