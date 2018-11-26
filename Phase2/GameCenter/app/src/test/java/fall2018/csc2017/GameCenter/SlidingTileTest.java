import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCenter.BoardManager;

import static org.junit.Assert.*;

public class SlidingTileTest {

    @Before
    public void setUp() throws Exception {
        /*
          The board manager for testing.
         */
        //BoardManager boardManager;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }
    public void iterator() {
    }

    @Test
    public void getLevel() {
    }

    @Test
    public void numTiles() {
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
        // setUpCorrect();
//        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
//        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
//        boardManager.getBoard().swapTiles(0, 0, 0, 1);
//        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
//        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }
}