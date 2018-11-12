package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.Serializable;

/**
 * The movement controller of game.
 */
public class SudokuMovementController implements Serializable {

    private SudokuBoardManager sudokuBoardManager;

    /**
     * The constructor of MovementController.
     */
    public SudokuMovementController() {
    }

    /**
     * Set a MovementController.
     *
     * @param sudokuBoardManager The boardmanager that is being set.
     */
    public void setSudokuBoardManager(SudokuBoardManager sudokuBoardManager) {
        this.sudokuBoardManager = sudokuBoardManager;
    }

    /**
     * Process a tapping movement.
     *
     * @param context  The context.
     * @param position The position that is tapped.
     * @param display  The display.
     */
    public void processTapMovement(Context context, int position, boolean display) {
        if (sudokuBoardManager.isValidTap(position)) {
            sudokuBoardManager.makeMove(position);
            if (sudokuBoardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                Intent tmp = new Intent(context, YouWinActivity.class);
                context.startActivity(tmp);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
