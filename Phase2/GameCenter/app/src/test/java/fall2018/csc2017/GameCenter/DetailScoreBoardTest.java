package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;

import static org.junit.Assert.*;

public class DetailScoreBoardTest {

    private DetailScoreBoard detailScoreBoard;

    private Context context = new MockContext();

    private String SlidingTileGameType = "SlidingTile";

    private String MineGameType = "Mine";

    private String SudokuGameType = "Sudoku";


    @Before
    public void setUp() throws Exception {
    }

    private void testSetUpSlidingTile(){
        detailScoreBoard = new DetailScoreBoard(SlidingTileGameType, context);
        // save to file:
        // score: {1: ["Ace"], 2: ["Bella"], 3: ["Cara"]}
        // load from file.
    }

    private void testSetUpMine(){
        detailScoreBoard = new DetailScoreBoard(MineGameType, context);
        // save to file:
        // score: {4: ["David"], 5: ["Ella"], 6: ["Fly"]}
        // load from file.
    }

    private void testSetUpSudoku(){
        detailScoreBoard = new DetailScoreBoard(SudokuGameType, context);
        // save to file:
        // score: {7: ["Gary"], 8: ["Ham"], 9: ["Irene"]}
        // load from file.
    }

    @After
    public void tearDown() throws Exception {
        detailScoreBoard.destroyAllManager();
        detailScoreBoard = null;
    }

    @Test
    public void testDestroyAllManager() {

        testSetUpSlidingTile();
        assertNotNull(detailScoreBoard.getBoardManager());
        detailScoreBoard.destroyAllManager();
        assertNull(detailScoreBoard.getBoardManager());

        testSetUpMine();
        assertNotNull(detailScoreBoard.getMineManager());
        detailScoreBoard.destroyAllManager();
        assertNull(detailScoreBoard.getMineManager());

        testSetUpSudoku();
        assertNotNull(detailScoreBoard.getSudokuBoardManager());
        detailScoreBoard.destroyAllManager();
        assertNull(detailScoreBoard.getSudokuBoardManager());
    }

    @Test
    public void testSetContext() {
        detailScoreBoard = new DetailScoreBoard(SlidingTileGameType, context);
        detailScoreBoard.setContext(context);
        assertEquals(context, detailScoreBoard.getContext());
    }

    @Test
    public void testDisplay() {
    }

    @Test
    public void testGetEasyTopOne() {
    }

    @Test
    public void testGetMediumTopOne() {
    }

    @Test
    public void testGetHardTopOne() {
    }

    @Test
    public void testGetEasySortedList() {
    }

    @Test
    public void testGetMediumSortedList() {
    }

    @Test
    public void testGetHardSortedList() {
    }

    @Test
    public void testGetHighestScoreByUser() {
    }
}