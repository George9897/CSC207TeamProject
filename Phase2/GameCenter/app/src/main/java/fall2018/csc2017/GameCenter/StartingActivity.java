package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity implements Serializable {
    /**
     * A temporary save file for sliding tile.
     */
    public static final String slidingFile = "sliding_tmp.ser";
    /**
     * A temporary save file for mine.
     */
    public static final String mineFile = "mine_tmp.ser";
    /**
     * A temporary save file for sudoku.
     */
    public static final String sudokuFile = "sudoku_tmp.ser";

    private BoardManager boardManager;

    private MineManager mineManager;

    private SudokuBoardManager sudokuBoardManager;

    private String gameType;

    /**
     * Creator of of starting activity.
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        gameType = intent.getStringExtra("gameType");

        switch (gameType){
            case "SlidingTile":
//                loadFromFile(slidingFile);
                break;
            case "Mine":
                loadFromFile(mineFile);
                break;
            case "Sudoku":
                loadFromFile(sudokuFile);
                break;
        }

        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addScoreboardButtonListener();
        addMyScoreButtonListener();
        addLogoutButtonListener();
        addProfileButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(v -> {
            switch (gameType){
                case "SlidingTile":
                    Intent slidingTile = new Intent(this, SettingActivity.class);
                    startActivity(slidingTile);
                    break;
                case "Mine":
                    Intent mine = new Intent(this, MineSettingActivity.class);
                    startActivity(mine);
                    break;
                case "Sudoku":
                    Intent sudoku = new Intent(this, SudokuSettingActivity.class);
                    startActivity(sudoku);
                    break;
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(v -> {
            switch (gameType){
                case "SlidingTile":
                    loadFromFile(slidingFile);
                    makeToastLoadedText();
                    switchToGame();
                    break;
                case "Mine":
                    loadFromFile(mineFile);
                    makeToastLoadedText();
                    switchToGame();
                    break;
                case "Sudoku":
                    loadFromFile(sudokuFile);
                    makeToastLoadedText();
                    switchToGame();
                    break;
            }
        });
    }

    /**
     * Activate the scoreboard button.
     */
    private void addScoreboardButtonListener() {
        Button scoreboardButton = findViewById(R.id.scoreButton);
        scoreboardButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, ChooseGameScoreActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Activate the MyScore button.
     */
    private void addMyScoreButtonListener(){
        Button myScoreButton = findViewById(R.id.MyScore);
        myScoreButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, PersonalScoreboardActivity.class);
            startActivity(tmp);
        });
    }


    /**
     * Activate the profile button.
     */
    private void addProfileButtonListener() {
        Button profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(view -> {
            Intent tmp = new Intent(this, ProfileActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Activate the logout button.
     */
    private void addLogoutButtonListener() {
        Button logoutButton = findViewById(R.id.LogoutButton);
        logoutButton.setOnClickListener(view -> {
            boardManager = null;
            Intent temp = new Intent(this, LoginActivity.class);
            startActivity(temp);
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary slidingTile from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        switch (gameType){
            case "SlidingTile":
                Intent slidingTile = new Intent(this, GameActivity.class);
                loadFromFile(slidingFile);
                if(boardManager!=null) {
                    System.out.println(boardManager.userName + "============================");
                    slidingTile.putExtra("slidingTileBoardManager", boardManager);
                    startActivity(slidingTile);
                } else {
                    Toast.makeText(this, "Never played before.", Toast.LENGTH_SHORT).show();
                }
                break;
            case "Mine":
                Intent mine = new Intent(this, MineGameActivity.class);
                saveToFile(mineFile);
                startActivity(mine);
                break;
            case "Sudoku":
                Intent sudoku = new Intent(this, SudokuBoardActivity.class);
                saveToFile(sudokuFile);
                startActivity(sudoku);
                break;
        }
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
                switch (gameType){
                    case "SlidingTile":
                        boardManager = (BoardManager) input.readObject();
                        break;
                    case "Mine":
                        mineManager = (MineManager) input.readObject();
                        break;
                    case "Sudoku":
                        sudokuBoardManager = (SudokuBoardManager) input.readObject();
                        break;
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
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
            switch (gameType){
                case "SlidingTile":
                    outputStream.writeObject(boardManager);
                    break;
                case "Mine":
                    outputStream.writeObject(mineManager);
                    break;
                case "Sudoku":
                    outputStream.writeObject(sudokuBoardManager);
                    break;
            }
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
