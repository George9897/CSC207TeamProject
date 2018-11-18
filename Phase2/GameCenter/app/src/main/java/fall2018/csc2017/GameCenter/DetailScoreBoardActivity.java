package fall2018.csc2017.GameCenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class DetailScoreBoardActivity extends AppCompatActivity {

    /**
     * The boardManager that is used in that round.
     */
    private BoardManager boardManager;

    private MineManager mineManager;

    private SudokuBoardManager sudokuBoardManager;

    private String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_score_board);
    }

    private void makeChanges(){
        switch (gameType){
            case "SlidingTile":
                break;
            case "Mine":
                mineManager = MineManager.getMineManager(this);
                break;
            case "Sudoku":
                sudokuBoardManager = SudokuBoardManager.getSudokuBoardManager(this);
                break;
        }
    }


    private void setTopOnes(DetailScoreBoard scoreBoard){
        TextView easyTopOne = findViewById(R.id.easyTopOne);
        TextView mediumTopOne = findViewById(R.id.mediumTopOne);
        TextView hardTopOne = findViewById(R.id.hardTopOne);

        easyTopOne.setText(String.format("%s\n%s", scoreBoard.getTopOneName(easy),
                scoreBoard.getTopOneScore(easy)));
        mediumTopOne.setText(String.format("%s\n%s", scoreBoard.getTopOneName(medium),
                scoreBoard.getTopOneScore(easy)));
        hardTopOne.setText(String.format("%s\n%s", scoreBoard.getTopOneName(hard),
                scoreBoard.getTopOneScore(easy)));
    }

    private void setModeData(DetailScoreBoard scoreBoard){
        TextView easyData = findViewById(R.id.easyData);
        TextView mediumData = findViewById(R.id.mediumData);
        TextView hardData = findViewById(R.id.hardData);

        easyData.setText(othersToString(scoreBoard.getOthers(easy)));
        mediumData.setText(othersToString(scoreBoard.getOthers(medium)));
        hardData.setText(othersToString(scoreBoard.getOthers(hard)));


    }

    private String othersToString(List<Pair<Integer, String>> sortedOthers){
        StringBuilder result = new StringBuilder();
        if (sortedOthers.size() > 0) {
            for (int i = 1; i <= sortedOthers.size(); i++) {
                result.append(i + 1);
                result.append(sortedOthers.get(i).second);
                result.append("\n");
                result.append(sortedOthers.get(i).first);
            }
        }
        return result.toString();
    }

}
