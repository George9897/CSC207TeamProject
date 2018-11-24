package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.Serializable;

/**
 * The movement controller of game.
 */
public class MovementController implements Serializable {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The constructor of MovementController.
     */
    MovementController() {
    }

    /**
     * Set a MovementController.
     *
     * @param boardManager The board manager that is being set.
     */
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process a tapping movement.
     *  @param context  The context.
     * @param position The position that is tapped.
     */
    void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                Intent tmp = new Intent(context, YouWinActivity.class);
                tmp.putExtra("gameType","SlidingTile" );
                context.startActivity(tmp);
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
