package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.util.Pair;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Test for Mine board class.
 */
public class MineBoardTest {
    /**
     * The mine board for test.
     */
    private MineBoard mineBoard;

    /**
     * The surrounding_directions.
     */
    private int[][] surrounding_directions = {
            {-1, 1},//upper-left
            {0, 1},//upper
            {1, 1},//upper-right
            {-1, 0},//left
            {1, 0},//right
            {-1, -1},//lower-left
            {0, -1},//lower
            {1, -1}};//lower-right

    /**
     * The expected opened tiles used in test touchOpen.
     */
    private int expectedOpenedTiles;

    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    private List<MineTile> createTiles() {
        List<MineTile> mineTiles = new ArrayList<>();
        int numTiles = MineBoard.getSize() * MineBoard.getSize();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            mineTiles.add(new MineTile(0, false));
        }
        return mineTiles;
    }

    /**
     * Set up the mine board for test.
     */
    @Before
    public void setUp() {
        mineBoard = new MineBoard(createTiles(), 26, new Random());
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
        mineBoard = null;
    }

    /**
     * Test whether getSize works.
     */
    @Test
    public void testGetSize() {
        setUp();
        assertEquals(16, MineBoard.getSize());
    }

    /**
     * Test whether getNumber works.
     */
    @Test
    public void testGetNumBoom() {
        setUp();
        assertEquals(26, mineBoard.getNumBoom());
    }

    /**
     * Test whether getMineTile works.
     */
    @Test
    public void testGetMineTile() {
        setUp();
        int row = 8;
        int col = 8;
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
    }

    /**
     * Test whether setNumBoom works.
     */
    @Test
    public void testSetNumBoom() {
        setUp();
        assertEquals(26, mineBoard.getNumBoom());
        mineBoard.setNumBoom(52);
        assertEquals(52, mineBoard.getNumBoom());
    }

    /**
     * Test whether SetIsFirstTapToFalse works.
     */
    @Test
    public void testSetIsFirstTapToFalse() {
        setUp();
        assertTrue(mineBoard.isFirstTap());
        mineBoard.setFirstTapToFalse();
        assertFalse(mineBoard.isFirstTap());
    }

    /**
     * Test whether touchOpen works.
     */
    @Test
    public void testTouchOpen() {
        setUp();

        mineBoard = new MineBoard(createTiles(), 1, new Random());
        int position = 0;

        assertEquals(0, testCreateBooms());
        assertFalse(mineBoard.getMineTile(0, 0).getIsOpened());

        mineBoard.touchOpen(position);

        assertEquals(2, testCreateBooms());
        assertTrue(mineBoard.getMineTile(0, 0).getIsOpened());
        assertEquals(2, testOpenedBooms());
        // test recursively open surrounding tiles when find a 0 value tile.
        int expectedOpen = getExpectedOpenedTile(position);
        int numOfOpenedTiles = testOpenedTiles();
        assertEquals(numOfOpenedTiles, expectedOpen);

        // test createBooms without first tap.
        mineBoard = new MineBoard(createTiles(), 1, new Random());
        assertEquals(0, testCreateBooms());
        mineBoard.touchOpen(position);
        assertEquals(0, testCreateBooms());
    }

    /**
     * Get expected opened Tile with the given position
     *
     * @param position the given position.
     * @return expected opened Tile
     */
    private int getExpectedOpenedTile(int position){
        setUp();
        int expectedOpenedTiles = 0;
        int row = position / MineBoard.getSize();
        int col = position % MineBoard.getSize();
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        putSurroundingOnQueue(row, col, queue);
        recursiveSurroundingOnQueue(queue);
        return expectedOpenedTiles;
    }


    /**
     * Test createBooms().
     *
     * @return num of booms are created.
     */
    private int testCreateBooms(){
        setUp();
        int numberOfBooms = 0;
        for (int row = 0; row < MineBoard.getSize(); row++) {
            for (int col = 0; col < MineBoard.getSize(); col++) {
                if (mineBoard.getMineTile(row, col).getValue() == -1) {
                    numberOfBooms++;
                }
            }
        }
        return numberOfBooms;
    }

    /**
     * Test displayBooms().
     *
     * @return num of booms are opened.
     */
    private int testOpenedBooms(){
        setUp();
        int numberOfDisplay = 0;
        for (int boomRow = 0; boomRow < MineBoard.getSize(); boomRow++) {
            for (int boomCol = 0; boomCol < MineBoard.getSize(); boomCol++) {
                if (mineBoard.getMineTile(boomRow, boomCol).getIsOpened()){
                    numberOfDisplay++;
                }
            }
        }
        return numberOfDisplay;
    }

    /**
     * Test opened tile.
     *
     * @return num of tile are opened.
     */
    private int testOpenedTiles(){
        setUp();
        int numberOfOpenedTiles = 0;
        for (int Row = 0; Row < MineBoard.getSize(); Row++) {
            for (int Col = 0; Col < MineBoard.getSize(); Col++) {
                if (mineBoard.getMineTile(Row, Col).getValue() == -1) {
                    if (mineBoard.getMineTile(Row, Col).getIsOpened()){
                        numberOfOpenedTiles++;
                    }
                }
            }
        }
        return numberOfOpenedTiles;
    }

    /**
     * Test whether replaceToFlag works.
     */
    @Test
    public void testReplaceToFlag() {
        setUp();
        int row = 8;
        int col = 8;
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertEquals(R.drawable.tile_closed, mineBoard.getMineTile(row, col).getBackground());
        mineBoard.replaceToFlag(row, col);
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertEquals(R.drawable.tile_flagged, mineBoard.getMineTile(row, col).getBackground());
        mineBoard.replaceToFlag(row, col);
        assertFalse(mineBoard.getMineTile(row, col).getIsOpened());
        assertEquals(0, mineBoard.getMineTile(row, col).getValue());
        assertEquals(R.drawable.tile_closed, mineBoard.getMineTile(row, col).getBackground());
    }

    /**
     * Recursively put surrounding empty mine tiles into the queue.
     *
     * @param queue the queue that stores all the surrounding empty mine tiles.
     *              Note: the "surrounding" might recursively gets bigger until there is a number
     *              tile border.
     */
    private void recursiveSurroundingOnQueue
    (Queue<Pair<Integer, Integer>> queue) {
        if (queue.size() > 0) {
            Pair<Integer, Integer> pointPair = queue.poll();
            if (pointPair.first != null && pointPair.second != null) {
                int row = pointPair.first;
                int col = pointPair.second;
                expectedOpenedTiles++;
                putSurroundingOnQueue(row, col, queue);
                recursiveSurroundingOnQueue(queue);
            }
        }
    }

    /**
     * Change the surrounding empty mine tiles in the queue.
     * Helper for recursiveSurroundingOnQueue.
     *
     * @param row   The row of the mine tile.
     * @param col   The col of the mine tile.
     * @param queue The queue of given mine tiles.
     */
    private void putSurroundingOnQueue
    (int row, int col, Queue<Pair<Integer, Integer>> queue) {
        for (int i = 0; i < 8; i++) {
            Integer surroundingX = row + surrounding_directions[i][0],
                    surroundingY = col + surrounding_directions[i][1];
            //Check given minePoint's surroundings and whether they are opened or not.
            boolean isOpenable = surroundingX >= 0 && surroundingX < MineBoard.getSize() &&
                    surroundingY >= 0 && surroundingY < MineBoard.getSize();
            if (isOpenable) {
                //Make all surroundings empty tiles appear inside a number tile border.
                if (mineBoard.getMineTile(surroundingX, surroundingY).getValue() == 0 &&
                        !mineBoard.getMineTile(surroundingX, surroundingY).getIsOpened()) {
                    expectedOpenedTiles++;
                    queue.offer(new Pair<>(surroundingX, surroundingY));
                    //Show the number tile.
                } else if (mineBoard.getMineTile(surroundingX, surroundingY).getValue() > 0) {
                    expectedOpenedTiles++;
                }
            }
        }
    }
}