package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MineManagerTest {

    Context context = new MockContext();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetLose() {
        MineManager mineManager = new MineManager(context);
        assertFalse(mineManager.getLose());
        mineManager.setLose();
        assertTrue(mineManager.getLose());
    }

    @Test
    public void testSetLose() {
        MineManager mineManager = new MineManager(context);
        assertFalse(mineManager.getLose());
        mineManager.setLose();
        assertTrue(mineManager.getLose());
    }

    @Test
    public void testGetTime() {
        MineManager mineManager = new MineManager(context);
        mineManager.setTime(0);
        assertEquals(0 , mineManager.getTime());
    }

    @Test
    public void testSetTime(){
        MineManager mineManager = new MineManager(context);
        mineManager.setTime(0);
        assertEquals(0 , mineManager.getTime());
    }

    @Test
    public void testGetScore() {
        MineManager mineManager = new MineManager(context);
        mineManager.setScore(0);
        assertEquals(0 , mineManager.getScore());
    }

    @Test
    public void testSetScore() {
        MineManager mineManager = new MineManager(context);
        mineManager.setScore(0);
        assertEquals(0 , mineManager.getScore());
    }

    @Test
    public void testGetMineBoard() {
    }

    @Test
    public void testGetUserName() {
    }

    @Test
    public void testGetContext() {
    }

    @Test
    public void testGetMineDifficulty() {
    }

    @Test
    public void testIsFirstTap() {
    }

    @Test
    public void testGetMineTiles() {
    }

    @Test
    public void testSetMineDifficulty() {
    }

    @Test
    public void testSetFirstTapToFalse() {
    }

    @Test
    public void testCreateTiles() {
    }

    @Test
    public void testPuzzleSolved() {
    }

    @Test
    public void testIsValidTap() {
    }

    @Test
    public void testFailing() {
    }

    @Test
    public void testWinning() {
    }

    @Test
    public void testSetUserName() {
    }
}