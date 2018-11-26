package fall2018.csc2017.GameCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SlidingTileScorerTest {
    /**
     * The SlidingTileScorer for test.
     */
    private SlidingTileScorer slidingTileScorer;

    @Before
    public void setUp() throws Exception { slidingTileScorer = new SlidingTileScorer();
    }

    @After
    public void tearDown() throws Exception { slidingTileScorer = null;
    }

    @Test
    public void calculateScore() {
        assertEquals(10000, slidingTileScorer.calculateScore(3,0));
        assertEquals(10000, slidingTileScorer.calculateScore(4, 100));
        assertEquals(1000000, slidingTileScorer.calculateScore(5, 100));
    }
}