package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardManagerTest {
    /**
     * The Boardmanager for test.
     */
    private BoardManager boardManager;
    /**
     * Context for test.
     */
    Context context = new MockContext();

//    /**
//     * Create a initial list of Tiles for game with matching sizes.
//     *
//     * @return list of Tiles.
//     */
//    private List createTiles() {
//        List<Tile> tiles = new ArrayList<>();
//        final int numTiles = this.boardManager.getLevel() * this.boardManager.getLevel();
//        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
//            if (tileNum == numTiles - 1) {
//                tiles.add(new Tile(0));
//            } else {
//                tiles.add(new Tile(tileNum + 1));
//            }
//        }
//        return tiles;
//    }

    @Before
    public void setUp() throws Exception {
        boardManager = new BoardManager(context, 3);
    }

    @After
    public void tearDown() throws Exception {
        context = null;
        boardManager = null;
    }

    @Test
    public void getSlidingTile() {
//        assertEquals(, this.getSlidingTile());
    }

    @Test
    public void getUndoLimit() {
        assertEquals(0, boardManager.getUndoLimit());
    }

    @Test
    public void getUndoLimit3() {
        assertEquals(3, boardManager.getUndoLimit3());
    }

    @Test
    public void testCreateTiles() {
    }

    @Test
    public void getNumMoves() {
        assertEquals(0, boardManager.getNumMoves());
    }

    @Test
    public void getScore() {
        assertEquals(0, boardManager.getScore());
    }

    @Test
    public void getSlidingTileDifficulty() {
        assertEquals("Easy", boardManager.getSlidingTileDifficulty());
        boardManager = new BoardManager(context, 4);
        assertEquals("Medium", boardManager.getSlidingTileDifficulty());
        boardManager = new BoardManager(context, 5);
        assertEquals("Hard", boardManager.getSlidingTileDifficulty());
    }

//    @Test
//    public void setSlidingTileDifficulty() {
//    }

    @Test
    public void puzzleSolved() {
    }

    @Test
    public void isValidTap() {
    }

    @Test
    public void touchMove() {
    }

    @Test
    public void undo() {
    }

    @Test
    public void undo3() {
    }
}