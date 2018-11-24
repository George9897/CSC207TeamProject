package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

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

    /**
     * The slidingTile manager.
     */
    private AccountManager accountManager = AccountManager.getAccountManager();

    private BoardManager boardManager;

    /**
     * The singleton scoreBoard.
     */
    private ScoreBoard scoreBoard;

    /**
     * Creator of of starting activity.
     * @param savedInstanceState the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scoreBoard = ScoreBoard.getScoreBoard(this);
        scoreBoard.buildLevelMap();
        saveToFile(slidingFile);

        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addScoreboardButtonListener();
        addLogoutButtonListener();
        addProfileButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(v -> {
            Intent tmp = new Intent(this, GameCenterActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(v -> {
            //loadFromFile(accountManager.userName + ".ser");
            //saveToFile(slidingFile);
            //switchToGame();
            loadFromFile(mineFile);
            saveToFile(mineFile);
            makeToastLoadedText();
            switchToMine();
        });
    }

    /**
     * Switch to mine game.
     */
    private void switchToMine(){
        Intent tmp = new Intent(this, MineGameActivity.class);
        saveToFile(StartingActivity.mineFile);
        startActivity(tmp);
    }

    /**
     * Activate the scoreboard button.
     */
    private void addScoreboardButtonListener() {
        Button scoreboardButton = findViewById(R.id.scoreButton);
        scoreboardButton.setOnClickListener(view -> {
            buildScoreFile(scoreBoard.getLevelOption());
            scoreBoard.updateHighestScore();
            Intent tmp = new Intent(this, ChooseGameScoreActivity.class);
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
     * Build level file based on levelOption.
     *
     * @param levelOption level of size of SlidingTile.
     */
    private void buildScoreFile(Integer[] levelOption) {
        int index = 0;
        while (index < levelOption.length) {
            Integer level = levelOption[index];
            String fileName = "level_" + Integer.toString(level) + "_File.ser";
            ArrayList userScore = new ArrayList();
            if (!scoreBoard.getLevelMap().containsKey(level)) {
                scoreBoard.getLevelMap().put(level, userScore);
                try {
                    ObjectOutputStream outputStream = new ObjectOutputStream(
                            this.openFileOutput(fileName, MODE_PRIVATE));
                    outputStream.writeObject(scoreBoard.getLevelMap().get(level));
                    outputStream.close();
                } catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }
            index++;
        }
    }

    /**
     * Activate the logout button.
     */
    private void addLogoutButtonListener() {
        Button logoutButton = findViewById(R.id.LogoutButton);
        logoutButton.setOnClickListener(view -> {
            BoardManager.destroyBoardManager();
            Intent temp = new Intent(this, HomeActivity.class);
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
        loadFromFile(slidingFile);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(StartingActivity.slidingFile);
        startActivity(tmp);
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
                //TODO: 3 gmae board manager
                //boardManager = (BoardManager) input.readObject();
                MineManager mineManager = (MineManager) input.readObject();
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
            //TODO: 3 gmae board manager
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
