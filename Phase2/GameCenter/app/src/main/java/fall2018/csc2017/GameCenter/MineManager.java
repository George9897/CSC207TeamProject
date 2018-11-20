package fall2018.csc2017.GameCenter;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

/**
 * The Mine game manager.
 */
public class MineManager implements Manager {

    /**
     * The Mine board.
     */
    private MineBoard mineBoard;
    /**
     * The AccountManager.
     */
    private AccountManager accountManager = AccountManager.getAccountManager();

    /**
     * The user's name.
     */
    private String userName = accountManager.getUserName();
    /**
     * The mark of whether the user tapped for at least once.
     */
    private boolean firstTap = true;

    /**
     * The context.
     */
    private Context context;

    /**
     *
     */
    private String mineDifficulty;

    public String getMineDifficulty() {
        return mineDifficulty;
    }

    public void setMineDifficulty(String mineDifficulty) {
        this.mineDifficulty = mineDifficulty;
    }

    /**
     * The number of booms in one game play.
     */
    private static int numBoom;
    /**
     * The score after the user find out all the booms.
     */
    private int score;
    /**
     * The time passed as seconds.
     */
    private int time;
    /**
     * The timer.
     */
    private Timer timer = new Timer();
    /**
     * The scorer for Mine game.
     */
    private MineScorer scorer = new MineScorer();
    /**
     * The singleton mine Manager.
     */
    private static MineManager mineManager;


    public List<MineTile> mineTiles = new ArrayList<>();


    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    public List createTiles() {
        List<MineTile> mineTiles = new ArrayList<>();
        int numTiles = MineBoard.getSize() * MineBoard.getSize();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            mineTiles.add(new MineTile(0, false));
        }
        return mineTiles;
    }
    /**
     * The constructor of MineManager.
     */
    MineManager(Context context) {
        this.context = context;
        this.mineTiles = createTiles();
        this.mineBoard = new MineBoard(mineTiles, numBoom, new Random());
        timer.schedule(scorer, 0, 1000);
    }

    /**
     * Getter for singleton Mine Manager.
     *
     * @param context The context.
     * @return mineManager.
     */
    static MineManager getMineManager(Context context) {
        if (mineManager == null) {
            mineManager = new MineManager(context);
        }
        return mineManager;
    }

    /**
     * Destroy for new singleton Mine Manager.
     *
     * @return a new mineManager.
     */
    static void destroyMineManager() {
        mineManager = null;
    }

    /**
     * Getter for the time passed.
     *
     * @return The time.
     */
    public int getTime() {
        return time;
    }

    /**
     * Getter for the score.
     *
     * @return score.
     */
    @Override
    public int getScore() {
        return score;
    }
    /**
     * Getter for mine Board.
     */
    MineBoard getMineBoard() {return mineBoard; }

    String getUserName() {
        return userName;
    }
    public Context getContext() {
        return context;
    }

    /**
     * Setter for numBoom.
     *
     * @param numBoom the wanted number of booms.
     */
    static void setNumBoom(int numBoom) { MineManager.numBoom = numBoom; }


    /**
     * Return true if puzzle is solved, false otherwise.
     *
     * @return is the puzzle solved.
     */
    @Override
    public boolean puzzleSolved() {
        int count = 0;
        for (int row = 0; row < MineBoard.getSize(); row++) {
            for (int col = 0; col < MineBoard.getSize(); col++) {
                if (!mineBoard.getMineTile(row, col).isOpened()) {
                    count++;
                }
                if (!mineBoard.getMineTile()[row][col].isOpened() &&
                        mineBoard.getMineTile(row, col).getValue() != -1) {
                    return false;
                }
            }
        }
        return count == numBoom;
    }

    /**
     * Move maker in Mine game.
     */
    public void makeMove(int position){
        if (isValidTap(position)) {
            mineBoard.touchOpen(position, firstTap);
            firstTap = false;
            failing(position);
            winning();
        }
    }

    /**
     * Check touch event is in the range.
     *
     * @return True if x and y are in the range, false otherwise.
     */
    boolean isValidTap(int position) {
        int row = position / MineBoard.getSize();
        int col = position % MineBoard.getSize();
        return !mineBoard.getMineTile()[row][col].isOpened();
    }

    /**
     * Game failing.
     */
    private void failing(int position) {
        int row = position / MineBoard.getSize();
        int col = position % MineBoard.getSize();
        if (mineBoard.getMineTile(row, col).getValue() == -1) {
            time = scorer.getTimeScore();
            score = 0;
            timer.cancel();
        }
    }

    /**
     * Game winning.
     */
    private void winning() {
        if (puzzleSolved()) {
            time = scorer.getTimeScore();
            score = scorer.calculateScore(numBoom, time);
            timer.cancel();
        }
    }
}
