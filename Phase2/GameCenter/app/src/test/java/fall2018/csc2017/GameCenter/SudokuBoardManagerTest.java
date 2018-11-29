package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Timer;

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

    Timer timer;

    /**
     * Context for test.
     */
    Context context;

    @Before
    public void setUp() throws Exception {
        context = Mockito.mock(Context.class);
        sudokuBoardManager = new SudokuBoardManager(context,"Easy");
    }

    @After
    public void tearDown() throws Exception {
        context = null;
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
        sudokuBoardManager.setScore(100);
        assertEquals(100, sudokuBoardManager.getScore());
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
//        assertEquals(sudokuBoardManager.sudoku, sudokuBoardManager.getSudoku());
    }

    @Test
    public void getUserName() {
        assertEquals(null, sudokuBoardManager.getUserName());
    }

    @Test
    public void setUserName() {
        sudokuBoardManager.setUserName("Tom");
        assertEquals("Tom", sudokuBoardManager.getUserName());
    }

    @Test
    public void getDifficulty(){
        assertEquals(2, sudokuBoardManager.getDifficulty());
    }

    @Test
    public void createTiles() {
    }

    @Test
    public void getSudokuBoardManager() {
        assertEquals("Easy", sudokuBoardManager.getSudokuDifficulty());

    }

    @Test
    public void getTimer(){assertEquals(timer, sudokuBoardManager.getTimer());}

    @Test
    public void addTime(){
        sudokuBoardManager.addTime(100);
        assertEquals(100, sudokuBoardManager.getTime());
    }

    @Test
    public void getScorer(){assertEquals(null, sudokuBoardManager.getScorer());}

    @Test
    public void setTimer(){
        sudokuBoardManager.setTimer(timer);
        assertEquals(0, sudokuBoardManager.getTime());
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