package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

/**
 * The ScoreBoard Activity.
 */
public class ScoreBoardActivity extends AppCompatActivity implements Serializable {
    /**
     * The BoardManager.
     */
    private BoardManager boardManager;
    /**
     * The ScoreBoard.
     */
    private ScoreBoard scoreBoard;
    /**
     * display records for per user or per game.
     */
    private boolean userScore = false;
    /**
     * The display of records.
     */
    TextView resultBox;

    /**
     * generate buttons when createBooms this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        scoreBoard = ScoreBoard.getScoreBoard(this);
        scoreBoard.buildLevelMap();
        boardManager = BoardManager.getBoardManager(this);

        setupUserScoreButtonListener();
        setupAllPlayerScoreButtonListener();

        setUpLevelThreeRankButtonListener();
        setUpLevelFourRankButtonListener();
        setUpLevelFiveRankButtonListener();
        setText();
    }

    /**
     * Create Button for per user.
     */
    @SuppressLint("SetTextI18n")
    private void setupUserScoreButtonListener() {
        Button userScoreButton = findViewById(R.id.userScoreButton);
        userScoreButton.setOnClickListener((v) -> {
            userScore = true;
            resultBox = findViewById(R.id.levelRank);
            resultBox.setText("Score SlidingTile for " + boardManager.userName + " now! " +
                    "Please choose complexity!");
        });
    }

    /**
     * Create Button for per game.
     */
    private void setupAllPlayerScoreButtonListener() {
        Button gameScoreButton = findViewById(R.id.gameScoreButton);
        gameScoreButton.setOnClickListener((v) -> userScore = false);
    }

    /**
     * display records.
     */
    private void setText() {
        scoreBoard = ScoreBoard.getScoreBoard(this);
        TextView highestScore = findViewById(R.id.highestScoreDisplayer);
        highestScore.setText((Integer.toString(scoreBoard.getHighestScore())));
    }

    /**
     * Activate the Level 3 button.
     */
    private void setUpLevelThreeRankButtonListener() {
        Button rankButton = findViewById(R.id.levelThreeButton);
        rankButton.setOnClickListener((v) -> {
            resultBox = findViewById(R.id.levelRank);
            String result;
            if (!userScore) {
                result = scoreBoard.toString(3);
                if (scoreBoard.toString(3).equals("")) {
                    result = "No record in level 3 file.";
                }
            } else {
                result = scoreBoard.toString(3, boardManager.userName);
                if (result.equals("")) {
                    result = "No record in level 3 file.";
                }
            }
            resultBox.setText(result);
        });
    }

    /**
     * Activate the Level 4 button.
     */
    private void setUpLevelFourRankButtonListener() {
        Button rankButton = findViewById(R.id.levelFourButton);
        rankButton.setOnClickListener((v) -> {
            resultBox = findViewById(R.id.levelRank);
            String result;
            if (!userScore) {
                result = scoreBoard.toString(4);
                if (scoreBoard.toString(4).equals("")) {
                    result = "No record in level 4 file.";
                }
            } else {
                result = scoreBoard.toString(4, boardManager.userName);
                if (result.equals("")) {
                    result = "No record in level 4 file.";
                }
            }
            resultBox.setText(result);
        });
    }

    /**
     * Activate the Level 5 button.
     */
    private void setUpLevelFiveRankButtonListener() {
        Button rankButton = findViewById(R.id.levelFiveButton);

        rankButton.setOnClickListener((v) -> {
            resultBox = findViewById(R.id.levelRank);
            String result;
            if (!userScore) {
                result = scoreBoard.toString(5);
                if (scoreBoard.toString(5).equals("")) {
                    result = "No record in level 5 file.";
                }
            } else {
                result = scoreBoard.toString(5, boardManager.userName);
                if (result.equals("")) {
                    result = "No record in level 5 file.";
                }
            }
            resultBox.setText(result);
        });
    }
}
