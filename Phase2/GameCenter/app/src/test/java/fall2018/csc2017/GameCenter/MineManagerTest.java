package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test mine manager test.
 */
public class MineManagerTest {

    /**
     * The mocked context for mine manager test.
     */
    private Context context;
    /**
     * The mine manager for test.
     */
    private MineManager mineManager;

    /**
     * Set up a mine manager for test.
     */
    @Before
    public void setUp() {
        context = new MockContext();
        mineManager = new MineManager(context);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        mineManager = null;
    }

    /**
     * Test whether get lose works.
     */
    @Test
    public void testGetLose() {
        setUp();
        mineManager.setLose();
        assertTrue(mineManager.getLose());
    }

    /**
     * Test whether SetLose works.
     */
    @Test
    public void testSetLose() {
        setUp();
        assertFalse(mineManager.getLose());
        mineManager.setLose();
        assertTrue(mineManager.getLose());
    }

    /**
     * Test whether GetTime works.
     */
    @Test
    public void testGetTime() {
        setUp();
        assertEquals(0 , mineManager.getTime());
    }

    /**
     * Test whether SetTime works.
     */
    @Test
    public void testSetTime(){
        setUp();
        mineManager.setTime(0);
        assertEquals(0 , mineManager.getTime());
    }

    /**
     * Test whether GetScore works.
     */
    @Test
    public void testGetScore() {
        setUp();
        assertEquals(0 , mineManager.getScore());
    }

    /**
     * Test whether SetScore works.
     */
    @Test
    public void testSetScore() {
        setUp();
        mineManager.setScore(0);
        assertEquals(0 , mineManager.getScore());
    }

    /**
     * Test whether GetMineBoard works.
     */
    @Test
    public void testGetMineBoard() {
        setUp();
        assertFalse(mineManager.getMineBoard().getMineTile(15, 15).getIsOpened());
    }

    /**
     * Test whether tGetUserName works.
     */
    @Test
    public void testGetUserName() {
        setUp();
        assertNull(mineManager.getUserName());

    }

    /**
     * Test whether GetContext works.
     */
    @Test
    public void testGetContext() {
        setUp();
        assertEquals(context, mineManager.getContext());
    }

    /**
     * Test whether GetMineDifficulty works.
     */
    @Test
    public void testGetMineDifficulty() {
        setUp();
        assertNull(mineManager.getMineDifficulty());
    }

    /**
     * Test whether GetMineTiles works.
     */
    @Test
    public void testGetMineTiles() {
        setUp();
        assertEquals(256, mineManager.getMineTiles().size());
    }

    /**
     * Test whether SetMineDifficulty works.
     */
    @Test
    public void testSetMineDifficulty() {
        setUp();
        assertNull(mineManager.getMineDifficulty());
        mineManager.setMineDifficulty("Hard");
        assertEquals("Hard", mineManager.getMineDifficulty());
    }

    /**
     * Test whether CreateTiles works.
     */
    @Test
    public void testCreateTiles() {
        setUp();
        assertEquals(256, mineManager.getMineTiles().size());
    }

    /**
     * Test whether PuzzleSolved works.
     */
    @Test
    public void testPuzzleSolved() {
        setUp();
        assertFalse(mineManager.puzzleSolved());
        mineManager.getMineBoard().setNumBoom(1);
        mineManager.getMineBoard().touchOpen(0);
        for (int row = 0; row < MineBoard.getSize(); row++) {
            for (int col = 0; col < MineBoard.getSize(); col++) {
                if (mineManager.getMineBoard().getMineTile(row, col).getValue() == -1) {
                    mineManager.getMineBoard().replaceToFlag(row, col);
                } else {
                    mineManager.getMineBoard().replaceToTrue(row, col);
                }
            }
        }
        assertTrue(mineManager.puzzleSolved());
    }

    /**
     * Test whether IsValidTap works.
     */
    @Test
    public void testIsValidTap() {
        setUp();
        assertFalse(mineManager.getMineBoard().getMineTile(0,0).getIsOpened());
        assertTrue(mineManager.isValidTap(0));
        mineManager.getMineBoard().touchOpen(0);
        assertFalse(mineManager.isValidTap(0));
    }

    /**
     * Test whether Failing works.
     */
    @Test
    public void testFailing() {
        setUp();
        assertEquals(0, mineManager.getScore());
        mineManager.setScore(50000);
        assertEquals(50000, mineManager.getScore());
        mineManager.failing();
        assertEquals(0, mineManager.getScore());

    }

    /**
     * Test whether Winning works.
     */
    @Test
    public void testWinning() {
        setUp();
        for (int i = 0; i < 11; i++) {
            mineManager.scorer.run();
        }
        mineManager.getMineBoard().setNumBoom(10);
        mineManager.winning();
        assertEquals(11, mineManager.getTime());
        assertEquals(9463, mineManager.getScore());
    }

    /**
     * Test whether SetUserName works.
     */
    @Test
    public void testSetUserName() {
        setUp();
        assertNull(mineManager.getUserName());
        mineManager.setUserName("A");
        assertEquals("A", mineManager.getUserName());
    }
}