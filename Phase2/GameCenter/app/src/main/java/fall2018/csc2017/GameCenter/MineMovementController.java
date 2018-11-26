package fall2018.csc2017.GameCenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.Serializable;

/**
 * The movement controller of game.
 */
class MineMovementController implements Serializable {
    /**
     * The singleton mine manager.
     */
    private MineManager mineManager;
    /**
     * The context.
     */
    private Context context;

    /**
     * The constructor of MineMovementController.
     */
    MineMovementController(Context context) {
        this.context = context;
    }

    /**
     * Set a MineMovementController.
     *
     * @param mineManager The manager that is being set.
     */
    void setMineManager(MineManager mineManager) {
        this.mineManager = mineManager;
    }

    /**
     * Reset the game if the user choose to do so.
     */
    private void resetTheGame() {
        mineManager = new MineManager(context);
        Intent tmp = new Intent(context, MineSettingActivity.class);
        context.startActivity(tmp);
    }

    /**
     * Finish the game with You Win Activity.
     */
    private void finish() {
        Intent tmp = new Intent(mineManager.getContext(), YouWinActivity.class);
        tmp.putExtra("gameType", "Mine");
        mineManager.getContext().startActivity(tmp);
    }

    /**
     * Process a tapping movement.
     *
     * @param context  The context.
     */
    void processTapMovement(Context context, int position) {
        if(mineManager.isValidTap(position)) {
            mineManager.getMineBoard().touchOpen(position, mineManager.isFirstTap());
            mineManager.setFirstTapToFalse();
            int row = position / MineBoard.getSize();
            int col = position % MineBoard.getSize();
            if (mineManager.getMineBoard().getMineTile(row, col).getValue() == -1) {
                mineManager.failing();
                mineManager.setLose();
                new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setMessage("You Shall Not Pass！")
                        .setPositiveButton("I want to play again.", (dialog, which) ->
                                resetTheGame())
                        .setNegativeButton("Quit", (dialog, which) -> finish())
                        .create()
                        .show();
            }
        }
        else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Process a double tap movement.
     *
     * @param position the position that is been double tapped.
     */
    void processDoubleTapMovement(int position) {
        if (mineManager.isValidTap(position)) {
            int row = position / MineBoard.getSize();
            int col = position % MineBoard.getSize();
            mineManager.getMineBoard().replaceToFlag(row, col);
            if (mineManager.puzzleSolved()) {
                mineManager.winning();
                new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setMessage("Victory!")
                        .setPositiveButton("I want to play again.", (dialog, which) ->
                                resetTheGame())
                        .setNegativeButton("Quit", (dialog, which) -> finish())
                        .create()
                        .show();
            }
        }
        else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}