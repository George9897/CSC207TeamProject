package fall2018.csc2017.GameCenter;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static fall2018.csc2017.GameCenter.SlidingTileScore.calculateScore;

/**
 * Manage a slidingTile, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {
    /**
     * The serialVersionUID.
     */
    public static final long serialVersionUID = 7738996747003692034L;

    /**
     * The slidingTile being managing
     */
    private SlidingTile slidingTile;

    /**
     * The boardManager.
     */
    private static BoardManager boardManager;

    /**
     * The number of moves taken by the users.
     */
    private int numMoves;

    /**
     * The list of storing Boards.
     */
    private List<Integer> listOfPosition;

    /**
     * The AccountManager.
     */
    private AccountManager accountManager = AccountManager.getAccountManager();

    /**
     * The user's name.
     */
    String userName = accountManager.getUserName();

    /**
     * The context used to connect to activity.
     */
    private transient Context context;

    /**
     * After score for one game round.
     */
    private int score;

    /**
     * The undo limitation.
     */
    private int undoLimit;

    /**
     * The undo limitation (Only 3 times allowed)
     */
    private int undoLimit3;

    /**
     * Getter for numMoves.
     *
     * @return numMoves.
     */
    int getNumMoves() {
        return numMoves;
    }

    /**
     * Getter for score.
     *
     * @return score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for slidingTile.
     *
     * @return the last slidingTile get stored.
     */
    SlidingTile getSlidingTile() {
        return this.slidingTile;
    }

    /**
     * @return The undoLimit of this game for unlimited method.
     */
    int getUndoLimit() {
        return undoLimit;
    }

    /**
     * @return The undoLimit of this game for limited method.
     */
    int getUndoLimit3() {
        return undoLimit3;
    }

    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    private List CreateTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = SlidingTile.level * SlidingTile.level;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            //TODO
            // tiles.add(new Tile(tileNum, false));
            if (tileNum == numTiles - 1) {
                tiles.add(new Tile(0, false));
            } else {
                tiles.add(new Tile(tileNum + 1, false));
            }
        }
        return tiles;
    }

    /**
     * Constructor for BoardManager.
     */
    private BoardManager(Context context) {
        this.context = context;
        if (this.listOfPosition == null) {
            this.undoLimit = 0;
            this.undoLimit3 = 3;
            this.numMoves = 0;
            this.listOfPosition = new ArrayList<>();
            List tiles = CreateTiles();
            Collections.shuffle(tiles);
            this.slidingTile = new SlidingTile(tiles);
        }
    }

    /**
     * Getter for singleton BoardManager.
     *
     * @param context The context used for connecting activity.
     * @return The singleton BoardManager.
     */
    static BoardManager getBoardManager(Context context) {
        if (boardManager == null) {
            boardManager = new BoardManager(context);
        }
        return boardManager;
    }

    /**
     * Destroy current BoardManager.
     */
    static void destroyBoardManager() {
        boardManager = null;
    }

    /**
     * Add a modified SlidingTile in the list of boards.
     *
     * @param position The position that was a blank tile.
     */
    private void addPosition(int position) {
        listOfPosition.add(position);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        Iterator<Tile> iter = slidingTile.iterator();
        int acc = 1;
        int countTrue = 0;
        while (iter.hasNext()) {
            Tile next = iter.next();
            if (next.getId() != acc) {
                solved = false;
            } else {
                countTrue += 1;
            }
            if (next.getId() == 0 && acc == slidingTile.numTiles() && countTrue == slidingTile.numTiles() - 1) {
                solved = true;
            }
            acc++;
        }
        if (solved) {
            score = calculateScore(boardManager.getSlidingTile().getLevel(), boardManager.getNumMoves());
            undoLimit = 0;
            ScoreBoard scoreBoard = ScoreBoard.getScoreBoard(context);
            scoreBoard.update(SlidingTile.level, userName, score);
            scoreBoard.updateHighestScore();
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / SlidingTile.level;
        int col = position % SlidingTile.level;
        int blankId = 0;
        Tile above = row == 0 ? null : slidingTile.getTile(row - 1, col);
        Tile below = row == SlidingTile.level - 1 ? null : slidingTile.getTile(row + 1, col);
        Tile left = col == 0 ? null : slidingTile.getTile(row, col - 1);
        Tile right = col == SlidingTile.level - 1 ? null : slidingTile.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the slidingTile, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / SlidingTile.level;
        int col = position % SlidingTile.level;
        int blankId = 0;
        if (isValidTap(position)) {
            numMoves++;
            undoLimit++;
            if (row != SlidingTile.level - 1 && (slidingTile.getTile(row + 1, col)).getId() == blankId) {
                slidingTile.swapTiles(row, col, row + 1, col);
                addPosition((row + 1) * SlidingTile.level + col);
            }
            if (row != 0 && (slidingTile.getTile(row - 1, col)).getId() == blankId) {
                slidingTile.swapTiles(row, col, row - 1, col);
                addPosition((row - 1) * SlidingTile.level + col);
            }
            if (col != 0 && (slidingTile.getTile(row, col - 1)).getId() == blankId) {
                slidingTile.swapTiles(row, col, row, col - 1);
                addPosition((row) * SlidingTile.level + col - 1);
            }
            if (col != SlidingTile.level - 1 && (slidingTile.getTile(row, col + 1)).getId() == blankId) {
                slidingTile.swapTiles(row, col, row, col + 1);
                addPosition((row) * SlidingTile.level + col + 1);
            }
        }
    }

    /**
     * Undo the previous moves as required properly.
     */
    void undo() {
        if (undoLimit > 0) {
            numMoves -= 2;
            undoLimit -= 2;
            touchMove(listOfPosition.remove(listOfPosition.size() - 1));
            listOfPosition.remove(listOfPosition.size() - 1);
        }
    }

    /**
     * Undo the previous move(can only be used 3 times).
     */
    void undo3() {
        if (undoLimit3 > 0) {
            numMoves -= 2;
            undoLimit3 -= 1;
            touchMove(listOfPosition.remove(listOfPosition.size() - 1));
            listOfPosition.remove(listOfPosition.size() - 1);
        }
    }
}
