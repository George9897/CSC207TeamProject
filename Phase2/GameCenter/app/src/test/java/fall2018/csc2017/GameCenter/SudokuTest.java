package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class SudokuTest {

    /**
     * The Sudoku for test.
     */
    private Sudoku sudoku;

//    private Iterator<Tile> iterator = sudoku.SudokuIterator();



    /**
     * Context for test.
     */
    private Context context = new MockContext();;

    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    private List createTiles() {
        List<Tile> tiles = new ArrayList<>();
//        final int numTiles = sudoku.getSize() * sudoku.getSize();
        final int numTiles = 81;
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
        sudoku = new Sudoku(createTiles());
    }

    @After
    public void tearDown() throws Exception {
        sudoku = null;
    }

    @Test
    public void iterator() throws Exception {
        assertTrue(sudoku.iterator().hasNext());
        assertEquals(1, sudoku.iterator().next().getId());
    }

    @Test
    public void numTiles() {
        assertEquals(81, sudoku.numTiles());
    }

    @Test
    public void getSize() {
        assertEquals(9, sudoku.getSize());
    }

    @Test
    public void getTile() {
        assertEquals(11, sudoku.getTile(1,1).getId());
    }

    @Test
    public void writeNum() {
        sudoku.writeNum(1, 1, 1);
        assertEquals(1, sudoku.getTile(1,1).getId());
        sudoku.writeNum(8, 8, 7);
        assertEquals(7, sudoku.getTile(8,8).getId());

    }

    @Test
    public void testtoString() {
        assertEquals("Sudoku{" +
                "tiles=" + Arrays.toString(sudoku.getTileList()) +
                '}', sudoku.toString());
    }
}