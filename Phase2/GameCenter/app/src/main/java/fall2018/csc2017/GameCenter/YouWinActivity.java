package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

/**
 * The Activity used to show "you win".
 */
public class YouWinActivity extends AppCompatActivity implements Serializable {
    /**
     * The boardManager that is used in that round.
     */
    private BoardManager boardManager;

    private MineManager mineManager;

    String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_win);
        TextView scoreBox = findViewById(R.id.scoreViewData);
        Intent intent = getIntent();
        gameType = intent.getStringExtra("gameType");

        switch (gameType){
            case "SlidingTile":
                boardManager = BoardManager.getBoardManager(this);
                scoreBox.setText((Integer.toString(boardManager.getScore())));
                break;
            case "Mine":
                mineManager = MineManager.getMineManager(this);
                scoreBox.setText((Integer.toString(mineManager.getScore())) + "\n\r" + "Time: "
                        + (Integer.toString(mineManager.getTime())) + " Second");
                break;
        }

        setUpSeeScoreButtonListener();
        setUpBackHButtonListener();
        setUpPlayAgainButtonListener();
    }

    /**
     * Set up the "see score" button.
     */
    private void setUpSeeScoreButtonListener() {
        Button seeScoreButton = findViewById(R.id.seeScoreButton);

        seeScoreButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, ScoreBoardActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Set up the "Back" button.
     */
    private void setUpBackHButtonListener() {
        Button backToHomeButton = findViewById(R.id.backHomeButton);

        backToHomeButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, StartingActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Set up the "Play again" button.
     */
    private void setUpPlayAgainButtonListener() {
        Button backToHomeButton = findViewById(R.id.playAgainButton);

        backToHomeButton.setOnClickListener((v) -> {
            switch (gameType){
                case "SlidingTile":
                    Intent SlidingTileTmp = new Intent(this, SettingActivity.class);
                    startActivity(SlidingTileTmp);
                    break;
                case "Mine":
                    Intent MineTmp = new Intent(this, MineSettingActivity.class);
                    startActivity(MineTmp);
                    break;
            }
        });
    }
}
