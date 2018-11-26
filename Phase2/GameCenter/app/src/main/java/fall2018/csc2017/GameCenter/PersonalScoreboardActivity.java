package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PersonalScoreboardActivity extends AppCompatActivity {

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_scoreboard);
        this.userName = AccountManager.getAccountManager().getUserName();
        setUserName();
        setSlidingTile();
        setMine();
        setSudoku();
    }

    private void setUserName(){
        TextView nameView = findViewById(R.id.nameData);
        nameView.setText(userName);
    }

    @SuppressLint("SetTextI18n")
    private void setSlidingTile(){
        String gameType = "SlidingTile";
        //TODO: implement highestScore;
        int highestScore = 0;
        TextView soreView = findViewById(R.id.slidingTileHighestScore);
        soreView.setText(Integer.toString(highestScore));
    }

    @SuppressLint("SetTextI18n")
    private void setMine() {
        String gameType = "Mine";
        //TODO: implement highestScore;
        int highestScore = 0;
        TextView soreView = findViewById(R.id.mineHighestScore);
        soreView.setText(Integer.toString(highestScore));
    }

    @SuppressLint("SetTextI18n")
    private void setSudoku(){
        String gameType = "Sudoku";
        //TODO: implement highestScore;
        int highestScore = 0;
        TextView soreView = findViewById(R.id.sudokuHighestScore);
        soreView.setText(Integer.toString(highestScore));
    }
}
