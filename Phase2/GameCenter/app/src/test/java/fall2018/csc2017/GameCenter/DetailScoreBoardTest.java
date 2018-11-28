package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public void testGetBoardManager() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 4, testUserName);
        assertNotNull(detailScoreBoard.getBoardManager());
    }

    @Test
    public void testSetBoardManager() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 4, testUserName);
        assertNull(detailScoreBoard.getBoardManager());
    }

    @Test
    public void getSudokuBoardManager() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        assertNotNull(detailScoreBoard.getSudokuBoardManager());
    }

    @Test
    public void setSudokuBoardManager() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        detailScoreBoard.setSudokuBoardManager(null);
        assertNull(detailScoreBoard.getSudokuBoardManager());
    }

    @Test
    public void getMineManager() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Medium", testUserName);
        assertNotNull(detailScoreBoard.getMineManager());
    }

    @Test
    public void setMineManager() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Medium", testUserName);
        detailScoreBoard.setMineManager(null);
        assertNull(detailScoreBoard.getMineManager());
    }

    @Test
    public void testDestroySlidingTileManager() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSlidingTileManager(testScore, 4, testUserName);
        detailScoreBoard.destroyAllManager();
        assertNull(detailScoreBoard.getBoardManager());
    }

    @Test
    public void testDestroyMineManager() {
        setUpMineScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpMineManager(testScore, "Medium", testUserName);
        detailScoreBoard.destroyAllManager();
        assertNull(detailScoreBoard.getMineManager());
    }

    @Test
    public void testDestroySudokuManager() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        detailScoreBoard.destroyAllManager();
        assertNull(detailScoreBoard.getSudokuBoardManager());
    }

    @Test
    public void testSetContext() {
    }

    @Test
    public void testGetContext() {
    }

    @Test
    public void testGetLevel() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testSetLevel() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Hard", testUserName);
        detailScoreBoard.setLevel("Easy");
        assertEquals("Easy", detailScoreBoard.getLevel());
    }

    @Test
    public void testGetUsername() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Hard", testUserName);
        assertEquals("user", detailScoreBoard.getUsername());
    }

    @Test
    public void testSetUsername() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "tester";
        setUpSudokuManager(testScore, "Hard", testUserName);
        detailScoreBoard.setUsername("user");
        assertEquals("user", detailScoreBoard.getUsername());
    }

    @Test
    public void testGetScore() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        assertEquals(testScore, detailScoreBoard.getScore());
    }

    @Test
    public void testSetScore() {
        setUpSudokuScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        setUpSudokuManager(testScore, "Easy", testUserName);
        detailScoreBoard.setScore(10);
        assertEquals(10, detailScoreBoard.getScore());
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
    public void testGetEasyLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setEasyLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getEasyLevel());
    }

    @Test
    public void testSetEasyLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setEasyLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getEasyLevel());
    }

    @Test
    public void testGetEasyTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setEasyTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getEasyTopOneName());
    }

    @Test
    public void testSetEasyTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setEasyTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getEasyTopOneName());
    }

    @Test
    public void testGetEasyTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setEasyTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getEasyTopOneScore());
    }

    @Test
    public void testSetEasyTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setEasyTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getEasyTopOneScore());
    }

    @Test
    public void testGetEasyScoreList() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setEasyScoreList(null);
        assertNull(detailScoreBoard.getEasyScoreList());
    }

    @Test
    public void testSetEasyScoreList() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setEasyScoreList(null);
        assertNull(detailScoreBoard.getEasyScoreList());
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
    public void testGetMediumLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setMediumLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getMediumLevel());
    }

    @Test
    public void testSetMediumLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setMediumLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getMediumLevel());
    }

    @Test
    public void testGetMediumTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setMediumTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getMediumTopOneName());
    }

    @Test
    public void testSetMediumTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setMediumTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getMediumTopOneName());
    }

    @Test
    public void testGetMediumTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setMediumTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getMediumTopOneScore());
    }

    @Test
    public void testSetMediumTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setMediumTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getMediumTopOneScore());
    }

    @Test
    public void testGetMediumScoreList() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setMediumScoreList(null);
        assertNull(detailScoreBoard.getMediumScoreList());
    }

    @Test
    public void testSetMediumScoreList() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setMediumScoreList(null);
        assertNull(detailScoreBoard.getMediumScoreList());
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
    public void testGetHardLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setHardLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getHardLevel());
    }

    @Test
    public void testSetHardLevel() {
        setUpSudokuScoreBoard();
        String testLevel = "played";
        detailScoreBoard.setHardLevel(testLevel);
        assertEquals(testLevel, detailScoreBoard.getHardLevel());
    }

    @Test
    public void testGetHardTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setHardTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getHardTopOneName());
    }

    @Test
    public void testSetHardTopOneName() {
        setUpSlidingTileScoreBoard();
        String testUserName = "user";
        detailScoreBoard.setHardTopOneName(testUserName);
        assertEquals(testUserName, detailScoreBoard.getHardTopOneName());
    }

    @Test
    public void testGetHardTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setHardTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getHardTopOneScore());
    }

    @Test
    public void testSetHardTopOneScore() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        detailScoreBoard.setHardTopOneScore(testScore);
        assertEquals(testScore, detailScoreBoard.getHardTopOneScore());
    }

    @Test
    public void testGetHardScoreList() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setHardScoreList(null);
        assertNull(detailScoreBoard.getHardScoreList());
    }

    @Test
    public void testSetHardScoreList() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setHardScoreList(null);
        assertNull(detailScoreBoard.getHardScoreList());
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
    public void testGetEasyTopOne() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        String expectMassage = testScore + " " + testUserName;
        detailScoreBoard.setEasyTopOneName(testUserName);
        detailScoreBoard.setEasyTopOneScore(testScore);
        assertEquals(expectMassage, detailScoreBoard.getEasyTopOne());
    }

    @Test
    public void testGetMediumTopOne() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        String expectMassage = testScore + " " + testUserName;
        detailScoreBoard.setMediumTopOneName(testUserName);
        detailScoreBoard.setMediumTopOneScore(testScore);
        assertEquals(expectMassage, detailScoreBoard.getMediumTopOne());
    }

    @Test
    public void testGetHardTopOne() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        String expectMassage = testScore + " " + testUserName;
        detailScoreBoard.setMediumTopOneName(testUserName);
        detailScoreBoard.setMediumTopOneScore(testScore);
        assertEquals(expectMassage, detailScoreBoard.getMediumTopOne());
    }

    @Test
    public void testGetEasyMap() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        List<String> testList = new ArrayList<>();
        testList.add(testUserName);
        detailScoreBoard.getEasyMap().put(testScore, testList);
        assertTrue(detailScoreBoard.getEasyMap().containsKey(testScore));
        assertEquals(testList, detailScoreBoard.getEasyMap().get(testScore));
    }

    @Test
    public void getGameType() {
        setUpSlidingTileScoreBoard();
        assertEquals("SlidingTile", detailScoreBoard.getGameType());
    }

    @Test
    public void getEasySortedList() {
    }


    @Test
    public void getMediumMap() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        List<String> testList = new ArrayList<>();
        testList.add(testUserName);
        detailScoreBoard.getMediumMap().put(testScore, testList);
        assertTrue(detailScoreBoard.getMediumMap().containsKey(testScore));
        assertEquals(testList, detailScoreBoard.getMediumMap().get(testScore));
    }

    @Test
    public void getMediumSortedList() {
    }

    @Test
    public void getHardMap() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        List<String> testList = new ArrayList<>();
        testList.add(testUserName);
        detailScoreBoard.getHardMap().put(testScore, testList);
        assertTrue(detailScoreBoard.getHardMap().containsKey(testScore));
        assertEquals(testList, detailScoreBoard.getHardMap().get(testScore));
    }

    @Test
    public void getHardSortedList() {
    }

    @Test
    public void testGetHighestScoreByUser() {
        String testUserName = "user";
        int testScore;

        setUpSlidingTileScoreBoard();
        testScore = 100;
        setUpSlidingTileManager(testScore, 3, testUserName);
        detailScoreBoard.collectScoreLevel();

        setUpSlidingTileScoreBoard();
        testScore = 1000;
        setUpSlidingTileManager(testScore, 4, testUserName);
        detailScoreBoard.collectScoreLevel();

        setUpSlidingTileScoreBoard();
        testScore = 10000;
        setUpSlidingTileManager(testScore, 5, testUserName);
        detailScoreBoard.collectScoreLevel();

        assertEquals(10000, detailScoreBoard.getHighestScoreByUser(testUserName));
    }
}