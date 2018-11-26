package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardManagerTest {
    /**
     * The Boardmanager for test.
     */
    private BoardManager boardManager;
    /**
     * Context for test.
     */
    private Context context;

    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
//    private List createTiles() {
//        List<Tile> tiles = new ArrayList<>();
//        final int numTiles = boardManager.getLevel() * boardManager.getLevel();
//        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
//            if (tileNum == numTiles - 1) {
//                tiles.add(new Tile(0));
//            } else {
//                tiles.add(new Tile(tileNum + 1));
//            }
//        }
//        return tiles;
//    }

    @Before
    public void setUp() throws Exception {
        context = new MockContext();
        boardManager = new BoardManager(context, 3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getSlidingTile() {
    }

    @Test
    public void getUndoLimit() {
    }

    @Test
    public void getUndoLimit3() {
    }

    @Test
    public void createTiles() {
    }

    @Test
    public void getNumMoves() {
    }

    @Test
    public void getScore() {
    }

    @Test
    public void getSlidingTileDifficulty() {
    }

    @Test
    public void setSlidingTileDifficulty() {
    }

    @Test
    public void puzzleSolved() {
    }

    @Test
    public void isValidTap() {
    }

    @Test
    public void touchMove() {
    }

    @Test
    public void undo() {
    }

    @Test
    public void undo3() {
    }
}