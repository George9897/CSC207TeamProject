package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class BoardManagerTest {
    /**
     * The Boardmanager for test.
     */
    private BoardManager boardManager1;
    private BoardManager boardManager2;
    private BoardManager boardManager3;
    /**
     * Context for test.
     */
    private Context context;

    /**
     * Set up three BoardManager for tests
     */
    @Before
    public void setUp() {
        context = Mockito.mock(Context.class);
        boardManager1 = new BoardManager(context, 3, true);
        boardManager2 = new BoardManager(context, 4, true);
        boardManager3 = new BoardManager(context, 5, true);
    }

    /**
     * Tear down after test
     */
    @After
    public void tearDown() {
        context = null;
        boardManager1 = null;
        boardManager2 = null;
        boardManager3 = null;
    }

    /**
     * Test getSlidingTile method
     */
    @Test
    public void getSlidingTile() {
    }

    /**
     * Test getUndoLimit method
     */
    @Test
    public void getUndoLimit() {
        assertEquals(0, boardManager1.getUndoLimit());
        assertEquals(0, boardManager2.getUndoLimit());
        assertEquals(0, boardManager3.getUndoLimit());
    }

    /**
     * Test getUndoLimit3 method
     */
    @Test
    public void getUndoLimit3() {
        assertEquals(3, boardManager1.getUndoLimit3());
        assertEquals(3, boardManager2.getUndoLimit3());
        assertEquals(3, boardManager3.getUndoLimit3());
    }

    /**
     * Test createTiles method
     */
    @Test
    public void testCreateTiles() {
    }

    /**
     * Test getNumMoves method
     */
    @Test
    public void getNumMoves() {
        assertEquals(0, boardManager1.getNumMoves());
        boardManager1.touchMove(7);
        assertEquals(1, boardManager1.getNumMoves());
    }

    /**
     * Test setScore method
     */
    @Test
    public void setScore(){
        int testScore = 0;
        boardManager1.setScore(testScore);
        assertEquals(testScore, boardManager1.getScore());
    }

    /**
     * Test getScore method
     */
    @Test
    public void getScore() {
        int testScore = 0;
        boardManager1.setScore(testScore);
        assertEquals(testScore, boardManager1.getScore());
    }

    /**
     * Test getSlidingTileDifficulty method
     */
    @Test
    public void getSlidingTileDifficulty() {
        assertEquals("Easy", boardManager1.getSlidingTileDifficulty());
        assertEquals("Medium", boardManager2.getSlidingTileDifficulty());
        assertEquals("Hard", boardManager3.getSlidingTileDifficulty());
    }

    /**
     * Test puzzleSolved method
     */
    @Test
    public void puzzleSolved() {
    }

    /**
     * Test isValidTap method
     */
    @Test
    public void isValidTap() {
//        assertEquals(True, boardManager.isValidTap());
    }

    /**
     * Test touchMove method
     */
    @Test
    public void touchMove() {
    }

    /**
     * Test undo method
     */
    @Test
    public void undo() {
    }

    /**
     * Test undo3 method
     */
    @Test
    public void undo3() {
    }

    /**
     * Test getUserName method
     */
    @Test
    public void getUserName() {
    }

    /**
     * Test setUserName method
     */
    @Test
    public void setUserName() {
    }

    /**
     * Test getLevel method
     */
    @Test
    public void getLevel() {
        assertEquals(3, boardManager1.getLevel());
    }
}