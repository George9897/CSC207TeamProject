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
//    context = Mockito.mock(Context.class);

    @Before
    public void setUp() throws Exception {
        context = Mockito.mock(Context.class);
        boardManager1 = new BoardManager(context, 3, true);
        boardManager2 = new BoardManager(context, 4, true);
        boardManager3 = new BoardManager(context, 5, true);
//        boardManager = spy(boardManager);
//        when(boardManager.getUndoLimit3()).thenReturn(3);
    }

    @After
    public void tearDown() throws Exception {
        context = null;
        boardManager1 = null;
        boardManager2 = null;
        boardManager3 = null;
    }

    @Test
    public void getSlidingTile() {
//        assertEquals(, this.getSlidingTile());
    }

    @Test
    public void getUndoLimit() {
        assertEquals(0, boardManager1.getUndoLimit());
        assertEquals(0, boardManager2.getUndoLimit());
        assertEquals(0, boardManager3.getUndoLimit());
    }

    @Test
    public void getUndoLimit3() {
        assertEquals(3, boardManager1.getUndoLimit3());
        assertEquals(3, boardManager2.getUndoLimit3());
        assertEquals(3, boardManager3.getUndoLimit3());
    }

    @Test
    public void testCreateTiles() {
    }

    @Test
    public void getNumMoves() {
        assertEquals(0, boardManager1.getNumMoves());
        boardManager1.touchMove(7);
        assertEquals(1, boardManager1.getNumMoves());
    }

    @Test
    public void setScore(){
        int testScore = 0;
        boardManager1.setScore(testScore);
        assertEquals(testScore, boardManager1.getScore());
    }
    @Test
    public void getScore() {
        int testScore = 0;
        boardManager1.setScore(testScore);
        assertEquals(testScore, boardManager1.getScore());
    }

    @Test
    public void getSlidingTileDifficulty() {
        assertEquals("Easy", boardManager1.getSlidingTileDifficulty());
        assertEquals("Medium", boardManager2.getSlidingTileDifficulty());
        assertEquals("Hard", boardManager3.getSlidingTileDifficulty());
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

    @Test
    public void getUserName() {
    }

    @Test
    public void setUserName() {
    }

    @Test
    public void getLevel() {
        assertEquals(3, boardManager1.getLevel());
    }
}