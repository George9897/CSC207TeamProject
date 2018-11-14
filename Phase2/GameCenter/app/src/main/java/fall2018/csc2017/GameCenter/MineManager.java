package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;

/**
 * The Mine game manager.
 */
public class MineManager extends View {

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
    private final int numBoom = 10;
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
    Timer timer = new Timer();
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
        timer.schedule(scorer, 0,1000);
        try {
            mineBoard.createBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for singleton Mine Manager.
     * @param context The context.
     * @return mineManager.
     */
    public static MineManager getMineManager(Context context){
        if (mineManager == null){
            mineManager = new MineManager(context);
        }
        return mineManager;
    }
    /**
     * Getter for new singleton Mine Manager.
     * @param context The context.
     * @return a new mineManager.
     */
    public static MineManager getNewMineManager(Context context){
        mineManager = new MineManager(context);
        return mineManager;
    }
    /**
     * Getter for the time passed.
     * @return The time.
     */
    public int getTime() {
        return time;
    }
    /**
     * Getter for the score.
     * @return score.
     */
    public int getScore() { return score; }

    /**
     * Game winning.
     */
    public void wining() {
        if (puzzleSolved()) {
            sentVictoryAlertDialog();
            this.score = scorer.getFinalScore(numBoom);
            this.time = scorer.getTimeScore();
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

        for (int row = 0; row < mineBoard.boardRow; row++) {
            for (int col = 0; col < mineBoard.boardCol; col++) {
                if (!mineBoard.mineTile[row][col].isOpened) {
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
    private boolean puzzleSolved() {
        int UnopenedTile = getUnopenedTile();
        return UnopenedTile == numBoom;
    }

    /**
     * Return true if boom is tapped, false otherwise.
     *
     * @param boomY boom y coordinate.
     * @param boomX boom x coordinate.
     * @return True if boom is tapped.
     */
    private boolean puzzleFailed(int boomY, int boomX) {
        return mineBoard.mineTile[boomY][boomX].value == -1;
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
    private void finish(){
        Intent tmp = new Intent(context, YouWinActivity.class);
        tmp.putExtra("gameType","Mine" );
        context.startActivity(tmp);
    }

    /**
     * Reset the game if the user choose to do so.
     */
    public void resetTheGame(){
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
                .setPositiveButton("Heroes never die!", (dialog, which) -> {
                    mineBoard = mineBoard.lastMineBoard;
                    invalidate();
                })
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
    protected void onDraw(Canvas canvas) { mineBoard.draw(canvas); }

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
            int x = (int) event.getX();
            int y = (int) event.getY();

            if (checkRange(x, y)) {
                int idxX = (x - mineBoard.x) / mineBoard.tileWidth;
                int idxY = (y - mineBoard.y) / mineBoard.tileWidth;
                mineBoard.touchOpen(new MinePoint(idxX, idxY), tappedOnce);
                tappedOnce = false;

                if (puzzleFailed(idxY, idxX)) {
                    sentDefeatedAlertDialog();
                    score = 0;
                    time = scorer.getTimeScore();
                    timer.cancel();
                }
                wining();
                invalidate();
            }
        }
        return true;
    }

    /**
     * Check touch event is in the range.
     *
     * @param x Given x coordinate.
     * @param y Given y coordinate.
     * @return True if x and y are in the range, false otherwise.
     */
    private boolean checkRange(int x, int y) {
        return x >= mineBoard.x &&
                y >= mineBoard.y &&
                x <= (mineBoard.boardWidth + mineBoard.x) &&
                y <= (mineBoard.y + mineBoard.boardHeight);
    }
}
