package fall2018.csc2017.GameCenter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import java.io.Serializable;

/**
 * The movement controller of game.
 */
class MineMovementController implements Serializable {

    private static MineManager mineManager;

    /**
     * The constructor of MovementController.
     */
    MineMovementController() {
    }

    /**
     * Set a MovementController.
     *
     * @param mineManager The manager that is being set.
     */
    void setMineManager(MineManager mineManager) {
        MineMovementController.mineManager = mineManager;
    }

    /**
     * Reset the game if the user choose to do so.
     */
    private void resetTheGame() {
        mineManager = MineManager.getNewMineManager(mineManager.getContext());
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
            mineManager.makeMove(position);
            if (mineManager.puzzleSolved()) {
                new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setMessage("Victory!")
                        .setPositiveButton("I want to play again.", (dialog, which) ->
                                resetTheGame())
                        .setNegativeButton("Quit", (dialog, which) -> finish())
                        .create()
                        .show();
            }
            int row = position / MineBoard.getSize();
            int col = position % MineBoard.getSize();
            if (mineManager.getMineBoard().getMineTile()[row][col].getValue() == -1) {
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

    }
}