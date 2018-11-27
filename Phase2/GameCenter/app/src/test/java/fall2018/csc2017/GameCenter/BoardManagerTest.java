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
    public void setScore(){
        int testScore = 0;
        boardManager.setScore(testScore);
        assertEquals(testScore, boardManager.getScore());
    }
    @Test
    public void getScore() {
        int testScore = 0;
        boardManager.setScore(testScore);
        assertEquals(testScore, boardManager.getScore());
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
//        assertEquals(True, boardManager.isValidTap());
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