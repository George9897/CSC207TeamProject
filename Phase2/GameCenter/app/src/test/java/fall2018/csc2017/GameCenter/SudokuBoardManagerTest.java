package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuBoardManagerTest {

    /**
     * The SudokuBoardManager for test.
     */
    private SudokuBoardManager sudokuBoardManager;

    /**
     * The AccountManager.
     */
    private AccountManager accountManager;

    /**
     * The user's name.
     */
    private String userName;

    /**
     * Context for test.
     */
    Context context = new MockContext();

    @Before
    public void setUp() throws Exception {
        sudokuBoardManager = new SudokuBoardManager(context,"Easy");
    }

    @After
    public void tearDown() throws Exception {
        sudokuBoardManager = null;
    }

    @Test
    public void getTime() {
        assertEquals(0, sudokuBoardManager.getTime());
    }

    @Test
    public void getScore() {
        assertEquals(0, sudokuBoardManager.getScore());
    }

    @Test
    public void setScore() {
    }

    @Test
    public void getSudokuDifficulty() {
        assertEquals("Easy", sudokuBoardManager.getSudokuDifficulty());
    }

    @Test
    public void setTime() {
        sudokuBoardManager.setTime(19);
        assertEquals(19 , sudokuBoardManager.getTime());

    }

    @Test
    public void getSudoku() {
//        assertEquals(sudokuBoardManager.udoku, sudokuBoardManager.getSudoku());
    }

    @Test
    public void getUserName() {
        assertEquals("Easy", sudokuBoardManager.getUserName());
    }

    @Test
    public void getDifficulty() {
        assertEquals("Easy", sudokuBoardManager.getSudokuDifficulty());
    }

    @Test
    public void createTiles() {
    }

    @Test
    public void getSudokuBoardManager() {
        assertEquals("Easy", sudokuBoardManager.getSudokuDifficulty());

    }

    @Test
    public void destroySudokuBoardManager() {
    }

    @Test
    public void puzzleSolved() {
    }

    @Test
    public void wining() {
    }

    @Test
    public void isValidTap() {
    }

    @Test
    public void makeMove() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void setMove() {
    }

    @Test
    public void undo() {
    }
}