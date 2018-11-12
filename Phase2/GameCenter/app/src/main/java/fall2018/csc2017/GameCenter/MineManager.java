package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;

import static fall2018.csc2017.GameCenter.MineScore.calculateScore;

/**
 * The MineBoard game manager.
 */
public class MineManager extends View {

    /**
     * The MineBoard board.
     */
    private MineBoard mineBoard;

    /**
     * The mark of whether the user tapped for at least once.
     */
    private boolean isFirst = true;

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
     * The time.
     */
    int secondPassed;

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

    private boolean isFalse = false;

    /**
     * The constructor of MineManager.
     */
    public MineManager(Context context) {
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
        try {
            mineBoard.createBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Game winning.
     */
    public void winning() {
        if (puzzleSolved()) {
            sentVictoryAlertDialog();
            score = calculateScore(numBoom, secondPassed);
        }
    }

    /**
     * get mineTile that Unopened.
     *
     * @return unopened MineTile.
     */
    private int getUnopenedTile() {
        int count = 0;

        for (int i = 0; i < mineBoard.boardRow; i++) {
            for (int j = 0; j < mineBoard.boardCol; j++) {
                if (!mineBoard.mineTile[i][j].isOpen) {
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
        return getUnopenedTile() == numBoom;
    }

    /**
     * Return true if boom is tapped, false otherwise.
     *
     * @param idxY boom y coordinate.
     * @param idxX boom x coordinate.
     * @return True if boom is tapped.
     */
    private boolean puzzleFailed(int idxY, int idxX) {
        return mineBoard.mineTile[idxY][idxX].value == -1;
    }

    /**
     * Send an winning AlertDialog and guild player to next activity.
     */
    private void sentVictoryAlertDialog() {
        new AlertDialog.Builder(context)
                .setMessage("Victory!")
                .setCancelable(false)
                .setPositiveButton("I want play again.", (dialog, which) -> {
                mineBoard.createBoard();
                invalidate();
                isFirst = true; })
                .setNegativeButton("Quit", (dialog, which) -> System.exit(0))
                .create()
                .show();
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
                    invalidate(); })
                .setNegativeButton("Quit", (dialog, which) -> System.exit(0))
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
                mineBoard.touchOpen(new MinePoint(idxX, idxY), isFirst);
                isFirst = false;

                if (puzzleFailed(idxY, idxX)) {
                    sentDefeatedAlertDialog();
                }
                if (isFalse) {
                    isFalse = false;
                    invalidate();
                    return true;
                }
                winning();
                invalidate();
            }

        }
        return true;
    }

    /**
     * Check touch event is in the range. Helper for onTouchEvent.
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
