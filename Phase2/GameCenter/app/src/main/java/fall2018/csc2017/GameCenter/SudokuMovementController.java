package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.Serializable;

/**
 * The movement controller of game.
 */
class SudokuMovementController implements Serializable {

    private SudokuBoardManager sudokuBoardManager;

    /**
     * The constructor of SudokuMovementController.
     */
    SudokuMovementController() {
    }

    /**
     * Set a SudokuMovementController.
     *
     * @param sudokuBoardManager The board manager that is being set.
     */
    void setSudokuBoardManager(SudokuBoardManager sudokuBoardManager) {
        this.sudokuBoardManager = sudokuBoardManager;
    }

    /**
     * Process a tapping movement.
     *
     * @param context  The context.
     * @param position The position that is tapped.
     */
    void processTapMovement(Context context, int position) {
        if (sudokuBoardManager.isValidTap(position)) {
            sudokuBoardManager.makeMove(position);
            if (sudokuBoardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                Intent tmp = new Intent(context, YouWinActivity.class);
                tmp.putExtra("gameType", "Sudoku");
                tmp.putExtra("sudokuGameBoard", sudokuBoardManager);
                sudokuBoardManager.wining();
                context.startActivity(tmp);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
