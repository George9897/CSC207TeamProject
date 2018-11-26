package fall2018.csc2017.GameCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for mine scorer class.
 */
public class MineScorerTest {
    private MineScorer mineScorer;

    /**
     * Set up a scorer for testing it.
     */
    @Before
    public void setUp() {
        mineScorer = new MineScorer();
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        mineScorer = null;
    }

    /**
     * Test whether calculateScore works.
     */
    @Test
    public void testCalculateScore() {
        assertEquals(26000, mineScorer.calculateScore(26,0));
        assertEquals(24728, mineScorer.calculateScore(26, 10));
    }
}