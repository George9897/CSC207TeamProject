package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The GamaCenterActivity.
 */
public class GameCenterActivity extends AppCompatActivity implements Serializable {
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_center_file_tmp.ser";

    /**
     * Create a Game Center Activity for user to choose game.
     *
     * @param savedInstanceState a saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);

        addTileGameButton();
        addMineGameButton();
        addSudokuGameButton();
    }

    /**
     * Activate the sliding tile game button.
     */
    private void addTileGameButton() {
        ImageButton tileGamePicButton = findViewById(R.id.tileGameButton);
        tileGamePicButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, SettingActivity.class);
            GameCenterActivity.this.startActivity(tmp);
        });
    }

    /**
     * Activate the Mine game button.
     */
    private void addMineGameButton() {
        ImageButton MineGamePicButton = findViewById(R.id.mineGameButton);
        MineGamePicButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, MineSettingActivity.class);
            GameCenterActivity.this.startActivity(tmp);
        });
    }

    /**
     * Activate the sliding tile game button.
     */
    private void addSudokuGameButton() {
        ImageButton sudokuButton = findViewById(R.id.sudokuButton);
        sudokuButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, SudokuSettingActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Read the temporary slidingTile from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Load the slidingTile manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    /**
     * Save the slidingTile manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(GameCenterActivity.TEMP_SAVE_FILENAME);
    }
}
