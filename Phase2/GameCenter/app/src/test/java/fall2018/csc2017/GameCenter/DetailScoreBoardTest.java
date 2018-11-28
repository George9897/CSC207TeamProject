package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DetailScoreBoardTest {

    private DetailScoreBoard detailScoreBoard;
    private Context context = new MockContext();
    private String gameType;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getBoardManager() {
    }

    @Test
    public void setBoardManager() {
    }

    @Test
    public void getSudokuBoardManager() {
    }

    @Test
    public void setSudokuBoardManager() {
    }

    @Test
    public void getMineManager() {
    }

    @Test
    public void setMineManager() {
    }

    @Test
    public void testDestroyAllManager() {
        // build Managers.
        // check Managers.
        // Destroy.
        // check Managers.
    }

    @Test
    public void setContext() {
    }

    @Test
    public void getContext() {
    }

    @Test
    public void getLevel() {
    }

    @Test
    public void setLevel() {
    }

    @Test
    public void getUsername() {
    }

    @Test
    public void setUsername() {
    }

    @Test
    public void getScore() {
    }

    @Test
    public void setScore() {
    }

    private void setUpSlidingTileScoreBoard(){
        gameType = "SlidingTile";
        detailScoreBoard = new DetailScoreBoard(gameType, context);
    }

    private void setUpSlidingTileManager(int score, int level, String userName){
        BoardManager slidingTileManager = new BoardManager(context, level);
        slidingTileManager.setScore(score);
        slidingTileManager.setUserName(userName);
        SlidingTileMovementController mController = new SlidingTileMovementController();
        mController.setBoardManager(slidingTileManager);
        mController.saveToFile(StartingActivity.slidingFile, context);
    }

    @Test
    public void testEasySlidingTileCollectScoreLevelScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 3, testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testEasySlidingTileCollectScoreLevelLevel() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 3, testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testEasySlidingTileCollectScoreLevelUserName() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 3, testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testUserName, detailScoreBoard.getUsername());
    }
    @Test
    public void testMediumSlidingTileCollectScoreLevelScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 4, testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testMediumSlidingTileCollectScoreLevelLevel() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 4, testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testMediumSlidingTileCollectScoreLevelUserName() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 4, testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testUserName, detailScoreBoard.getUsername());
    }
    @Test
    public void testHardSlidingTileCollectScoreLevelScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 5, testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testHardSlidingTileCollectScoreLevelLevel() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 5, testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testHardSlidingTileCollectScoreLevelUserName() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 5, testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testUserName, detailScoreBoard.getUsername());
    }

    private void setUpMineScoreBoard(){
        gameType = "Mine";
        detailScoreBoard = new DetailScoreBoard(gameType, context);
    }

    private void setUpMineManager(int score, String level, String userName){
        MineManager mineManager = new MineManager(context, userName, level);
        mineManager.setScore(score);
        MineMovementController mController = new MineMovementController(context);
        mController.setMineManager(mineManager);
        mController.saveToFile(StartingActivity.mineFile, context);
    }

    @Test
    public void testEasyMineCollectScoreLevelScore() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Easy", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testEasyMineCollectScoreLevelLevel() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Easy", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testEasyMineCollectScoreLevelUserName() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Easy", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testUserName, detailScoreBoard.getUsername());
    }

    @Test
    public void testEasyMineCollectScoreLevelUpdateScore() {
    }

    @Test
    public void testMediumMineCollectScoreLevelScore() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Medium", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testMediumMineCollectScoreLevelLevel() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Medium", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testMediumMineCollectScoreLevelUserName() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Medium", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testUserName, detailScoreBoard.getUsername());
    }

    @Test
    public void testMediumMineCollectScoreLevelUpdateScore() {
    }

    @Test
    public void testHardMineCollectScoreLevelScore() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Hard", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testHardMineCollectScoreLevelLevel() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Hard", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testHardMineCollectScoreLevelUserName() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Hard", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testUserName, detailScoreBoard.getUsername());
    }

    @Test
    public void testHardMineCollectScoreLevelUpdateScore() {
    }

    private void setUpSudokuScoreBoard(){
        gameType = "Sudoku";
        detailScoreBoard = new DetailScoreBoard(gameType, context);
    }

    private void setUpSudokuManager(int score, String level, String userName){
        SudokuBoardManager sudokuManager = new SudokuBoardManager(context, level);
        sudokuManager.setScore(score);
        sudokuManager.setUserName(userName);
        SudokuMovementController mController = new SudokuMovementController();
        mController.setSudokuBoardManager(sudokuManager);
        mController.saveToFile(StartingActivity.sudokuFile, context);
    }

    @Test
    public void testEasySudokuCollectScoreLevelScore() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testEasySudokuCollectScoreLevelLevel() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testEasySudokuCollectScoreLevelUserName() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testUserName, detailScoreBoard.getUsername());
    }

    @Test
    public void testEasySudokuCollectScoreLevelUpdateScore() {
    }

    @Test
    public void testMediumSudokuCollectScoreLevelScore() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Medium", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testMediumSudokuCollectScoreLevelLevel() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Medium", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testMediumSudokuCollectScoreLevelUserName() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Medium", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testUserName, detailScoreBoard.getUsername());
    }

    @Test
    public void testMediumSudokuCollectScoreLevelUpdateScore() {
    }

    @Test
    public void testHardSudokuCollectScoreLevelScore() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Hard", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testHardSudokuCollectScoreLevelLevel() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Hard", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testHardSudokuCollectScoreLevelUserName() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Hard", testUserName);
        detailScoreBoard.collectScoreLevel();
        assertEquals(testUserName, detailScoreBoard.getUsername());
    }

    @Test
    public void testHardSudokuCollectScoreLevelUpdateScore() {
    }

    @Test
    public void testEasySlidingTileCreateSortedList() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 3, testUserName);
        detailScoreBoard.createSortedList();
        assertTrue(detailScoreBoard.getEasyScoreList().contains(testScore));
    }
    //TODO: Medium, Hard tests.

    @Test
    public void testEasyMineCreateSortedList() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Easy", testUserName);
        detailScoreBoard.createSortedList();
        assertTrue(detailScoreBoard.getEasyScoreList().contains(testScore));
    }
    //TODO: Medium, Hard tests.


    @Test
    public void testEasySudokuCreateSortedList() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        detailScoreBoard.createSortedList();
        assertTrue(detailScoreBoard.getEasyScoreList().contains(testScore));
    }
    //TODO: Medium, Hard tests.

    @Test
    public void getEasyLevel() {
    }

    @Test
    public void setEasyLevel() {
    }

    @Test
    public void getEasyTopOneName() {
    }

    @Test
    public void setEasyTopOneName() {
    }

    @Test
    public void getEasyTopOneScore() {
    }

    @Test
    public void setEasyTopOneScore() {
    }

    @Test
    public void getEasyScoreList() {
    }

    @Test
    public void setEasyScoreList() {
    }

    @Test
    public void testSlidingTileModifyEasyTopOneNeverPlayed() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setEasyScoreList(null);
        assertEquals("neverPlayed", detailScoreBoard.getEasyLevel());
    }

    @Test
    public void testSlidingTileModifyEasyTopOnePlayed() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 3, testUserName);
        assertEquals("played", detailScoreBoard.getEasyLevel());
    }

    @Test
    public void testSlidingTileModifyEasyTopOneLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setLevel("neverPlayed");
        assertEquals("No data", detailScoreBoard.getEasyTopOne());
    }

    @Test
    public void testSlidingTileModifyEasyTopOneEasyLevelNoData() {
        setUpSlidingTileScoreBoard();
        // getEasyLevel().equals("neverPlayed")
        detailScoreBoard.setEasyLevel("neverPlayed");
        assertEquals("No data", detailScoreBoard.getEasyTopOne());
    }
    // TODO: findTopOne(getEasyTopOneScore(), getEasyTopOneName(),getScore(), getUsername()) == null
    // TODO: test Mine, Sodoku


    @Test
    public void getMediumLevel() {
    }

    @Test
    public void setMediumLevel() {
    }

    @Test
    public void getMediumTopOneName() {
    }

    @Test
    public void setMediumTopOneName() {
    }

    @Test
    public void getMediumTopOneScore() {
    }

    @Test
    public void setMediumTopOneScore() {
    }

    @Test
    public void getMediumScoreList() {
    }

    @Test
    public void setMediumScoreList() {
    }

    @Test
    public void testSlidingTileModifyMediumTopOneNeverPlayed() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setMediumScoreList(null);
        assertEquals("neverPlayed", detailScoreBoard.getMediumLevel());
    }

    @Test
    public void testSlidingTileModifyMediumTopOnePlayed() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 4, testUserName);
        assertEquals("played", detailScoreBoard.getMediumLevel());
    }

    @Test
    public void testSlidingTileModifyMediumTopOneLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setLevel("neverPlayed");
        assertEquals("No data", detailScoreBoard.getMediumTopOne());
    }

    @Test
    public void testSlidingTileModifyMediumTopOneEasyLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setMediumLevel("neverPlayed");
        assertEquals("No data", detailScoreBoard.getMediumTopOne());
    }
    // TODO: findTopOne(getEasyTopOneScore(), getMediumTopOneName(),getScore(), getUsername()) == null
    // TODO: test Mine, Sodoku

    @Test
    public void getHardLevel() {
    }

    @Test
    public void setHardLevel() {
    }

    @Test
    public void getHardTopOneScore() {
    }

    @Test
    public void setHardTopOneScore() {
    }

    @Test
    public void getHardTopOneName() {
    }

    @Test
    public void setHardTopOneName() {
    }

    @Test
    public void getHardScoreList() {
    }

    @Test
    public void setHardScoreList() {
    }

    @Test
    public void testSlidingTileHardMediumTopOneNeverPlayed() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setHardScoreList(null);
        assertEquals("neverPlayed", detailScoreBoard.getHardLevel());
    }

    @Test
    public void testSlidingTileModifyHardTopOnePlayed() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 5, testUserName);
        assertEquals("played", detailScoreBoard.getHardLevel());
    }

    @Test
    public void testSlidingTileModifyHardTopOneLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setLevel("neverPlayed");
        assertEquals("No data", detailScoreBoard.getHardTopOne());
    }

    @Test
    public void testSlidingTileModifyHardTopOneEasyLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setHardLevel("neverPlayed");
        assertEquals("No data", detailScoreBoard.getHardTopOne());
    }
    // TODO: findTopOne(getEasyTopOneScore(), getHardTopOneName(),getScore(), getUsername()) == null
    // TODO: test Mine, Sodoku


    @Test
    public void getEasyTopOne() {
    }

    @Test
    public void getMediumTopOne() {
    }

    @Test
    public void getHardTopOne() {
    }

    @Test
    public void getEasyMap() {
    }

    @Test
    public void setEasyMap() {
    }

    @Test
    public void getGameType() {
    }

    @Test
    public void setGameType() {
    }

    @Test
    public void getEasySortedList() {
    }

    @Test
    public void getMediumMap() {
    }

    @Test
    public void setMediumMap() {
    }

    @Test
    public void getMediumSortedList() {
    }

    @Test
    public void getHardMap() {
    }

    @Test
    public void setHardMap() {
    }

    @Test
    public void getHardSortedList() {
    }

    @Test
    public void testGetHighestScoreByUser() {
    }
}