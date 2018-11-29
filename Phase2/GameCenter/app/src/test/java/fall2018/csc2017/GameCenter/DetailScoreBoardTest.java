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

    private void setUpSlidingTileScoreBoard(){
        gameType = "SlidingTile";
        detailScoreBoard = new DetailScoreBoard(gameType, context);
    }

    private void setUpSudokuScoreBoard(){
        gameType = "Sudoku";
        detailScoreBoard = new DetailScoreBoard(gameType, context);
    }

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
        assertNull(detailScoreBoard.getEasyLevel());
    }

    @Test
    public void testSlidingTileModifyEasyTopOneLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setLevel("neverPlayed");
        assertEquals("", detailScoreBoard.getEasyTopOneName());
    }

    @Test
    public void testSlidingTileModifyEasyTopOneEasyLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setEasyLevel("neverPlayed");
        assertEquals("", detailScoreBoard.getEasyTopOneName());
    }

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
        assertNull(detailScoreBoard.getMediumLevel());
    }

    @Test
    public void testSlidingTileModifyMediumTopOneLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setLevel("neverPlayed");
        assertEquals("", detailScoreBoard.getMediumTopOneName());
    }

    @Test
    public void testSlidingTileModifyMediumTopOneEasyLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setMediumLevel("neverPlayed");
        assertEquals("", detailScoreBoard.getMediumTopOneName());
    }

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
        assertNull(detailScoreBoard.getHardLevel());
    }

    @Test
    public void testSlidingTileModifyHardTopOneLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setLevel("neverPlayed");
        assertEquals("", detailScoreBoard.getHardTopOneName());
    }

    @Test
    public void testSlidingTileModifyHardTopOneEasyLevelNoData() {
        setUpSlidingTileScoreBoard();
        detailScoreBoard.setHardLevel("neverPlayed");
        assertEquals("", detailScoreBoard.getHardTopOneName());
    }

    @Test
    public void testGetEasyTopOne() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        String expectMassage = testScore + "  " + testUserName;
        detailScoreBoard.setEasyTopOneName(testUserName);
        detailScoreBoard.setEasyTopOneScore(testScore);
        assertEquals(expectMassage, detailScoreBoard.getEasyTopOne());
    }

    @Test
    public void testGetMediumTopOne() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        String expectMassage = testScore + "  " + testUserName;
        detailScoreBoard.setMediumTopOneName(testUserName);
        detailScoreBoard.setMediumTopOneScore(testScore);
        assertEquals(expectMassage, detailScoreBoard.getMediumTopOne());
    }

    @Test
    public void testGetHardTopOne() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        String expectMassage = testScore + "  " + testUserName;
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
    public void testGetMediumMap() {
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
    public void testGetHardMap() {
        setUpSlidingTileScoreBoard();
        int testScore = 10000;
        String testUserName = "user";
        List<String> testList = new ArrayList<>();
        testList.add(testUserName);
        detailScoreBoard.getHardMap().put(testScore, testList);
        assertTrue(detailScoreBoard.getHardMap().containsKey(testScore));
        assertEquals(testList, detailScoreBoard.getHardMap().get(testScore));
    }
}