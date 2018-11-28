package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The setting activity.
 */
public class SettingActivity extends AppCompatActivity implements Serializable {
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_setting_file_tmp.ser";
    /**
     * The slidingTile.
     */
    protected SlidingTile slidingTile;
    /**
     * The boardManager.
     */
    private BoardManager boardManager;
    /**
     * Track difficulty selected
     */
    protected int difficulty;
    /**
     * Switch for undo method.
     */
    Switch undoSwitch;
    /**
     * Limited undo or not.
     */
    boolean undoLimited;

    private int level;

    /**
     * The creator of setting activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveToFile(TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_slidingtile_setting);

        Spinner spinner = findViewById(R.id.spinner);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("4x4");
        categories.add("3x3");
        categories.add("5x5");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                String item = (String) arg0.getSelectedItem();
                Toast.makeText(getBaseContext(),
                        "You have selected size : " + item, Toast.LENGTH_SHORT).show();
                switch (item) {
                    case "3x3":
                        difficulty = 3;
                        level = 3;
//                        boardManager.setSlidingTileDifficulty( "Easy");
                        break;
                    case "4x4":
                        difficulty = 4;
                        level = 4;
//                        boardManager.setSlidingTileDifficulty( "Medium");
                        break;
                    default:
                        difficulty = 5;
                        level = 5;
//                        boardManager.setSlidingTileDifficulty( "Hard");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        addConfirmButtonListener();
        undoSwitch = findViewById(R.id.undoSwitch);
        undoSwitch.setChecked(false);
        undoSwitch.setTextOn("Unlimited");
        undoSwitch.setTextOff("limited");
        undoSwitch.setOnClickListener(view -> {
            undoLimited = undoSwitch.isChecked();
            String statusSwitch;
            if (undoSwitch.isChecked()) {
                statusSwitch = undoSwitch.getTextOn().toString();
            } else {
                statusSwitch = undoSwitch.getTextOff().toString();
            }
            Toast.makeText(getApplicationContext(), "Undo Mode :" +
                    statusSwitch, Toast.LENGTH_LONG).show();
        });
    }


    /**
     * Read the temporary slidingTile from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
//        loadFromFile(slidingFile);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.slidingFile);
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
        Intent tmp = new Intent(this, GameActivity.class);
        boardManager = new BoardManager(this, level, false);
        saveToFile(boardManager.getUserName() + ".ser");
        tmp.putExtra("slidingTileBoardManager", boardManager);
        tmp.putExtra("slidingTile", slidingTile);
        tmp.putExtra("undo", undoLimited);
        startActivity(tmp);
        finish();
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
        Button confirmButton = findViewById(R.id.ConfirmButton);
        confirmButton.setOnClickListener(view -> {
            boardManager = null;
            //boardManager = BoardManager.getBoardManager(this);
            switchToGame();
            System.out.println(boardManager.getSlidingTile().getTileList().length);
            System.out.println("still numbers mode");
        });
    }
}