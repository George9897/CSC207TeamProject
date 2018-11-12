package fall2018.csc2017.GameCenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.Mine;

/**
 * The Mine game manager.
 */
public class MineManager extends View {

    /**
     * The Mine board.
     */
    private Mine mine;

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
    private final int mineNum = 10;

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
        gameSettings.add(mineNum);
        gameSettings.add(TILE_WIDTH);
        mine = new Mine(gameSettings);
        try {
            mine.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Game logic.
     */
    public void logic() {
        if (puzzleSolved()) {
            sentVictoryAlertDialog();
        }
    }

    /**
     * get mineTile that Unopened.
     *
     * @return unopened MineTile.
     */
    private int getUnopenedTile() {
        int count = 0;

        for (int i = 0; i < mine.boardRow; i++) {
            for (int j = 0; j < mine.boardCol; j++) {
                if (!mine.mineTile[i][j].open) {
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
        return UnopenedTile == mineNum;
    }

    /**
     * Return true if boom is tapped, false otherwise.
     *
     * @param idxY boom y coordinate.
     * @param idxX boom x coordinate.
     * @return True if boom is tapped.
     */
    private boolean puzzleFail(int idxY, int idxX) {
        return mine.mineTile[idxY][idxX].value == -1;
    }

    /**
     * Send an winning AlertDialog and guild player to next activity.
     */
    private void sentVictoryAlertDialog() {
        new AlertDialog.Builder(context)
                .setMessage("Victory!")
                .setCancelable(false)
                .setPositiveButton("I want play again.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mine.init();
                        invalidate();
                        isFirst = true;
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .create()
                .show();
    }

    /**
     * Send an failing AlertDialog and guild player to next activity.
     */
    private void sentDefeatedAlertDialog() {
        mine.isDrawBooms = true;
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage("You Shall Not Pass！")
                .setPositiveButton("Heroes never die!(Undo)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mine.init();
                        isFalse = true;
                        isFirst = true;

                        invalidate();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .create()
                .show();
    }

    /**
     * refresh View.
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        mine.draw(canvas);
    }

    /**
     * On touch event.
     *
     * @param event
     * @return True if
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            if (checkRange(x, y)) {
                int idxX = (x - mine.x) / mine.tileWidth;
                int idxY = (y - mine.y) / mine.tileWidth;
                mine.touchOpen(new MinePoint(idxX, idxY), isFirst);
                isFirst = false;

                if (puzzleFail(idxY, idxX)) {
                    sentDefeatedAlertDialog();
                }
                if (isFalse) {
                    isFalse = false;
                    invalidate();
                    return true;
                }
                logic();

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
        return x >= mine.x &&
                y >= mine.y &&
                x <= (mine.boardWidth + mine.x) &&
                y <= (mine.y + mine.boardHeight);
    }
}
