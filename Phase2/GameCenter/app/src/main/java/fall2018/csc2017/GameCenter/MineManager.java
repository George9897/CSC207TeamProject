package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;

/**
 * The Mine game manager.
 */
public class MineManager extends View implements Manager {

    /**
     * The Mine board.
     */
    private MineBoard mineBoard;
    /**
     * The mark of whether the user tapped for at least once.
     */
    private boolean tappedOnce = true;
    /**
     * The context.
     */
    private Context context;
    /**
     * The number of booms in one game play.
     */
    public static int numBoom;
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
     * The board height.
     */
    private final int ROW = 15;
    /**
     * The board width.
     */
    private final int COL = 8;

    /**
     * The mineTile's size.
     */
    private int TILE_WIDTH = 50;
    /**
     * The scorer for Mine game.
     */
    private MineScorer scorer = new MineScorer();
    /**
     * The singleton mine Manager.
     */
    private static MineManager mineManager;

    /**
     * The constructor of MineManager.
     */
    private MineManager(Context context) {
        super(context);
        this.context = context;

        TILE_WIDTH = MineGameActivity.Width / 10;
        ArrayList<Integer> gameSettings = new ArrayList<>();
        gameSettings.add((MineGameActivity.Width - COL * TILE_WIDTH) / 2);
        gameSettings.add((MineGameActivity.Height - ROW * TILE_WIDTH) / 2);
        gameSettings.add(COL);
        gameSettings.add(ROW);
        gameSettings.add(numBoom);
        gameSettings.add(TILE_WIDTH);
        mineBoard = new MineBoard(gameSettings);
        timer.schedule(scorer, 0, 1000);
        try {
            mineBoard.createBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for singleton Mine Manager.
     *
     * @param context The context.
     * @return mineManager.
     */
    public static MineManager getMineManager(Context context) {
        if (mineManager == null) {
            mineManager = new MineManager(context);
        }
        return mineManager;
    }

    /**
     * Getter for new singleton Mine Manager.
     *
     * @param context The context.
     * @return a new mineManager.
     */
    public static MineManager getNewMineManager(Context context) {
        mineManager = new MineManager(context);
        return mineManager;
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
     * Setter for numBoom.
     *
     * @param numBoom the wanted number of booms.
     */
    public static void setNumBoom(int numBoom) {
        MineManager.numBoom = numBoom;
    }

    public void setTime(int time) { this.time = time; }

    /**
     * Game winning.
     */
    public void wining() {
        if (puzzleSolved()) {
            invalidate();
            sentVictoryAlertDialog();
            this.time = scorer.getTimeScore();
            this.score = scorer.calculateScore(numBoom, time);
            timer.cancel();
        }
    }

    /**
     * Get mineTile that is Unopened.
     *
     * @return unopened MineTile.
     */
    private int getUnopenedTile() {
        int count = 0;

        for (int row = 0; row < mineBoard.getBoardRow(); row++) {
            for (int col = 0; col < mineBoard.getBoardCol(); col++) {
                if (!mineBoard.getMineTile()[row][col].isOpened()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Return true if puzzle is solved, false otherwise.
     *
     * @return is the puzzle solved.
     */
    @Override
    public boolean puzzleSolved() {
        for (int row = 0; row < mineBoard.getBoardRow(); row++) {
            for (int col = 0; col < mineBoard.getBoardCol(); col++) {
                if (!mineBoard.getMineTile()[row][col].isOpened() &&
                        mineBoard.getMineTile()[row][col].getValue() != -1) {
                    return false;
                }
            }
        }
        return getUnopenedTile() == numBoom;
    }

    /**
     * Send an winning AlertDialog and guild player to next activity.
     */
    private void sentVictoryAlertDialog() {
        new AlertDialog.Builder(context)
                .setMessage("Victory!")
                .setCancelable(false)
                .setPositiveButton("I want play again.", (dialog, which) -> resetTheGame())
                .setNegativeButton("Quit", (dialog, which) -> finish())
                .create()
                .show();
    }

    /**
     * Finish the game with You Win Activity.
     */
    private void finish() {
        Intent tmp = new Intent(context, YouWinActivity.class);
        tmp.putExtra("gameType", "Mine");
        context.startActivity(tmp);
    }

    /**
     * Reset the game if the user choose to do so.
     */
    public void resetTheGame() {
        mineBoard.createBoard();
        invalidate();
        tappedOnce = true;
    }

    /**
     * Send an failing AlertDialog and guild player to next activity.
     */
    private void sentDefeatedAlertDialog() {
        mineBoard.isDrawBooms = true;
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage("You Shall Not Pass！")
                .setPositiveButton("I want to play again.", (dialog, which) -> resetTheGame())
                .setNegativeButton("Quit", (dialog, which) -> finish())
                .create()
                .show();
    }

    /**
     * Refresh View.
     *
     * @param canvas The drawing tool for the board.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        mineBoard.draw(canvas);
    }

    private MotionEvent event;

    /**
     * Perform a touch event on a tile.
     *
     * @param event The event.
     * @return True if the user tapped.
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.event = event;
            makeMove();
        }
        return true;
    }

    /**
     * Move maker in Mine game.
     */
    @Override
    public void makeMove(){
        int x = (int) this.event.getX();
        int y = (int) this.event.getY();

        if (checkRange(x, y)) {
            int idxX = (x - mineBoard.getX()) / mineBoard.getTileWidth();
            int idxY = (y - mineBoard.getY()) / mineBoard.getTileWidth();
            mineBoard.touchOpen(new MinePoint(idxX, idxY), tappedOnce);
            tappedOnce = false;

            if (mineBoard.getMineTile()[idxY][idxX].getValue() == -1) {
                sentDefeatedAlertDialog();
                score = 0;
            }
            wining();
        }
        invalidate();
    }

    /**
     * Check touch event is in the range.
     *
     * @param x Given x coordinate.
     * @param y Given y coordinate.
     * @return True if x and y are in the range, false otherwise.
     */
    private boolean checkRange(int x, int y) {
        return x >= mineBoard.getX() &&
                y >= mineBoard.getY() &&
                x <= (mineBoard.getBoardWidth() + mineBoard.getX()) &&
                y <= (mineBoard.getY() + mineBoard.getBoardHeight());
    }
}
