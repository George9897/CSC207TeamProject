package fall2018.csc2017.GameCenter;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Manage a slidingTile, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable, Undoable {
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
     * The scorer for sliding tile.
     */
    private SlidingTileScorer scorer = new SlidingTileScorer();

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
            //Collections.shuffle(tiles)
            this.slidingTile = new SlidingTile(tiles);
            solvableShuffle();
        }
    }

    /**
     * Shuffle tiles while gurantees a solution.
     */
     private void solvableShuffle(){
        // TODO: implement this function!
        // Constant for swaping directions.
        int level = SlidingTile.level;
        int left = -1;
        int right = 1;
        int up = -(level);

        int k = 0;
        //int blankID = 0;
        int bPosi = (level)*(level)-1;
        // Random choose i
        Random r1 = new Random();
        int i = 50 + r1.nextInt(50);
        ArrayList history = new ArrayList();
        while (k <= i){
            ArrayList swapChoices = new ArrayList();
            int random_direction;
            int row = bPosi / level;
            int col = bPosi % level;;
            Tile above = row == 0 ? null : slidingTile.getTile(row - 1, col);
            Tile below = row == level - 1 ? null : slidingTile.getTile(row + 1, col);
            Tile lefT = col == 0 ? null : slidingTile.getTile(row, col - 1);
            Tile righT = col == level - 1 ? null : slidingTile.getTile(row, col + 1);
            if (above != null){
                swapChoices.add(up);
            }
            if (below != null){
                swapChoices.add(level);
            }
            if (lefT != null){
                swapChoices.add(left);
            }
            if (righT != null){
                swapChoices.add(right);
            }
            // Random choose an element from swapChoices, then swap them.
            Random r2 = new Random();
            int c = (int)(swapChoices.get(r2.nextInt(swapChoices.size())));
            history.add(c);
            int d = bPosi + c;
            this.slidingTile.swapTiles(row, col, d / level, d % level);
            bPosi = d;
            k++;
            System.out.println(bPosi);
        }
        System.out.println(history);

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
            if (next.getId() == 0 && acc == slidingTile.numTiles() && countTrue ==
                    slidingTile.numTiles() - 1) {
                solved = true;
            }
            acc++;
        }
        if (solved) {
            score = scorer.calculateScore(slidingTile.getLevel(), getNumMoves());
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

    private int row;
    private int col;
    private int blankId;

    /**
     * Process a touch at position in the slidingTile, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        row = position / SlidingTile.level;
        col = position % SlidingTile.level;
        blankId = 0;
        if (isValidTap(position)) {
            makeMove();
        }
    }
    
    void makeMove(){
        numMoves++;
        undoLimit++;
        if (row != SlidingTile.level - 1 && (slidingTile.getTile(row + 1, col)).getId() ==
                blankId) {
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
        if (col != SlidingTile.level - 1 && (slidingTile.getTile(row, col + 1)).getId() ==
                blankId) {
            slidingTile.swapTiles(row, col, row, col + 1);
            addPosition((row) * SlidingTile.level + col + 1);
        }
    }

    /**
     * Undo the previous moves as required properly.
     */
    public void undo() {
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
