package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class DetailScoreBoardActivity extends AppCompatActivity implements Serializable {

    /**
     * The boardManager that is used in that round.
     */
    private Manager manager;

    private DetailScoreBoard detailScoreBoard;

    private String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_score_board);
        Intent intent = getIntent();
        gameType = intent.getStringExtra("gameTypeWantedToSee");

        getGameType();
        detailScoreBoard = DetailScoreBoard.getDetailScoreBoard(gameType, this);
        detailScoreBoard.display();
        TextView gameView = findViewById(R.id.GameView);
        gameView.setText(gameType);
        setTopOnes(detailScoreBoard);
        setModeData(detailScoreBoard);

        addScoreQuitButtonsListener();
    }

    private void getGameType(){

    }

    private void makeChanges(){
        switch (gameType){
            case "SlidingTile":
                manager = BoardManager.getBoardManager(this);
                break;
            case "Mine":
                manager = MineManager.getMineManager(this);
                break;
            case "Sudoku":
                manager = SudokuBoardManager.getSudokuBoardManager(this);
                break;
        }
    }


    private void setTopOnes(DetailScoreBoard scoreBoard){
        TextView easyTopOne = findViewById(R.id.easyTopOne);
        TextView mediumTopOne = findViewById(R.id.mediumTopOne);
        TextView hardTopOne = findViewById(R.id.hardTopOne);

        easyTopOne.setText(scoreBoard.getEasyTopOne());
        mediumTopOne.setText(scoreBoard.getMediumTopOne());
        hardTopOne.setText(scoreBoard.getHardTopOne());
    }

    private void setModeData(DetailScoreBoard scoreBoard){
        TextView easyData = findViewById(R.id.easyTextView);
        TextView mediumData = findViewById(R.id.mediumTextView);
        TextView hardData = findViewById(R.id.hardTextView);

        easyData.setText(othersToString(scoreBoard.getEasySortedList()));
        mediumData.setText(othersToString(scoreBoard.getMediumSortedList()));
        hardData.setText(othersToString(scoreBoard.getHardSortedList()));
    }

    private String othersToString(List<String> sortedOthers){
        StringBuilder result = new StringBuilder();
        if (sortedOthers.size() > 0) {
            for (int i = 0; i < sortedOthers.size(); i++) {
                result.append(sortedOthers.get(i));
                result.append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Activate the buttons for Quit.
     */
    private void addScoreQuitButtonsListener() {
        Button quitButton = findViewById(R.id.scoreBoardQuitbutton);
        quitButton.setOnClickListener((v) -> {
            switch (gameType){
                case "SlidingTile":
                    BoardManager.destroyBoardManager();
                    break;
                case "Mine":
                    //TODO
                    break;
                case "Sudoku":
                    SudokuBoardManager.destroySudokuBoardManager();
                    break;
            }
            Intent tmp = new Intent(this, StartingActivity.class);
            startActivity(tmp);
        });
    }

}
