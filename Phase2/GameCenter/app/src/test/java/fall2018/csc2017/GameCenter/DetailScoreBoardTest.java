package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DetailScoreBoardTest {

    private DetailScoreBoard detailScoreBoard;

    private Context context = new MockContext();

    private String SlidingGameType = "SlidingTile";

    private String MineGameType = "Mine";

    private String SudokuGameType = "Sudoku";


    @Before
    public void setUp() throws Exception {
    }

    private void testSetUpSlidingTile(){
        detailScoreBoard = new DetailScoreBoard(SlidingGameType, context);
        // score: {1: ["Ace"], 2: ["Bella"], 3: ["Cara"]}
    }

    private void testSetUpMine(){
        detailScoreBoard = new DetailScoreBoard(MineGameType, context);
        // score: {4: ["David"], 5: ["Ella"], 6: ["Fly"]}
    }

    private void testSetUpSudoku(){
        detailScoreBoard = new DetailScoreBoard(SudokuGameType, context);
        // score: {7: ["Gary"], 8: ["Ham"], 9: ["Irene"]}
    }

    @After
    public void tearDown() throws Exception {
        detailScoreBoard.destroyAllManager();
        detailScoreBoard = null;
    }

    @Test
    public void testDestroyAllManager() {
        testSetUpMine();
        // test detailScoreBoard contain MineManager.
        detailScoreBoard.destroyAllManager();
        // test detailScoreBoard do not contain MineManager.(== null)

    }

    @Test
    public void testSetContext() {
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