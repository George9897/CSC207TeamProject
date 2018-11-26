package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SudokuTest {

    /**
     * The Sudoku for test.
     */
    private Sudoku sudoku;

    /**
     * The tiles on the sudoku board in row-major order.
     */
    private Tile[][] tiles = new Tile[9][9];

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
        final int numTiles = sudoku.getSize() * sudoku.getSize();
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
        context = new MockContext();
        sudoku = new Sudoku(createTiles());
    }

    @After
    public void tearDown() throws Exception {
        sudoku = null;
    }

    @Test
    public void iterator() {
    }

    @Test
    public void getSize() {
        assertEquals(9, sudoku.getSize());
    }

    @Test
    public void getTile() {
    }

    @Test
    public void writeNum() {
//        tiles[9][9] = new Tile(move);
//        setChanged();
//        notifyObservers();
    }

//    @Test
//    public void toString() {
//    }
}