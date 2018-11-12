package fall2018.csc2017.GameCenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import java.io.Serializable;

public class MineSettingActivity extends AppCompatActivity implements Serializable {/**
 * A temporary save file.
 */
public static final String TEMP_SAVE_FILENAME = "save_setting_file_tmp.ser";

    /**
     * Track difficulty selected
     */
    protected int difficulty;

    /**
     * Switch for undo method.
     */
    Switch undoSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * Read the temporary slidingTile from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
    }

    /**
     * Dispatch onStop() to fragments.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
    }

    /**
     * Save the slidingTile manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
    }

    /**
     * Activate the confirm button.
     */
    private void addConfirmButtonListener() {
    }
}
